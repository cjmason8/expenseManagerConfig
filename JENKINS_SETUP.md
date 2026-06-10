# Jenkins Setup for expenseManagerConfig

## Changes Applied

This repository has been updated to support AWS Secrets Manager and improved Jenkins deployment reliability.

### 1. Jenkinsfile - Preserves .env File

**What changed:**
- Removed "Delete Workspace" stage that deleted everything
- Updated "Checkout" stage to preserve `.env` file while cleaning other files
- Clones into temporary directory and moves files to preserve `.env`

**Why:**
- The `.env` file contains AWS credentials needed for AWS Secrets Manager
- Must persist across Jenkins builds
- Not in git (for security), so must be manually placed on Jenkins workspace

### 2. deploy.sh - AWS Credentials Loading

**What changed:**
- Loads AWS credentials from `.env` file before deployment
- Exports `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` to environment
- Added retry logic for Rancher state conflicts (up to 5 attempts, 10s delay)

**Why:**
- Docker containers need AWS credentials to access AWS Secrets Manager
- Credentials are passed via environment variables to containers
- Retry logic handles timing issues with Rancher upgrades

## Jenkins Workspace Setup

### One-Time Setup on Jenkins Server

You need to create the `.env` file in the Jenkins workspace:

```bash
# SSH to Jenkins server
ssh user@jenkins-server

# Navigate to Jenkins workspace
cd /var/jenkins_home/workspace/expense-manager-pipeline

# Create .env file with your actual credentials
cat > .env << 'EOF'
AWS_ACCESS_KEY_ID=your-actual-access-key-here
AWS_SECRET_ACCESS_KEY=your-actual-secret-key-here
EOF

# Set secure permissions
chmod 600 .env

# Verify
cat .env
```

**Important:** Replace the placeholder values with your actual AWS credentials. This only needs to be done once. The `.env` file will persist across all Jenkins builds.

## How It Works

### Build Flow

1. **Checkout Stage**
   - Preserves `.env` file
   - Clones latest code from GitHub
   - Clones expenseManager application repo

2. **Build Stage**
   - Builds expenseManager with Maven
   - Creates Docker image

3. **Deploy Stage**
   - Loads AWS credentials from `.env`
   - Exports them as environment variables
   - Deploys to Rancher with retry logic
   - Rancher passes credentials to Docker containers
   - Application uses credentials to access AWS Secrets Manager

### Runtime Flow

```
Application starts
    ↓
Reads AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY from environment
    ↓
AwsSecretsService connects to AWS Secrets Manager
    ↓
Retrieves database credentials from prod-database-credentials secret
    ↓
DatabaseConfig uses credentials to connect to database
    ↓
Application runs
```

## Verification

### Check .env file exists in Jenkins workspace

```bash
ssh user@jenkins-server
ls -la /var/jenkins_home/workspace/expense-manager-pipeline/.env
```

Should show:
```
-rw------- 1 jenkins jenkins 123 Jun 11 09:00 .env
```

### Check Jenkins Build Logs

Look for this line in the "Deploy" stage:
```
Loading AWS credentials from .env file...
```

### Check Application Logs

After deployment, check the application logs:
```bash
docker logs expensemanager 2>&1 | grep "AWS Secrets"
```

Should see:
```
AWS Secrets Manager client initialized for region: ap-southeast-2
Successfully retrieved secret: prod-database-credentials
```

## Troubleshooting

### Build fails with "Unable to load credentials"

**Cause:** `.env` file missing or not readable in Jenkins workspace

**Fix:** Create the `.env` file in the Jenkins workspace as shown above

### Build fails with "Service must be state=active"

**Cause:** Rancher service still updating from previous deployment

**Fix:** Retry logic will handle this automatically (up to 5 attempts). If still failing, wait a few minutes and re-run the build.

### Application fails to start - "Secret not found"

**Cause:** AWS secret doesn't exist or incorrect name

**Fix:**
```bash
# Create the production secret
aws secretsmanager create-secret \
    --name prod-database-credentials \
    --secret-string '{"USER_NAME":"postgres","PASSWORD":"your-prod-password"}' \
    --region ap-southeast-2

# Create email credentials secret
aws secretsmanager create-secret \
    --name email-credentials \
    --secret-string '{"USER_NAME":"your-email@gmail.com","PASSWORD":"your-app-password"}' \
    --region ap-southeast-2
```

## Files Modified

- ✅ `Jenkinsfile` - Preserve .env, improved checkout
- ✅ `deploy.sh` - Load AWS credentials, add retry logic (already done earlier)
- ✅ `prd/docker-compose-prd.yml` - AWS env vars (already done earlier)
- ✅ `lcl/docker-compose-lcl.yml` - AWS env vars (already done earlier)

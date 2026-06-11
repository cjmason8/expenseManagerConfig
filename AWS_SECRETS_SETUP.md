# AWS Secrets Manager Setup Guide

## Prerequisites

- AWS CLI installed and configured
- AWS account with Secrets Manager access
- IAM permissions to create secrets

## Step 1: Create AWS Secrets

Run these commands to create the required secrets in AWS Secrets Manager:

### 1. Email Credentials

```bash
aws secretsmanager create-secret \
    --name email-credentials \
    --description "Email credentials for expense manager EmailTrawler" \
    --secret-string '{
        "USER_NAME": "cjmason8bills@gmail.com",
        "PASSWORD": "uqloyrigcyqyncbh"
    }' \
    --region ap-southeast-2
```

### 2. Production Database Credentials

```bash
aws secretsmanager create-secret \
    --name prod-database-credentials \
    --description "Production database credentials for expense manager" \
    --secret-string '{
        "USER_NAME": "postgres",
        "PASSWORD": "postgres2"
    }' \
    --region ap-southeast-2
```

### 3. Local Database Credentials

```bash
aws secretsmanager create-secret \
    --name local-database-credentials \
    --description "Local/development database credentials" \
    --secret-string '{
        "USER_NAME": "postgres",
        "PASSWORD": "Yoke1976%"
    }' \
    --region ap-southeast-2
```

## Step 2: Verify Secrets Created

```bash
# List all secrets
aws secretsmanager list-secrets --region ap-southeast-2

# Verify email credentials
aws secretsmanager get-secret-value \
    --secret-id email-credentials \
    --region ap-southeast-2 \
    --query SecretString \
    --output text | jq .

# Verify prod database credentials
aws secretsmanager get-secret-value \
    --secret-id prod-database-credentials \
    --region ap-southeast-2 \
    --query SecretString \
    --output text | jq .

# Verify local database credentials
aws secretsmanager get-secret-value \
    --secret-id local-database-credentials \
    --region ap-southeast-2 \
    --query SecretString \
    --output text | jq .
```

## Step 3: Create IAM Policy

Create an IAM policy for accessing these secrets:

```bash
cat > expense-manager-secrets-policy.json <<'EOF'
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "ReadExpenseManagerSecrets",
            "Effect": "Allow",
            "Action": [
                "secretsmanager:GetSecretValue",
                "secretsmanager:DescribeSecret"
            ],
            "Resource": [
                "arn:aws:secretsmanager:ap-southeast-2:*:secret:email-credentials*",
                "arn:aws:secretsmanager:ap-southeast-2:*:secret:prod-database-credentials*",
                "arn:aws:secretsmanager:ap-southeast-2:*:secret:local-database-credentials*"
            ]
        }
    ]
}
EOF

# Create the policy
aws iam create-policy \
    --policy-name ExpenseManagerSecretsAccess \
    --policy-document file://expense-manager-secrets-policy.json \
    --description "Allow expense manager to read secrets from Secrets Manager"
```

## Step 4: Attach Policy to IAM User/Role

### For IAM User (e.g., for Jenkins):

```bash
# Replace with your IAM username
aws iam attach-user-policy \
    --user-name jenkins-user \
    --policy-arn arn:aws:iam::YOUR_ACCOUNT_ID:policy/ExpenseManagerSecretsAccess
```

### For IAM Role (e.g., for EC2/ECS):

```bash
# Replace with your role name
aws iam attach-role-policy \
    --role-name expense-manager-role \
    --policy-arn arn:aws:iam::YOUR_ACCOUNT_ID:policy/ExpenseManagerSecretsAccess
```

## Step 5: Set AWS Credentials in Jenkins

### Option A: Environment Variables (Global)

1. Go to **Manage Jenkins** > **Configure System**
2. Scroll to **Global properties**
3. Check **Environment variables**
4. Add:
   - Name: `AWS_ACCESS_KEY_ID`, Value: `<your-access-key>`
   - Name: `AWS_SECRET_ACCESS_KEY`, Value: `<your-secret-key>`

### Option B: Jenkins Credentials (Recommended)

1. Go to **Manage Jenkins** > **Credentials**
2. Add **AWS Credentials** (if plugin installed) or **Secret text**
3. Update Jenkinsfile to use credentials:

```groovy
stage('Deploy') {
    withCredentials([
        string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
        string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_ACCESS_KEY')
    ]) {
        sh "./deploy.sh ${rancher_access} ${rancher_secret} ${rancher_url} prd"
    }
}
```

## Step 6: Update Production Environment

Ensure these environment variables are set in production docker-compose:

```yaml
environment:
  AWS_ACCESS_KEY_ID: ${AWS_ACCESS_KEY_ID}
  AWS_SECRET_ACCESS_KEY: ${AWS_SECRET_ACCESS_KEY}
  AWS_SECRETS_ENABLED: true
  AWS_SECRETS_REGION: ap-southeast-2
```

## Testing Locally

To test AWS Secrets Manager integration locally:

### 1. Set AWS Credentials

```bash
export AWS_ACCESS_KEY_ID=your-key
export AWS_SECRET_ACCESS_KEY=your-secret
export AWS_SECRETS_ENABLED=true
export AWS_SECRETS_REGION=ap-southeast-2
export ENV=local
```

### 2. Run Application

```bash
cd expenseManager
mvn spring-boot:run
```

### 3. Verify Connection

Check logs for:
```
AWS Secrets Manager client initialized for region: ap-southeast-2
Successfully retrieved secret: email-credentials
Successfully retrieved secret: local-database-credentials
```

## Updating Secrets

To update a secret value:

```bash
# Update email password
aws secretsmanager update-secret \
    --secret-id email-credentials \
    --secret-string '{
        "USER_NAME": "cjmason8bills@gmail.com",
        "PASSWORD": "new-password-here"
    }' \
    --region ap-southeast-2

# Restart application to pick up new value
docker restart expense-manager
```

## Secret Rotation (Optional)

Enable automatic rotation for database credentials:

```bash
aws secretsmanager rotate-secret \
    --secret-id prod-database-credentials \
    --rotation-lambda-arn arn:aws:lambda:ap-southeast-2:YOUR_ACCOUNT:function:SecretsManagerRotation \
    --rotation-rules AutomaticallyAfterDays=30 \
    --region ap-southeast-2
```

## Troubleshooting

### Secret Not Found

```bash
# Check if secret exists
aws secretsmanager describe-secret \
    --secret-id email-credentials \
    --region ap-southeast-2
```

If not found, create it using commands in Step 1.

### Access Denied

```bash
# Check IAM permissions
aws iam get-user-policy \
    --user-name jenkins-user \
    --policy-name ExpenseManagerSecretsAccess

# Or for roles
aws iam get-role-policy \
    --role-name expense-manager-role \
    --policy-name ExpenseManagerSecretsAccess
```

### Wrong Region

Ensure you're using **ap-southeast-2** (Sydney) region:
- In AWS CLI commands: `--region ap-southeast-2`
- In application config: `AWS_SECRETS_REGION=ap-southeast-2`

### Application Can't Connect

Check application logs:
```bash
docker logs expense-manager 2>&1 | grep -i "secret\|aws"
```

Look for:
- "AWS Secrets Manager client initialized" (should see this)
- "Successfully retrieved secret" (should see for each secret)
- "Failed to retrieve secret" (indicates problem)

## Security Best Practices

1. **Use IAM Roles** instead of access keys when possible (EC2, ECS, Lambda)
2. **Rotate secrets regularly** (at least every 90 days)
3. **Enable CloudTrail** to audit secret access
4. **Use least privilege** - only grant access to specific secrets needed
5. **Never commit** AWS credentials to version control
6. **Enable MFA** for IAM users with Secrets Manager access
7. **Use encryption** - Secrets Manager encrypts at rest by default
8. **Monitor access** using CloudWatch Logs

## Cost Estimate

AWS Secrets Manager pricing (as of 2026):
- $0.40 per secret per month
- $0.05 per 10,000 API calls

For 3 secrets with minimal API calls:
- **Monthly cost**: ~$1.20 - $2.00

Much cheaper than managing encrypted values and encryption keys!

## Cleanup (If Needed)

To delete secrets (use with caution):

```bash
# Schedule deletion (can recover within 7-30 days)
aws secretsmanager delete-secret \
    --secret-id email-credentials \
    --recovery-window-in-days 7 \
    --region ap-southeast-2

# Force immediate deletion (cannot recover!)
aws secretsmanager delete-secret \
    --secret-id email-credentials \
    --force-delete-without-recovery \
    --region ap-southeast-2
```

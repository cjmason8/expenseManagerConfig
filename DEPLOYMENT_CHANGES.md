# Deployment Changes - AWS Secrets Manager Migration

## Changes Made

### 1. Removed Environment Variables
The following encrypted environment variables are **no longer used**:
- `REQUIRED_INFO` - Email password
- `REQ_ACCOUNT` - Email account  
- `ALPHA_VEC` - Encryption key
- `DB_USER` - Database username (when commented out)
- `DB_PASS` - Database password (when commented out)

### 2. Required AWS Environment Variables
These must be set in Jenkins or deployment environment:
- `AWS_ACCESS_KEY_ID` - AWS credentials for Secrets Manager access
- `AWS_SECRET_ACCESS_KEY` - AWS secret key for Secrets Manager access

### 3. Application Configuration
Added to `prd/docker-compose-prd.yml`:
```yaml
AWS_SECRETS_ENABLED: true
AWS_SECRETS_REGION: ap-southeast-2
```

### 4. Required AWS Secrets

Create these secrets in AWS Secrets Manager (ap-southeast-2 region):

#### `email-credentials`
```bash
aws secretsmanager create-secret \
    --name email-credentials \
    --description "Email credentials for EmailTrawler" \
    --secret-string '{
        "USER_NAME": "cjmason8bills@gmail.com",
        "PASSWORD": "uqloyrigcyqyncbh"
    }' \
    --region ap-southeast-2
```

#### `prod-database-credentials`
```bash
aws secretsmanager create-secret \
    --name prod-database-credentials \
    --description "Production database credentials" \
    --secret-string '{
        "USER_NAME": "postgres",
        "PASSWORD": "postgres2"
    }' \
    --region ap-southeast-2
```

#### `local-database-credentials`
```bash
aws secretsmanager create-secret \
    --name local-database-credentials \
    --description "Local/dev database credentials" \
    --secret-string '{
        "USER_NAME": "postgres",
        "PASSWORD": "Yoke1976%"
    }' \
    --region ap-southeast-2
```

## Jenkins Configuration

### Required Jenkins Credentials/Environment Variables

Set these in Jenkins (Manage Jenkins > Configure System > Global Properties > Environment variables):

1. **AWS_ACCESS_KEY_ID** - IAM user access key with Secrets Manager permissions
2. **AWS_SECRET_ACCESS_KEY** - Corresponding secret key

Or add to Jenkins credentials and inject in pipeline.

### IAM Permissions Required

The IAM user/role needs these permissions:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
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
```

## Deployment Process

1. **Ensure AWS Secrets exist** (run commands above)
2. **Set AWS credentials in Jenkins**
3. **Run Jenkins pipeline** - it will build and deploy with new version
4. **Verify application starts** and can access secrets

## Troubleshooting

### Error: "Could not parse config for project"
**Cause**: Invalid docker-compose syntax (like `${VAR:-default}`)
**Fix**: Use simple `${VAR}` syntax only

### Error: "The AWS_ACCESS_KEY_ID variable is not set"
**Cause**: AWS credentials not set in Jenkins
**Fix**: Add AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY to Jenkins environment

### Error: "Cannot access secret: email-credentials"
**Cause**: Secret doesn't exist or wrong AWS permissions
**Fix**: 
1. Verify secret exists: `aws secretsmanager list-secrets --region ap-southeast-2`
2. Check IAM permissions
3. Create secret if missing

### Error: "Null input buffer" or "EncryptionService.decrypt"
**Cause**: Old Docker image still has EncryptionService
**Fix**: Rebuild and redeploy (Jenkins will handle this automatically)

## Docker Compose Syntax Notes

**DON'T USE** (not supported):
```yaml
AWS_S3_BUCKET: ${AWS_S3_BUCKET:-expense-manager-documents}  # ❌ Bash-style defaults
```

**USE INSTEAD**:
```yaml
AWS_S3_BUCKET: ${AWS_S3_BUCKET}  # ✅ Simple variable
```

Or set a default in the application:
```java
@Value("${aws.s3.bucket:expense-manager-documents}")
private String bucket;
```

## Rollback Procedure

If deployment fails and you need to rollback:

1. **Quick rollback** to previous version:
   ```bash
   cd /path/to/expenseManagerConfig
   export TAG_NAME=<previous-version>
   ./deploy.sh
   ```

2. **Temporary fix** - Re-add encrypted environment variables to `prd/docker-compose-prd.yml`:
   ```yaml
   DB_USER: postgres
   DB_PASS: 29XCSdu61eixtdTi2WIkiQ==  # Encrypted password
   REQUIRED_INFO: S4Eqz7962JO0qeRAfQdevwjQAfUilB86oZnyH0RD4zs=
   REQ_ACCOUNT: icMZGRCb+RbYUKI5RX7HaM33C3mLyertwbUl2RhYdt8=
   ALPHA_VEC: E66YYu84iW50GE66
   ```

3. **Restore EncryptionService** in code (from git history)

## Verification Commands

After deployment, verify everything works:

```bash
# Check container is running
docker ps | grep expense-manager

# Check logs for errors
docker logs expense-manager 2>&1 | grep -i "error\|exception" | tail -20

# Verify AWS Secrets Manager connection
docker logs expense-manager 2>&1 | grep -i "secret"

# Check database connection
docker logs expense-manager 2>&1 | grep -i "database"

# Health check
curl http://localhost:8080/health
```

## Files Changed

### In expenseManager repo:
- Removed: `src/main/java/.../service/EncryptionService.java`
- Updated: `src/main/java/.../config/DatabaseConfig.java`
- Updated: `src/main/java/.../robot/EmailTrawler.java`
- Updated: `src/main/resources/log4j2.xml`
- Added: `src/main/java/.../service/AwsSecretsService.java`

### In expenseManagerConfig repo:
- Updated: `prd/docker-compose-prd.yml`
- Added: `DEPLOYMENT_CHANGES.md` (this file)
- Added: `AWS_SECRETS_SETUP.md` (setup guide)

## Success Criteria

- ✅ Application starts without errors
- ✅ Database connection works via AWS Secrets
- ✅ Email trawler connects via AWS Secrets  
- ✅ No "EncryptionService" errors
- ✅ No hardcoded path errors in logs
- ✅ Health endpoint responds
- ✅ Jenkins pipeline completes successfully

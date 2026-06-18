#!/usr/bin/groovy

def project = "expenseManager"
def version = -1
def imageName = "expense-manager"

node {
    stage('Checkout') {
        // Clean up old files but preserve .env file
        sh '''
            # Remove all files except .env
            find . -mindepth 1 -maxdepth 1 ! -name '.env' -exec rm -rf {} +
        '''
        
        sh 'git clone git@github.com:cjmason8/expenseManagerConfig.git tmp_config'
        sh 'mv tmp_config/* tmp_config/.git* . 2>/dev/null || true'
        sh 'rm -rf tmp_config'
        
        sh 'git clone git@github.com:cjmason8/expenseManager.git'
    }

    stage('Format Check') {
        sh './mavenInDocker.sh spotless:check --no-transfer-progress'
    }

    stage('Test') {
        sh './mavenInDocker.sh test --no-transfer-progress'
    }

    stage('Update Version') {
        sh './updateVersion.sh'

        version = readFile('VERSION').trim()
    }

    stage('Build') {
        sh "./build.sh $imageName $version"
    }

    stage('Tag and Push') {
        sh "./tagAndPush.sh $imageName $version"
    }

    stage('Deploy') {
        withCredentials([usernamePassword(credentialsId: 'Rancher', passwordVariable: 'SECRETKEY', usernameVariable: 'ACCESSKEY')]) {
            sh './deploy.sh $ACCESSKEY $SECRETKEY http://161.97.133.187:8080/v2-beta/projects/1a5 prd'
        }
    }

}
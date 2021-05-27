#!/usr/bin/groovy

def project = "expenseManager"
def version = -1
def imageName = "expense-manager"

node {
    stage('Checkout') {
        sh './checkout.sh'
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
            sh './deploy.sh $ACCESSKEY $SECRETKEY http://167.86.68.204:8080/v2-beta/projects/1a5 prd'
        }
    }

    stage('Delete Workspace') {
        sh 'rm -rf {,.[!.],..?}*'
    }
}
#!/usr/bin/groovy

def project = "expenseManager"
def version = -1
def imageName = "expense-manager"

node {
    stage('Checkout') {
        def file = new File("checkout.sh")
        if (!file.exists()) {
            git(
                url: 'https://github.com/cjmason8/expenseManagerConfig.git',
                credentialsId: 'Github',
                branch: "master"
            )
            dir('expenseManager') {
                git(
                    url: 'https://github.com/cjmason8/expenseManager.git',
                    credentialsId: 'Github',
                    branch: "master"
                )    
            }
        }

        withCredentials([usernamePassword(credentialsId: 'Github', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh './checkout.sh $PASSWORD expenseManager'
        }
    }

    stage('Update Version') {
        withCredentials([usernamePassword(credentialsId: 'Github', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh './updateVersion.sh $PASSWORD expenseManager'
        }

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
            sh './deploy.sh $ACCESSKEY $SECRETKEY http://80.241.221.122:8080/v2-beta/projects/1a5 prd'
        }
    }
}
#!/usr/bin/groovy

def project = "expenseManager"
def version = -1
def imageName = "expense-manager"

node {
    stage('Checkout') {
        def file = new File("checkout.sh")
        if (!file.exists()) {
            git(
                url: 'https://github.com/cjmason8/$projectConfig.git',
                credentialsId: 'Github',
                branch: "master"
            )
            dir($project) {
                git(
                    url: 'https://github.com/cjmason8/$project.git',
                    credentialsId: 'Github',
                    branch: "master"
                )    
            }
        }

        withCredentials([usernamePassword(credentialsId: 'Github', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh './checkout.sh $PASSWORD $project'
        }
    }

    stage('Update Version') {
        withCredentials([usernamePassword(credentialsId: 'Github', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh './updateVersion.sh $PASSWORD $project'
        }

        version = readFile('VERSION').trim()
    }

    stage('Build') {
        sh "./build.sh $imageName $version"
    }

    stage('Tag and Push') {
        sh "./tagAndPush.sh $imageName $version"
    }
}
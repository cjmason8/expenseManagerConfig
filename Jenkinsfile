def project = "expenseManager"
def version = -1
def imageName = "expense-manager"

node {
    stage('Checkout') {
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
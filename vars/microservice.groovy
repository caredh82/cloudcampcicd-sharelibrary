#!/usr/bin/env groovy
//comment
node ('docker&&linux'){
    checkoutFromRepo('main','https://github.com/caredh82/cloudcampcicd.git','git-credentials')

    buildDockerFile('hello-world-python')

    pushDockerFile('us-east-1','851725481871.dkr.ecr.us-east-1.amazonaws.com','hello-world-python','ch.v.0.1')
}

// Metodos
def checkoutFromRepo(branch, repoURL, credentialsId) {
    stage('Checkout') {
        checkout scmGit(
                        branches:
                        [[name: branch]],
                        extensions: [],
                        userRemoteConfigs: [[url: repoURL, credentialsId: credentialsId]]
                        )
    }
}

def buildDockerFile(tag, context=".", fileArg=""){
    if (fileArg=="") {
        path=""
    }
    else{
        path="-f ${fileArg}"
    }
    stage('Build'){
        sh "exec docker build -t ${tag} ${path} ${context}"
    }
}

def pushDockerFile(region, registryURL, appName, appVersion){
     stage ('Push') {
        sh "aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${registryURL}"
        sh "docker tag ${appName}:latest ${registryURL}/${appName}:${appVersion}"
        sh "docker tag ${appName}:latest ${registryURL}/${appName}:latest"
        sh "docker push --all-tags ${registryURL}/${appName}"
        
    }

}

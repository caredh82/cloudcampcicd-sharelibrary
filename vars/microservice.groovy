#!/usr/bin/env groovy
def call(){
    //comment
    node ('docker&&linux'){
        checkoutFromRepo(branch: 'main',repoURL:'https://github.com/caredh82/cloudcampcicd.git',credentialsId:'git-credentials')

        buildDockerFile(tag: 'hello-world-python' ,fileArg: '', path:'Dockerfile', context: './')

        pushDockerFile(region: 'us-east-1',registryURL: '851725481871.dkr.ecr.us-east-1.amazonaws.com',appName: 'hello-world-python',appVersion: 'ch.v.0.1')
    }

}

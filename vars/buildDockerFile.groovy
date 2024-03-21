def call(Map parameters){
// Metodos
    if (parameters.fileArg=="") {
        path=""
    }
    else{
        path="-f ${parameters.fileArg}"
    }
    stage('Build'){
        sh "exec docker build -t ${parameters.tag} ${path} ${parameters.context}"
    }
}

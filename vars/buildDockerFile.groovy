def call(Map parameters){
// Metodos
    if (fileArg=="") {
        path=""
    }
    else{
        path="-f ${fileArg}"
    }
    stage('Build'){
        sh "exec docker build -t ${parameters.tag} ${path} ${parameters.context}"
    }
}

pipeline {
    agent any
    tools {
        maven 'maven 3.9.14'
    }
    
    stages {
        stage('Check Maven Version') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}

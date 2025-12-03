pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saladin-scs/TP-FOYER.git'
            }
        }
        stage('Build Maven') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('version') {
            steps {
                sh 'mvn -version'
            }
        }
        stage('Hello') {
            steps {
                echo 'Pipeline exécuté via Jenkinsfile !'
            }
        }
    }
}

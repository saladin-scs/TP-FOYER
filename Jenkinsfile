pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token-id')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saladin-scs/TP-FOYER.git'
            }
        }

        stage('Maven SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=TP1 \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }
    }
}

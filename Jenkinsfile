pipeline {
    agent any

    environment {
        // Docker
        REGISTRY = "localhost:5000"
        IMAGE_NAME = "myubuntu"
        DOCKERHUB = "khalfaouisaladin"

        // SonarQube token stocké dans Jenkins Credentials
        SONAR_TOKEN = credentials('sonar-token-id')
    }

    triggers {
        pollSCM('H/5 * * * *') // Vérifie le repo toutes les 5 minutes
    }

    stages {

        // ---------------------- GIT ----------------------
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saladin-scs/TP-FOYER.git'
            }
        }

        // ---------------------- BUILD MAVEN ----------------------
        stage('Build Maven') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        // ---------------------- SONARQUBE ----------------------
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

        // ---------------------- DOCKER BUILD ----------------------
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }

        // ---------------------- PUSH LOCAL REGISTRY ----------------------
        stage('Push Local Registry') {
            steps {
                sh 'docker tag $IMAGE_NAME:latest $REGISTRY/$IMAGE_NAME:latest'
                sh 'docker push $REGISTRY/$IMAGE_NAME:latest'
            }
        }

        // ---------------------- PUSH DOCKER HUB ----------------------
        stage('Push Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials',
                        usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh 'docker tag $IMAGE_NAME:latest $DOCKERHUB/$IMAGE_NAME:latest'
                    sh 'docker push $DOCKERHUB/$IMAGE_NAME:latest'
                }
            }
        }
    }

    // ---------------------- CLEANUP ----------------------
    post {
        always {
            sh 'docker logout'
        }
    }
}

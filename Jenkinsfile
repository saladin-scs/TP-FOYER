pipeline {
    agent any
    environment {
        REGISTRY = "localhost:5000"
        IMAGE_NAME = "myubuntu"
        DOCKERHUB = "khalfaouisaladin"
    }
    triggers {
        pollSCM('H/5 * * * *') // Vérifie tous les 5 min les nouveaux commits
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saladin-scs/TP-FOYER.git'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }
        stage('Push Local Registry') {
            steps {
                sh 'docker tag $IMAGE_NAME:latest $REGISTRY/$IMAGE_NAME:latest'
                sh 'docker push $REGISTRY/$IMAGE_NAME:latest'
            }
        }
        stage('Push Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh 'docker tag $IMAGE_NAME:latest $DOCKERHUB/$IMAGE_NAME:latest'
                    sh 'docker push $DOCKERHUB/$IMAGE_NAME:latest'
                }
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
    }
}

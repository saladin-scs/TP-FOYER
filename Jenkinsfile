pipeline {
    agent any
    environment {
        REGISTRY = "localhost:5000"
        IMAGE_NAME = "myubuntu"
        DOCKERHUB = "khalfaouisaladin"
    }
    triggers {
        pollSCM('H/5 * * * *') // Vérifie toutes les 5 minutes pour nouveaux commits
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saladin-scs/TP-FOYER.git'
            }
        }
        stage('Clean & Build Project') {
            steps {
                echo "Nettoyage et reconstruction du projet..."
                sh 'rm -rf build || true'
                sh 'mkdir -p build'
                sh 'touch build/dummy'  // Simule un build
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }
        stage('Tag & Push Local Registry') {
            steps {
                sh 'docker tag $IMAGE_NAME:latest $REGISTRY/$IMAGE_NAME:latest'
                sh 'docker push $REGISTRY/$IMAGE_NAME:latest'
            }
        }
        stage('Tag & Push Docker Hub') {
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

pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:${BUILD_NUMBER}"
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
    }

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo "📦 Build and run tests..."
                sh 'mvn clean compile test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "🔍 Running SonarQube scan..."
                withSonarQubeEnv('sonar-token-id') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Building Docker image..."
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                    docker login -u $DOCKER_USER -p $DOCKER_PASS
                    docker build -t ${IMAGE_NAME} .
                    docker push ${IMAGE_NAME}
                    docker images | grep ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo "🚀 Deploying to Kubernetes..."
                sh """
                # Apply YAMLs
                kubectl apply -f ./mysql.yaml --validate=false
                kubectl apply -f ./spring.yaml --validate=false

                # Update Spring deployment image
                kubectl set image deployment/spring-app spring-app=${IMAGE_NAME} -n devops

                # Wait for rollout
                kubectl rollout status deployment/spring-app -n devops || exit 1

                # Show pods and services
                kubectl get pods -n devops
                kubectl get svc -n devops
                """
            }
        }
    }

    post {
        success {
            echo "✅ Kubernetes deployment successful!"
            // Optional: Slack notification
            // slackSend(channel: '#devops', message: "Deployment succeeded: ${IMAGE_NAME}")
        }
        failure {
            echo "❌ Kubernetes deployment failed!"
            // Optional rollback
            sh 'kubectl rollout undo deployment/spring-app -n devops'
            // Optional: Slack notification
            // slackSend(channel: '#devops', message: "Deployment failed: ${IMAGE_NAME}")
        }
    }
}

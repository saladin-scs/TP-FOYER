pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:${BUILD_NUMBER}"
        KUBECONFIG = "/var/lib/jenkins/.kube/config" // Use your working kubeconfig
    }

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                echo "🔧 Building Docker image..."
                docker build -t ${IMAGE_NAME} .
                docker images | grep ${IMAGE_NAME}
                """
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh """
                echo "🚀 Deploying to Kubernetes..."

                # Apply YAMLs (make sure no tabs, only spaces)
                kubectl apply -f ./mysql.yaml --validate=false
                kubectl apply -f ./spring.yaml --validate=false

                # Update the Spring deployment image
                kubectl set image deployment/spring-app spring-app=${IMAGE_NAME} -n devops

                # Wait for rollout to complete
                kubectl rollout status deployment/spring-app -n devops

                # Show pods
                kubectl get pods -n devops
                """
            }
        }
    }

    post {
        success {
            echo "✅ Kubernetes deployment successful!"
        }
        failure {
            echo "❌ Kubernetes deployment failed!"
        }
    }
}

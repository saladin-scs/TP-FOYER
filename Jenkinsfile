pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:latest"
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        SONAR_TOKEN = credentials('Sonar_Token')
    }

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', 'docker-hub-credentials') {
                        docker.image("${IMAGE_NAME}").push()
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn clean verify sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig-devops', variable: 'KUBECONFIG')]) {
                    script {
                        // Apply manifests
                        sh """
                        kubectl apply -f ${WORKSPACE}/PipelineKubernities/mysql.yaml --validate=false || echo "mysql.yaml missing, skipping..."
                        kubectl apply -f ${WORKSPACE}/PipelineKubernities/spring.yaml --validate=false || echo "spring.yaml missing, skipping..."
                        
                        # Update image
                        kubectl set image deployment/spring-app spring-app=${IMAGE_NAME} -n devops || echo "Skipping set image"
                        
                        # Rollout status
                        kubectl rollout status deployment/spring-app -n devops || echo "Rollout failed"
                        
                        # Show pods
                        kubectl get pods -n devops
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Check the logs!"
        }
    }
}

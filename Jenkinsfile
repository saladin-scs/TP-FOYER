pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:${BUILD_NUMBER}"
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        SONAR_TOKEN = credentials('sonar-token-id')
    }

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn verify sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}")
                    docker.withRegistry('', 'docker-hub-credentials') {
                        docker.image("${IMAGE_NAME}").push()
                        docker.image("${IMAGE_NAME}").push("latest")
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig-devops', variable: 'KUBECONFIG')]) {
                    script {
                        // Option 1: Update image dynamically
                        sh """
                        kubectl apply -f ${WORKSPACE}/PipelineKubernities/mysql.yaml --validate=false || echo "mysql.yaml missing, skipping..."
                        kubectl apply -f ${WORKSPACE}/PipelineKubernities/spring.yaml --validate=false || echo "spring.yaml missing, skipping..."
                        kubectl set image deployment/spring-app spring-app=${IMAGE_NAME} -n devops
                        kubectl rollout status deployment/spring-app -n devops
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

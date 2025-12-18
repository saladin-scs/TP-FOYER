pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:${BUILD_NUMBER}"
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
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh "mvn verify sonar:sonar -DskipTests -Dsonar.login=${SONAR_TOKEN}"
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
                    sh """
                    kubectl apply -f PipelineKubernities/mysql.yaml --validate=false || true
                    kubectl apply -f PipelineKubernities/spring.yaml --validate=false || true
                    kubectl set image deployment/spring-app spring-app=${IMAGE_NAME} -n devops
                    kubectl rollout status deployment/spring-app -n devops
                    kubectl get pods -n devops
                    """
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

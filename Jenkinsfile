pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:latest"
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials') // keep your credentials
        KUBECONFIG = "/home/jenkins/.kube/config"
    }

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    docker.build(IMAGE_NAME)
                }
            }
        }

        stage('Load Image to Minikube') {
            steps {
                script {
                    sh "minikube status || exit 1"
                    sh "minikube image load ${IMAGE_NAME}"
                }
            }
        }

        stage('Docker Login & Push') {
            steps {
                script {
                    sh """
                        echo ${DOCKER_CREDENTIALS_PSW} | docker login -u ${DOCKER_CREDENTIALS_USR} --password-stdin
                        docker push ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withEnv(["KUBECONFIG=$KUBECONFIG"]) {
                    // Apply MySQL and Spring YAML files
                    sh "kubectl apply -f ${WORKSPACE}/mysql.yaml || echo 'mysql.yaml missing, skipping...'"
                    sh "kubectl apply -f ${WORKSPACE}/spring.yaml || echo 'spring.yaml missing, skipping...'"

                    // Update Spring Boot image directly
                    sh "kubectl set image deployment/spring-app spring-app=${IMAGE_NAME} -n devops || echo 'Skipping set image'"

                    // Wait for rollout to complete
                    sh "kubectl rollout status deployment/spring-app -n devops || echo 'Rollout failed'"

                    // List pods to verify
                    sh "kubectl get pods -n devops"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Déploiement terminé avec succès !"
        }
        failure {
            echo "❌ La pipeline a échoué. Vérifier les logs."
        }
    }
}

pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:latest"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-creds') // ID des credentials Docker Hub dans Jenkins
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
                dir('StudentsManagement-DevOps') { // adjust if your Maven project is in another folder
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Docker Build') {
            steps {
                dir('StudentsManagement-DevOps') { // adjust to your Dockerfile location
                    sh "docker build -t ${IMAGE_NAME} ."
                }
            }
        }

        stage('Docker Login & Push') {
            steps {
                script {
                    sh """
                        echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin
                        docker push ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Load Image to Minikube') {
            steps {
                script {
                    // Vérifie si minikube est accessible
                    sh "minikube status || exit 1"
                    // Charge l'image dans Minikube
                    sh "minikube image load ${IMAGE_NAME}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withEnv(["KUBECONFIG=$KUBECONFIG"]) {
                    // Apply MySQL and Spring Boot deployment YAMLs
                    sh "kubectl apply -f ${WORKSPACE}/mysql.yaml || echo 'mysql.yaml missing, skipping...'"
                    sh "kubectl apply -f ${WORKSPACE}/spring.yaml || echo 'spring.yaml missing, skipping...'"

                    // Optionally update Spring Boot image
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
            echo "✅ Pipeline terminée avec succès !"
        }
        failure {
            echo "❌ La pipeline a échoué. Vérifier les logs !"
        }
    }
}

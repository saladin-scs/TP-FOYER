pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:latest"
        DOCKER_CREDENTIALS = "docker-hub-credentials"
        KUBECONFIG = "/home/jenkins/.kube/config"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saladin-scs/TP-FOYER.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS) {
                        def appImage = docker.build(IMAGE_NAME)
                        appImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withEnv(["KUBECONFIG=$KUBECONFIG"]) {
                    sh 'kubectl set image deployment/spring-app spring-app=$IMAGE_NAME -n devops'
                    sh 'kubectl rollout status deployment/spring-app -n devops'
                    sh 'kubectl get pods -n devops'
                }
            }
        }
    }

    post {
        success {
            echo "✅ Déploiement terminé avec succès !"
        }
        failure {
            echo "❌ Le pipeline a échoué. Vérifier les logs."
        }
    }
}

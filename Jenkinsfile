pipeline {
    agent any

    environment {
        IMAGE_NAME = "khalfaouisaladin/myubuntu:${BUILD_NUMBER}"
    }

    stages {

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig-devops', variable: 'KUBECONFIG')]) {
                    sh '''
                    echo "🚀 Deploying to Kubernetes..."

                    kubectl apply -f PipelineKubernities/mysql.yaml --validate=false
                    kubectl apply -f PipelineKubernities/spring.yaml --validate=false

                    kubectl set image deployment/spring-app spring-app=${IMAGE_NAME} -n devops
                    kubectl rollout status deployment/spring-app -n devops

                    kubectl get pods -n devops
                    '''
                }
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

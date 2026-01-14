pipeline {
    agent any

    environment {
        IMAGE_NAME = "docker.io/khalfaouisaladin/tp-foyer"
        SONAR_HOST = "http://localhost:9000" // ton serveur SonarQube
    }

    stages {

        stage('Checkout SCM') {
            steps {
                echo "🔄 Checking out source code..."
                git branch: 'main', url: 'https://github.com/saladin-scs/TP-FOYER.git'
            }
        }

        stage('Build with Maven') {
            steps {
                echo "📦 Building project with Maven..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('sonar-token-id') // ID du credential SonarQube
            }
            steps {
                echo "🔍 Running SonarQube analysis (errors will be ignored)..."
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    sh """
                        mvn sonar:sonar \
                            -Dsonar.projectKey=TP-FOYER \
                            -Dsonar.host.url=${SONAR_HOST} \
                            -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

stage('Build & Push Docker Image') {
    steps {
        echo "🐳 Building and pushing Docker image..."
        withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
            sh """
                docker login -u $DOCKER_USER -p $DOCKER_PASS
                docker build -t khalfaouisaladin/tp-foyer:${BUILD_NUMBER} .
                docker tag khalfaouisaladin/tp-foyer:${BUILD_NUMBER} khalfaouisaladin/tp-foyer:latest
                docker push khalfaouisaladin/tp-foyer:${BUILD_NUMBER} > /dev/null
                docker push khalfaouisaladin/tp-foyer:latest > /dev/null
                echo "✅ Docker image pushed successfully!"
            """
        }
    }
}


        stage('Run Docker Container') {
            steps {
                echo "🚀 Running Docker container locally..."
                sh """
                    docker stop tp-foyer || true
                    docker rm tp-foyer || true
                    docker run -d --name tp-foyer -p 8081:8080 ${IMAGE_NAME}:${BUILD_NUMBER}
                    docker ps | grep tp-foyer
                """
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed! Check logs for details."
        }
        unstable {
            echo "⚠️ SonarQube analysis failed, but pipeline continued."
        }
    }
}

pipeline {
    agent any
    tools {
        maven 'Maven-3.8.1'
        jdk 'JDK-11'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Devil2005-de/swetha.git'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Docker Build & Push') {
            steps {
                script {
                    docker.build("harika1305/library-management:latest")
                    docker.withRegistry('', 'docker-hub-credentials') {
                        docker.image("harika1305/library-management:latest").push()
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f deployment.yaml'
                sh 'kubectl rollout status deployment/library-deployment'
            }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
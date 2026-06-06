pipeline {

    agent any

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url 'https://github.com/nicholasaffum/jenkinds_Demo2026.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Verify Target Folder') {
            steps {
                sh 'ls -lrt target'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t sample-java-app .'
            }
        }

        stage('Remove Old Container') {
            steps {
                sh 'docker rm -f java-container || true'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker run -d -p 8000:8000 --name java-container sample-java-app'
            }
        }

        stage('Verify Container') {
            steps {
                sh 'docker ps'
            }
        }
    }

    post {

        success {
            echo 'Pipeline executed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}

pipeline {

    agent any

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/nicholasaffum/jenkinds_Demo2026.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Maven unit test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube') {
            steps {
                // FIXED: Changed 'sonarsonar' to 'sonar:sonar'
                sh 'mvn sonar:sonar'
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

        stage('Docker Push') {
            steps {
                sh "docker push sample-java-app:2026.1"
            }
        }

        stage('Deploy to kubenet') {
            steps {
                sh "sed -i 's|IMAGE_TAG|2026.1|g' k8s/deployment.yaml"
                sh 'kubectl apply -f k8s/'
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

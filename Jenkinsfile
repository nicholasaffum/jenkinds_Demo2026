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
                // FIXED: Explicit plugin coordinates to resolve the prefix mapping error
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594:sonar'
            }
        }

        stage('Verify Target Folder') {
            steps {
                sh 'ls -lrt target'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t sample-java-app:2026.1 .'
            }
        }

        stage('Remove Old Container') {
            steps {
                sh 'docker rm -f java-container || true'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker run -d -p 8000:8000 --name java-container sample-java-app:2026.1'
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
                sh "sed -i 's|IMAGE_TAG|2026.1|g' deployment.yaml"
                sh 'kubectl apply -f deployment.yaml'
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

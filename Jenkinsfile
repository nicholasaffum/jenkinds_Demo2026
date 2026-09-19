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
                // FIXED: Tagged the image with 2026.1 during build to match the push stage
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
                // FIXED: Pointed to the newly matching tagged image
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
                // FIXED: Adjusted path from k8s/deployment.yaml to deployment.yaml to match repo root
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

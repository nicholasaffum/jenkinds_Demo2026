pipeline {

    agent any

    stages {

        stage('Maven Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube') {
            steps {
                withCredentials([string(
                    credentialsId: 'sonarqube-token',
                    variable: 'SONAR_TOKEN'
                )]) {
                    sh '''
                        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594:sonar \
                        -Dsonar.host.url=http://18.216.91.10:9000 \
                        -Dsonar.token="$SONAR_TOKEN"
                    '''
                }
            }
        }

        stage('Verify Target Folder') {
            steps {
                sh 'ls -lrt target'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t nicholasaffum/sample-java-app:2026.1 .'
            }
        }

        stage('Remove Old Container') {
            steps {
                sh 'docker rm -f java-container || true'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh '''
                    docker run -d \
                    -p 8000:8080 \
                    --name java-container \
                    nicholasaffum/sample-java-app:2026.1
                '''
            }
        }

        stage('Verify Container') {
            steps {
                sh '''
                    docker ps
                    sleep 5
                    curl --fail http://localhost:8000
                '''
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login \
                        --username "$DOCKER_USERNAME" \
                        --password-stdin

                        docker push nicholasaffum/sample-java-app:2026.1
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
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

pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Build Maven (sans tests)') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/walidbensmida/harington-training']])
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Run Tests and Generate Report') {
            steps {
                script {
                    // Ajoutez ici le script pour exécuter vos tests et générer le rapport
                    sh 'mvn test'
                    // Ajoutez d'autres commandes si nécessaire pour générer le rapport
                }
            }
        }
        stage('Docker build and push image') {
            steps {
                  script {
                        // Docker Login
                         docker.withRegistry('', 'docker-hub-credentials') {
                         // Build Docker image
                         customImage = docker.build('waelbenammara/harington-training:tag', '--build-arg JAR_FILE=target/harington-training-0.0.1-SNAPSHOT.jar .')
                         // Push Docker image to Docker Hub
                         customImage.push()
                         }
                       }
                }
        }
    }
}

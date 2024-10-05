pipeline {
    agent any

    stages {
        stage('Prepare data base') {
            steps {
                sh 'docker compose up'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean test'
            }
        }
    }
}

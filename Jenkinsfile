pipeline {
    agent any

    stages {
        stage('Prepare data base') {
            steps {
                sh 'docker compose up -d'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean'
                sh './gradlew --info test'
            }
        }
    }

    post {
        always {
            sh 'docker compose down'
        }
    }
}

pipeline {
    agent {
        node {
            label 'dungeon-master'
        }
    }

    options {
        ansiColor('xterm')
    }

    environment {
        MONGO_NAME = "test-mongo-${env.BUILD_TAG}".toLowerCase()
        MASTER_NAME = "test-master-${env.BUILD_TAG}".toLowerCase()
        TESTS_NAME = "test-tests-${env.BUILD_TAG}".toLowerCase()
        COMPOSE_PROJECT_NAME = "${env.BUILD_TAG}".toLowerCase()
    }

    stages {
        stage('Build') {
            steps {
                script {
                    sh './gradlew clean'
                    sh './gradlew build'
                    sh './gradlew testJar'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh "docker compose up -d --build"
                    sh "docker logs -f $TESTS_NAME"
                    status = sh(script: "docker inspect $TESTS_NAME --format='{{.State.ExitCode}}'" returnStdout: true).trim() as Integer
                    if (status != 0) {
                        error("Tests failed")
                    }
                }
            }
        }
    }

    post {
        always {
            sh "docker compose down"
        }
    }
}

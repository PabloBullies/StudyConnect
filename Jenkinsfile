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
                    status = sh(script: "docker inspect $TESTS_NAME --format='{{.State.ExitCode}}'", returnStdout: true).trim() as Integer
                    if (status != 0) {
                        error("Tests failed")
                    }
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }

            steps {
                script {
                    sh 'docker build . -t study-master'
                    sh 'docker stop study-master-prod || true'
                    withCredentials([usernamePassword(credentialsId: 'mongo-prod-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "docker run --rm --name study-master-prod -p 8100:8080 -d study-master --spring.profiles.active=prod --mongodb.username=$USERNAME --mongodb.password=$PASSWORD"
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker compose down --rmi local'
            sh './gradlew --stop'
        }
    }
}

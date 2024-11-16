pipeline {
    agent {
        node {
            label 'dungeon-master'
        }
    }

    options {
        ansiColor('xterm')
    }

    parameters {
        booleanParam(defaultValue: false, name: 'sonar')
    }

    environment {
        MONGO_NAME = "mongo-${env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        MASTER_NAME = "master-${env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        TESTS_NAME = "tests-${env.BUILD_TAG.split('-')[-1]}".toLowerCase()
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

        stage('SonarQube analysis') {
            when {
                expression { params.sonar }
            }
            steps {
                withSonarQubeEnv('sonar-master') {
                    sh './gradlew sonar'
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
                    sh 'docker rm -f study-master-prod || true'
                    withCredentials([usernamePassword(credentialsId: 'mongo-prod-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "docker run --restart always --name study-master-prod --network master-prod-network -p 8100:8080 -d study-master --spring.profiles.active=prod --mongodb.username=$USERNAME --mongodb.password=$PASSWORD"
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker compose down --rmi local'
        }
    }
}

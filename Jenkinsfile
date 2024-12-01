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
        BRANCH_NAME = "${env.CHANGE_BRANCH == null ? env.BRANCH_NAME : env.CHANGE_BRANCH}"
        TIMESTAMP = sh(returnStdout: true, script: 'date +%Y.%m.%d-%k.%M.%S').trim()
        MONGO_NAME = "mongo-${env.BUILD_TAG.split('-')[-2] + env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        MASTER_NAME = "master-${env.BUILD_TAG.split('-')[-2] + env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        TESTS_NAME = "tests-${env.BUILD_TAG.split('-')[-2] + env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        COMPOSE_PROJECT_NAME = "${env.BUILD_TAG}".toLowerCase()
    }

    stages {
        stage('Build') {
            steps {
                script {
                    sh 'rm -rf .gradle'
                    sh 'gradle clean'
                    sh 'gradle build'
                    sh 'gradle itestJar'
                }
            }
        }

        stage('SonarQube analysis') {
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

        stage('Push to nexus') {
            when {
                branch 'main'
            }

            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'nexus-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "docker login -u $USERNAME -p $PASSWORD owa.gigachadus.ru"
                        sh "docker build . -t owa.gigachadus.ru/study-master:${env.TIMESTAMP}"
                        sh "docker push owa.gigachadus.ru/study-master:${env.TIMESTAMP}"
                        sh "docker tag owa.gigachadus.ru/study-master:${env.TIMESTAMP} owa.gigachadus.ru/study-master:latest"
                        sh "docker push owa.gigachadus.ru/study-master:latest"
                        sh "docker image rm owa.gigachadus.ru/study-master:${env.TIMESTAMP}"
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
                    sh 'docker rm -f study-master-prod || true'
                    withCredentials([usernamePassword(credentialsId: 'mongo-prod-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "docker run --restart always --name study-master-prod --network master-prod-network -p 8100:8080 -d owa.gigachadus.ru/study-master:latest --spring.profiles.active=prod --mongodb.username=$USERNAME --mongodb.password=$PASSWORD"
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

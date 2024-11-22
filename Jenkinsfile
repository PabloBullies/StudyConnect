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
        BRANCH_NAME = scm.branches[0].name
        MONGO_NAME = "mongo-${env.BUILD_TAG.split('-')[-2] + env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        MASTER_NAME = "master-${env.BUILD_TAG.split('-')[-2] + env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        TESTS_NAME = "tests-${env.BUILD_TAG.split('-')[-2] + env.BUILD_TAG.split('-')[-1]}".toLowerCase()
        COMPOSE_PROJECT_NAME = "${env.BUILD_TAG}".toLowerCase()
    }

    stages {
        stage('Build') {
            steps {
                script {
                    echo "$BRANCH_NAME"
                    sh 'gradle clean'
                    sh 'gradle build'
                    sh 'gradle testJar'
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

        stage('Push to nexus') {
            steps {
                script {
                    docker.withRegistry('https://owa.gigachadus.ru', 'nexus-creds') {
                        docker.build('study-master').push(BRANCH_NAME)
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
                        sh "docker run --restart always --name study-master-prod --network master-prod-network -p 8100:8080 -d owa.gigachadus.ru/study-master --spring.profiles.active=prod --mongodb.username=$USERNAME --mongodb.password=$PASSWORD"
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

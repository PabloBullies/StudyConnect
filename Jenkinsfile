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
        NETWORK_NAME = "test-network-${env.BUILD_TAG}".toLowerCase()
        MONGO_NAME = "test-mongo-${env.BUILD_TAG}".toLowerCase()
        MASTER_NAME = "test-master-${env.BUILD_TAG}".toLowerCase()
    }

    stages {
        stage('Build') {
            steps {
                script {
                    sh './gradlew clean build'
                    masterImageId = sh(script: 'docker build -q .', returnStdout: true).trim()

                    sh './gradlew clean testJar'
                    testImageId = sh(script: 'docker build -q -f Dockerfile.itest .', returnStdout: true).trim()
                }
            }
        }

        stage('Prepare data base') {
            steps {
                script {
                    sh "docker network create $NETWORK_NAME"
                    sh "docker run --rm --network $NETWORK_NAME --name $MONGO_NAME -e MONGO_INITDB_ROOT_USERNAME=username -e MONGO_INITDB_ROOT_PASSWORD=password -d mongo"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh "docker run --rm --network $NETWORK_NAME --name $MASTER_NAME -d $masterImageId --mongodb.host=$MONGO_NAME"
                    sh 'sleep 10'
                    sh "docker run --rm --network $NETWORK_NAME $testImageId '-Dmaster.host=http://${MASTER_NAME}:8080' -jar junit.jar --classpath test.jar --scan-classpath"
                }
            }
        }
    }

    post {
        always {
            sh "docker logs $MASTER_NAME"
            sh "docker stop $MONGO_NAME"
            sh "docker stop $MASTER_NAME"
            sh "docker network rm $NETWORK_NAME"
        }
    }
}

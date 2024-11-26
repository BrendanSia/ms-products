pipeline {
    agent any
    environment {
        IMAGE_NAME = 'ms-products'
        IMAGE_VERSION = '1.0'
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_USERNAME = 'brendansiach@gmail.com'
        DOCKER_PASSWORD = 'Bren@1130'
    }
    stages {
        stage('Build & Install') {
            steps {
                script {
                    logfile = 'pipeline.log'
                    try {
                        checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/BrendanSia/ms-products.git']])
                        sh "mvn clean install >> ${logfile} 2>&1"
                    } catch (Exception e) {
                        writeFile file: "${logfile}", text: "Failed in Build & Install stage: ${e.toString()}\n", append: true
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }
        stage('SonarQube analysis') {
            steps {
                script {
                    logfile = 'pipeline.log'
                    try {
                        withSonarQubeEnv(installationName: 'SQ1') {
                            sh "mvn clean package sonar:sonar >> ${logfile} 2>&1"
                        }
                    } catch (Exception e) {
                        writeFile file: "${logfile}", text: "Failed in SonarQube analysis stage: ${e.toString()}\n", append: true
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }
        stage('Docker Build & Deploy') {
            steps {
                script {
                    logfile = 'pipeline.log'
                    try {
                        sh "docker build -t ${IMAGE_NAME}:${IMAGE_VERSION} . >> ${logfile} 2>&1"

                        sh """
                            echo "$DOCKER_PASSWORD" | docker login ${DOCKER_REGISTRY} -u "$DOCKER_USERNAME" --password-stdin >> ${logfile} 2>&1
                        """

                        // Tag and Push Docker image
                        sh """
                            docker tag ${IMAGE_NAME}:${IMAGE_VERSION} ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_VERSION} >> ${logfile} 2>&1
                            docker push ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_VERSION} >> ${logfile} 2>&1
                        """

                        // checks and removes existing containers
                        sh """
                            docker ps -a --filter "name=${CONTAINER_NAME}" --format "{{.Names}}" | grep -q ${CONTAINER_NAME} && docker rm -f ${CONTAINER_NAME} || true
                        """

                        sh """
                             docker run -d --name ${CONTAINER_NAME} --restart=always -p 8081:8081 ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_VERSION} >> ${logfile} 2>&1
                        """
                    } catch (Exception e) {
                        writeFile file: "${logfile}", text: "Failed in Docker Build and Deploy stage: ${e.toString()}\n", append: true
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'pipeline.log', onlyIfSuccessful: false
        }
        failure {
            emailext subject: "Build Failure",
                      body: "The Jenkins build has failed. Please check the logs.",
                      to: "you@example.com"
        }
    }
}
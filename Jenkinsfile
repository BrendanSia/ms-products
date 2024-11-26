pipeline {
    agent any
    environment {
        IMAGE_NAME = 'ms-products'
        IMAGE_VERSION = '1.0'
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_USERNAME = 'brendansiach@gmail.com'
        DOCKER_PASSWORD = 'Bren@1130'
        KUBE_CONTEXT = 'docker-desktop'  // Kubernetes context
        KUBE_NAMESPACE = 'default'
        POD_NAME = 'ms-products-pod'
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
        stage('Docker Build') {
            steps {
                script {
                    logfile = 'pipeline.log'
                    try {
                        // Build Docker image
                        sh "docker build -t ${IMAGE_NAME}:${IMAGE_VERSION} . >> ${logfile} 2>&1"
                    } catch (Exception e) {
                        writeFile file: "${logfile}", text: "Failed in Docker Build stage: ${e.toString()}\n", append: true
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    logfile = 'pipeline.log'
                    try {
                        // Login to Docker registry
                        sh """
                            echo "$DOCKER_PASSWORD" | docker login ${DOCKER_REGISTRY} -u "$DOCKER_USERNAME" --password-stdin >> ${logfile} 2>&1
                        """

                        // Tag and Push Docker image
                        sh """
                            docker tag ${IMAGE_NAME}:${IMAGE_VERSION} ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_VERSION} >> ${logfile} 2>&1
                            docker push ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_VERSION} >> ${logfile} 2>&1
                        """
                    } catch (Exception e) {
                        writeFile file: "${logfile}", text: "Failed in Docker Push stage: ${e.toString()}\n", append: true
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }
        stage('Kubernetes Deployment') {
            steps {
                script {
                    logfile = 'pipeline.log'
                    try {
                        // Ensure kubectl is configured to use the correct Kubernetes context
                        sh """
                            kubectl config use-context ${KUBE_CONTEXT}
                        """

                        // Check if the pod already exists
                        def podExists = sh(script: "kubectl get pod ${POD_NAME} -n ${KUBE_NAMESPACE} --ignore-not-found=true", returnStatus: true) == 0

                        // If the pod exists, delete it
                        if (podExists) {
                            sh "kubectl delete pod ${POD_NAME} -n ${KUBE_NAMESPACE}"
                        }

                        // Deploy the new pod or update the deployment
                        sh """
                            kubectl run ${POD_NAME} --image=${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_VERSION} \
                            --restart=Always \
                            --port=8081 \
                            -n ${KUBE_NAMESPACE} \
                            --dry-run=client -o yaml | kubectl apply -f -
                        """
                    } catch (Exception e) {
                        writeFile file: "${logfile}", text: "Failed in Kubernetes Deployment stage: ${e.toString()}\n", append: true
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
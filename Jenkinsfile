pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/BrendanSia/ms-products.git']])
            }
        }
        stage('Jacoco Report') {
            steps {
                sh "mvn clean test"
            }
        }
    }
}
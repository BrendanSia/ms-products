pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/BrendanSia/ms-products.git']])
            }
        }
        stage('Install') {
            steps {
                sh "mvn clean install"
                sh "mvn jacoco:report"
            }
        }
    }
    post {
        always {
            jacoco(execPattern: '**/target/jacoco.exec')
            script {
                withSonarQubeEnv('SQ1') {
                     sonarQube credentialsId: '', serverUrl: 'http://localhost:9000', installationName: 'SQ1', scannerHome: ''
                }
            }
        }
    }
}
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
        stage('SonarQube analysis') {
            steps {
                script {
                    def scannerHome = tool 'scanner1'
                    withSonarQubeEnv('SQ1') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
    }
}
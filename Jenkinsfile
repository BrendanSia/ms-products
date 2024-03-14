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
                sh "mvn jacoco:report"
            }
        }
        stage('Sonar Scan') {
            withSonarQubeEnv(installationName: 'SQ1') {
                sh './mvnw clean org.sonarsource.scanner.maven:sonar-maven-plugin:2.9.0.2155:sonar'
            }
        }
    }
}
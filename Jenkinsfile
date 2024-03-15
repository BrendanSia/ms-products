pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                checkout scm
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
}

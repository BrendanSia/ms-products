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
                withSonarQubeEnv(installationName: 'SQ1') {
                    sh 'mvn clean package sonar:sonar'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 15, unit: 'MINUTES') { // If analysis takes longer than indicated time, then build will be aborted
                    waitForQualityGate abortPipeline: true
                    script{
                        def qg = waitForQualityGate() // Waiting for analysis to be completed
                        if(qg.status != 'OK'){ // If quality gate was not met, then present error
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
    }
}
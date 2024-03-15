pipeline {
    agent any
    stages {
        stage('Build & Install') {
            steps {
                script {
                    checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/BrendanSia/ms-products.git']])
                    def mvnResult = sh(script: "mvn clean install", returnStatus: true)
                    def jacocoResult = sh(script: "mvn jacoco:report", returnStatus: true)

                    // Pass stage details to report generation script
                    if (mvnResult != 0 || jacocoResult != 0) {
                        env.FAILED_STAGE_NAME = 'Build & Install'
                        env.FAILED_STAGE_DETAILS = "Maven Build: ${mvnResult}\nJacoco Report: ${jacocoResult}"
                    }
                }
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
                        def qg = waitForQualityGate()
                        if(qg.status != 'OK'){
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
    }
    post {
        failure {
            // Generate failure report
            sh './generate_report.sh'
        }
    }
}

pipeline {
    agent any
    stages {
        stage('Build & Install') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/BrendanSia/ms-products.git']])
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
            script {
                if (currentBuild.result == 'FAILURE') {
                // Generate a detailed report
                def failedStage = currentBuild.previousBuild.rawBuild.getCause(hudson.model.Cause$UpstreamCause).upstreamRun.rawBuild.getDisplayName()
                def failedJob = currentBuild.fullDisplayName
                def failedBuild = currentBuild.number

                def report = "Build Failed in Stage: $failedStage\nJob: $failedJob\nBuild Number: $failedBuild\n"

                // Send email notification
                emailext subject: "Build Failure Notification",
                         body: report,
                         to: "brendan.bnsch@paydaes.com",
                         mimeType: 'text/plain'
                }
            }
        }
    }
}
pipeline {
    agent any
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
        stage("Quality Gate") {
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    script {
                        try {
                            waitForQualityGate abortPipeline: true
                        } catch (Exception e) {
                            writeFile file: "${logfile}", text: "Failed in Quality Gate stage: ${e.toString()}\n", append: true
                            currentBuild.result = 'FAILURE'
                        }
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
            sh './generate_report.sh'

            def currentDate = new Date().format("yyyyMMdd_HHmmss")
            def attachmentsPattern = "reports/failure_report_${currentDate}.xml"
            def failedStageName = env.FAILED_STAGE_NAME ?: 'Unknown Stage'

            emailext attachLog: true,
            subject: "Pipeline Build Failure",
            body: "The build failed in stage '${failedStageName}'. Please find the attached report for details.",
            to: "your@email.com"
        }
    }
}

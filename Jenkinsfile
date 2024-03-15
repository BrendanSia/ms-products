pipeline {
    agent any
    stages {
        stage('Build') {
            step([$class: 'JacocoPublisher',
                  execPattern: 'target/*.exec',
                  classPattern: 'target/classes',
                  sourcePattern: 'src/main/java',
                  exclusionPattern: 'src/test*'
            ])
        }
        stage('Install') {
            steps {
                sh "mvn clean verify"
                sh "mvn test"
            }
        }
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv(installationName: 'SQ1') {
                    sh './mvnw clean sonar:sonar'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
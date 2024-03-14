pipeline {
    agent { docker { image 'maven:3.9.6-eclipse-temurin-17-alpine' } }
    triggers {
        githubPush()
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
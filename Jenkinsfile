pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven-3.9.11'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build + Test') {
            steps {
                sh 'mvn -B clean test'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/automation-report-*.html', fingerprint: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}

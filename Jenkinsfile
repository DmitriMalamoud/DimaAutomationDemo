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

        stage('Save Report') {
            steps {
                archiveArtifacts artifacts: "target/extent-report-*.html"
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}

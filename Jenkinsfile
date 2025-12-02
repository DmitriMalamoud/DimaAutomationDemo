pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven-3.9.11'
    }

    parameters {
        choice(name: 'Environment', choices: ['LOCAL', 'DEV_MOCK', 'STAGING_MOCK', 'FAIL'])
        choice(name: 'TestGroup', choices: ['All', 'Sanity', 'Fail Demo'])
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '8'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build + Test') {
            steps {
                script {
                    def mvnCmd = sh "mvn -B clean test -Dspring.profiles.active=${params.Environment.toLowerCase()}"
                    def group = params.TestGroup?.trim()
                    if (group && !group.equalsIgnoreCase('All')) {
                        def normalizedTag = group.toLowerCase().replaceAll(/\s+/, '_')
                        mvnCmd += " -Dgroups=${normalizedTag}"
                    }
                    sh mvnCmd
                }
            }
        }
    }

    post {
        always {
            script {
                currentBuild.displayName = "#${env.BUILD_NUMBER} env_${params.Environment}"
            }
            archiveArtifacts artifacts: 'target/automation-report-*.html', fingerprint: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}

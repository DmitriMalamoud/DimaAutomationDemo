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
                script {
                    def baseUrl
                    switch (params.Environment) {
                    case 'LOCAL':
                        baseUrl = 'http://localhost'
                        break
                    case 'DEV_MOCK':
                        baseUrl = 'http://localhost'
                        break
                    case 'STAGING_MOCK':
                        baseUrl = 'http://localhost'
                        break
                    case 'FAIL':
                        baseUrl = 'http://fail.url'
                        break
                    default:
                        error "Unsupported environment: ${params.Environment}"
                    }
                sh 'mvn -B clean test -Dapi.baseUrl=${baseUrl}'
            }
        }
    }

    post {
        script {
            currentBuild.displayName = "#${env.BUILD_NUMBER} env_${params.API_BASE_URL}"
        }
        always {
            archiveArtifacts artifacts: 'target/automation-report-*.html', fingerprint: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}

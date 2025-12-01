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

                    def mvnCmd = "mvn -B clean test -Dapi.baseUrl=${baseUrl}"
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

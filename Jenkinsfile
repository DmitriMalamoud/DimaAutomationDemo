pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven-3.9.11'
    }

    parameters {
        choice(name: 'Environment', choices: ['JENKINS', 'LOCAL', 'FAIL'])
        choice(name: 'TestGroup', choices: ['All', 'Sanity', 'Fail Demo'])
        booleanParam(name: 'LLM_Analysis', defaultValue: true, description: 'Enable LLM Analysis for failed tests')
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
                    def mvnCmd = "mvn -B clean test -Dspring.profiles.active=${params.Environment.toLowerCase()}"
                    def group = params.TestGroup?.trim()
                    if (group && !group.equalsIgnoreCase('All')) {
                        def normalizedTag = group.toLowerCase().replaceAll(/\s+/, '_')
                        mvnCmd += " -Dgroups=${normalizedTag}"
                    }
                    mvnCmd += " -Dllm=${params.LLM_Analysis}"
                    echo "Running: ${mvnCmd}"
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

//             allure results: [[path: 'target/allure-results']]
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}

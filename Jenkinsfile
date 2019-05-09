pipeline {
    agent any

    stages {
        stage("Clean") {
            steps {
                sh "./gradlew clean"
            }
        }

        stage("Build") {
            steps {
                sh "./gradlew assemble"
            }
        }

        stage("Test") {
            steps {
                sh "./gradlew check"
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "./gradlew buildDockerImage"
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "./gradlew pushDockerImage"
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/**/*.xml'
        }
    }
}



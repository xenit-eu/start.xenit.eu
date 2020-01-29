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
                sh "./gradlew bootJar --info --stacktrace"
            }
        }

        stage("Test") {
            steps {
                sh "./gradlew check"
            }
        }

        stage('Build Docker Image') {
            when {
                anyOf {
                    branch "master*"
                }
            }
            steps {
                sh "./gradlew buildDockerImage"
            }
        }

        stage('Publish Docker Image') {
            when {
                anyOf {
                    branch "master*"
                }
            }
            steps {
                sh "./gradlew pushDockerImage"
            }
        }

        stage('Deploy via Ansible Tower') {
            when {
                anyOf {
                    branch "master*"
                }
            }

            steps {
                script {
                    image=getImageWithTagFromFile()
                    echo "image=${image}"
                }

                ansibleTower(
                    towerServer: 'Production ansible tower',
                    templateType: 'job',
                    jobTemplate: 'Deploy start.xenit.eu (test-swarm)',
                    importTowerLogs: true,
                    removeColor: false,
                    verbose: true,
                    extraVars: """---
docker_compose_env_variables: {'DOCKER_IMAGE': '${image}'}"""
                )
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/**/*.xml'
        }
    }
}


def getImageWithTagFromFile() {
    return readFile("tag.txt")
}

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
		ansibleTower(
		    towerServer: 'Sandbox ansible tower',
		    templateType: 'job',
		    jobTemplate: 'Update start.xenit.eu',
		    importTowerLogs: true,
		    removeColor: false,
		    verbose: true,
		    extraVars: '''---
applications_to_install:
    - app: start
      name: start'''
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



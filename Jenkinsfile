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
    - name: nginx_sbx
      app: nginx
      proxy_tag: sbx
      proxy_domain: sbx.xenit.eu
      certbot_staging: False
      ctpl_templates:
        - auto-proxy-consul.conf.ctmpl
    - app: start
      name: initializr
      image: start.xenit.eu:awx
      service_name: initializr
      service_tags: proxy-http,sbx'''
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



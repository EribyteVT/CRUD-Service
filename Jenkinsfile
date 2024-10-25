pipeline {
    agent any
    parameters {
        gitParameter branchFilter: 'origin/(.*)', defaultValue: 'main', name: 'BRANCH', type: 'PT_BRANCH'
    }
    environment {
        DOCKER_IMAGE_NAME = "eribyteofficial/crud-service"
    }
    stages {
        stage("Checkout from github repo"){
            steps{
            git  branch: "${params.BRANCH}", url: 'https://github.com/EribyteVT/CRUD-Service.git'
            }
        }
        stage('Build') {
            steps {
                sh '/opt/apache-maven-3.9.9/bin/mvn -B -DskipTests clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    app = docker.build(DOCKER_IMAGE_NAME)
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker_hub_login') {
                        app.push("${env.BUILD_NUMBER}")
                        app.push("latest")
                    }
                }
            }
        }
        stage('DeployToProduction') {
            steps {
                withCredentials([string(credentialsId: 'CA_CERTIFICATE', variable: 'cert'),
                                 string(credentialsId: 'Kuubernetes_creds_id', variable: 'cred'),
                                 string(credentialsId: 'kubernetes_server_url', variable: 'url')]) {

                    kubeconfig(caCertificate: "${cert}", credentialsId: "${cred}", serverUrl: "${url}"){
                        sh 'kubectl apply -f deployment.yaml'
                        sh 'kubectl apply -f app-service.yaml'
                        sh 'kubectl rollout restart deployment eribot'
                    }
                }

            }
        }
    }
}
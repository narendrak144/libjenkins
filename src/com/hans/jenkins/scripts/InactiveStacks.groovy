package com.hans.jenkins.scripts

import com.hans.jenkins.App

/**
 * Lists and optionally deletes the inactive stacks of a given APP and ENV.
 * see cloud-templates/bin/inactive_stacks.sh.
 */
def main() {

    pipeline {
        agent any
        parameters {
            choice(name: 'app', choices: App.names())
            choice(name: 'env', choices: 'dev\nqa\nprod')
            string(name: 'deletion', description: 'type "--delete" to delete the inactive stacks')
            string(name: 'env_confirm', description: 'type "production" to confirm when deleting prod stacks')
        }
        stages {
            stage('confirm') {
                steps {
                    script {
                        if (params.env == "prod" && params.env_confirm != "production") {
                            currentBuild.description = "Must confirm when deleting in prod env"
                            throw new IllegalArgumentException("Must confirm when deleting in prod env")
                        }
                    }
                }
            }
            stage('checkout') {
                steps {
                    git branch: 'master',
                        credentialsId: '38b34877-da0d-483e-827a-a70952c936e6',
                        url: 'https://github.mlbam.net/ticketing/cloud-templates/'
                }
            }
            stage('pull image') {
                steps {
                    sh 'eval $(aws --region us-east-1 ecr get-login)'
                    sh 'docker pull 950377457506.dkr.ecr.us-east-1.amazonaws.com/ticketing/deploy:latest'
                }
            }
            stage('list inactive stacks') {
                steps {
                    withCredentials([[
                                         $class: 'FileBinding',
                                         credentialsId: 'aws-credential-file',
                                         variable: 'AWS_CONFIG_FILE'
                                     ]]) {

                        sh "docker run -e AWS_CONFIG_FILE=/root/.aws/config -v $AWS_CONFIG_FILE:/root/.aws/config -v \$(pwd):/build 950377457506.dkr.ecr.us-east-1.amazonaws.com/ticketing/deploy " +
                            "'bin/inactive_stacks.sh ${params.app} ${params.env} ${params.deletion}'"
                    }
                }
            }
        }
    }

}


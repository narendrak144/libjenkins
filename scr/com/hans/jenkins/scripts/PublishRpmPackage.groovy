package com.hans.jenkins.scripts

import com.hans.jenkins.App

/**
 * Publish a one-off RPM outside the integration workflow.
 */
def main() {

    def app = new App(params.app)

    pipeline {
        agent any
        parameters {
            choice(name: 'app', choices: App.names())
            string(name: 'branch', description: 'branch of application repository to use', defaultValue: 'release')
        }
        stages {
            stage('Setup') {
                steps {
                    script {
                        currentBuild.displayName = "${params.app} : ${params.branch} : ${app.getRpmVersion()}"
                    }
                    echo "pull mvn container"
                    mvnPullContainer()
                }
            }
            stage('Checkout') {
                steps {
                    echo "checkout ${params.app} : ${params.branch}"
                    checkoutGitBranch app
                }
            }
            stage('Build/Test') {
                steps {
                    echo "mvn test/package"
                    mvnTestPackage params.branch
                }
            }
            stage('RPM') {
                steps {
                    echo "rpmBuilder checkout/upload"
                    rpmBuilder app, 'release'
                }
            }
        }
    }

    return app

}

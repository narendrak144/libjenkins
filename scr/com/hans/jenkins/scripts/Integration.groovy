package com.hans.jenkins.scripts

import com.hans.jenkins.App
import com.hans.jenkins.Github

/**
 *
 * Compiles and tests a repo for multi-branch automation.
 * For the integration branch `release`, a new RPM and QA deployment are also made.
 *
 * @param apps - apps to test/publish/deploy.
 * @return apps as confirmation.
 *
 */
def main(List<App> apps) {

    def github = new Github()

    pipeline {
        agent any
        stages {
            stage('Setup') {
                steps {
                    echo "pull mvn container"
                    mvnPullContainer()
                }
            }
            stage('Checkout') {
                steps {
                    checkout scm
                }
            }
            stage('Build/Test') {
                steps {
                    echo "mvn test/package"
                    mvnTestPackage env.BRANCH_NAME
                }
            }
            stage('RPM') {
                steps {
                    echo "${github.integrationBranch} <> ${env.BRANCH_NAME}"
                    echo "rpmBuilder checkout/upload"
                    rpmBuilder apps, env.BRANCH_NAME
                    script {
                        currentBuild.displayName = ""
                        for (App app : apps) {
                            currentBuild.displayName += "${app.name} : ${env.BRANCH_NAME} : ${app.getRpmVersion()}\n"
                        }
                    }
                }
            }
            stage('Deploy QA') {
                steps {
                    deployIntegration apps, env.BRANCH_NAME
                }
            }
        }
    }

    return apps

}

/**
 * Known apps can be indicated by their unique app name string.
 * For complete customization, import the App class and initialize your own list.
 * @param appNames
 */
def main(String... appNames) {

    def apps = []
    for (def appName : appNames) {
        apps.add(new App(appName))
    }
    return main(apps)

}

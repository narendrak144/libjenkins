//@Library("jenkins-shared-libraries@release")
//@Library('github.com/mschuchard/jenkins-devops-libs@version')_
import com.hans.jenkins.App

import com.hans.jenkins.Github
//import org.jenkinsci.plugins.pipeline.utility.steps.conf.mf.SimpleManifest


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

   node('master') {
            stage('Setup') {
                    echo "pull mvn container"
                    //mvnPullContainer()
            }
            stage('Checkout') {
					
					echo "just doing checkout branch "
                   // checkout scm
            }
            stage('Build/Test') {
                    echo "mvn test/package"
                   // mvnTestPackage env.BRANCH_NAME
            }
            stage('RPM') {
						echo "just I am getting RPM"
					/*
                    echo "${github.integrationBranch} <> ${env.BRANCH_NAME}"
                    echo "rpmBuilder checkout/upload"
                    rpmBuilder apps, env.BRANCH_NAME
                    script {
                        currentBuild.displayName = ""
                        for (App app : apps) {
                            currentBuild.displayName += "${app.name} : ${env.BRANCH_NAME} : ${app.getRpmVersion()}\n"
                        }
                    }*/
            }
            stage('Deploy Dev') {
					echo "successfully deployed"
                   // deployIntegration apps, env.BRANCH_NAME
            }
        
    }
}

/**
 * Known apps can be indicated by their unique app name string.
 * For complete customization, import the App class and initialize your own list.
 * @param appNames
 */
def call(String... appNames) {

    def apps = []
    for (def appName : appNames) {
        apps.add(new App(appName))
    }
    return main(apps)
	//return apps;

}

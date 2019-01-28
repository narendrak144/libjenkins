import com.hans.jenkins.Github
import com.hans.jenkins.Maven

def call(def branch) {

    def github = new Github()
    def mvn = new Maven()

    configFileProvider([configFile(fileId: 'ticketing-maven-settings.xml', variable: 'MAVEN_SETTINGS')]) {
        if (branch == github.integrationBranch) {
            sh "${mvn.mvn} clean package -U ${mvn.runTests} ${mvn.withSettings} ${mvn.withBranch(branch)}"
        } else {
            sh "${mvn.mvn} clean test -U ${mvn.runTests} ${mvn.withSettings} ${mvn.withBranch(branch)}"
        }
        junit "**/target/surefire-reports/*.xml"
    }
}
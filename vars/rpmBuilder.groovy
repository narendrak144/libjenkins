import com.hans.jenkins.App
import com.hans.jenkins.Github

def call(List<App> apps, def branch) {
    for (App app : apps) {
        call(app, branch)
    }
}

def call(App app, def branch) {

    def github = new Github()

    if (branch != github.integrationBranch) {
        return
    }

    def rpmBuilder = fileLoader.fromGit(
        'rpm/rpm.groovy',
        'https://github.mlbam.net/Jenkins/libjenkins',
        'master',
        github.jenkinsCredentialsId
    )

    echo "rpm publish"

    try {
        rpmBuilder.defaultRPM(app.name, app.version, app.dest, app.dir)
        rpmBuilder.publishRPM("noenv/ticketing")
    } catch (e) {
        e.printStackTrace()
        throw e
    }

}

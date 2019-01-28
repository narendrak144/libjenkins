import com.hans.jenkins.Github

def call(def app) {

    def github = new Github()

    git branch: params.branch,
        credentialsId: github.jenkinsCredentialsId,
        url: app.repository

}

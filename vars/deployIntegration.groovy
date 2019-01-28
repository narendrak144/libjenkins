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

    echo "Deploy QA ${app.name} ${app.version}"

    build job: 'deploy',
        wait: false,
        parameters:
            [
                [ $class: 'StringParameterValue', name: 'app', value: app.name ],
                [ $class: 'StringParameterValue', name: 'env', value: 'qa' ],
                [ $class: 'StringParameterValue', name: 'region', value: 'us-east-1' ],
                [ $class: 'StringParameterValue', name: 'pool', value: app.getRpmVersion() ],
                [ $class: 'StringParameterValue', name: 'branch', value: 'master' ],
                [ $class: 'StringParameterValue', name: 'env_confirm', value: 'qa' ]
            ]
}

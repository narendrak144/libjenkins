import com.hans.jenkins.Maven

def call() {

    mvn = new Maven()

   // sh 'eval $(aws --region us-east-1 ecr get-login)'
    def mvnContainer = docker.image(mvn.image)
    mvnContainer.pull()

}

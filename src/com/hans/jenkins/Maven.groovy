package com.hans.jenkins

/**
 *
 * Maven image and configuration references.
 *
 */
public class Maven implements Serializable {

    def imgRegistry = "950377457506.dkr.ecr.us-east-1.amazonaws.com"
    def image = "${imgRegistry}/coreeng/mvn-build:latest"

    def mvn = "docker run --rm -v \$(pwd):/build -v \$MAVEN_SETTINGS:/build/settings.xml ${image}"

    def runTests = "-DskipTests=false"
    def withSettings = "--settings /build/settings.xml"
    def withBranch(def branch) {
        return "-Djenkins_branch=${branch}"
    }
    
    Maven() {
    } 
    
}

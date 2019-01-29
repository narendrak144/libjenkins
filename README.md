# libjenkins
[shared library](https://jenkins.io/doc/book/pipeline/shared-libraries/#global-shared-libraries) for methods used in Jenkins pipelines

### PipelineDemo

----

#### Standalone Scripts

- blue_green_deploy (deploy stacks)
- inactive_stacks (list/delete stacks)

Found top-level at [Jenkins2](http:localhost:8080/jenkins)

----

#### Application Scripts

##### Integration

Add this invocation to application repos in order to have automated tests for all your branches and PRs.
Additionally, merging to `release` triggers RPM publication and downstream `deploy` to QA.

Ex. `Jenkinsfile`

```groovy
#!/usr/bin/env groovy
@Library('libjenkins') _

// -*- mode: groovy; c-basic-offset: 2 -*-
// libjenkins docs https://github.narendrak144/libjenkins

new com.hans.jenkins.scripts.Integration().main(
    "rest-service",
)
```

Provide `String...` of your applications contained within the repository in question.
Above is an example of the tm-sh-proxy repo which has the two application stacks.

These names should correspond to the application metadata is found in `libjenkins: com.hans.jenkins.App`

----

For repositories that only need automated tests run (no application, RPM or stacks) such as `without rpm`
You should theoretically be able to run the same integration task with no arguments.

```groovy
#!/usr/bin/env groovy
@Library('libjenkins') _

new com.hans.jenkins.scripts.Integration().main()
```

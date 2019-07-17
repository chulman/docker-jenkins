/**
 * pipeline scripts with subversion and ant build
 */
node {
    def workspace = "${pwd()}"
    def release_path = "${workspace}/release"
    def svn_path = ''
    def ant_version = ''

    stage('Set') {
        svn_path = "${SVN_PATH}"
        println "svn url = ${svn_path}"

        ant_version = "${ANT_VERSION}"
        println " ANT_VERSION = ${ant_version}"
    }


    stage('Ready') {
        sh "echo 'Ready'"
        sh "echo workspace : ${workspace}"
        sh "echo release : ${release_path}"
        sh "echo ant version : ${ant_version}"
        sh "echo svn path : ${svn_path}"
        sh "java -version"
    }


    stage('Checkout') {
        sh "echo checkout"
        cleanWs()
        checkout([$class                : 'SubversionSCM',
                  additionalCredentials : [],
                  excludedCommitMessages: '',
                  excludedRegions       : '',
                  excludedRevprop       : '',
                  excludedUsers         : '',
                  filterChangelog       : false,
                  ignoreDirPropChanges  : false,
                  includedRegions       : '',
                  locations             : [
                          [cancelProcessOnExternalsFail: true,
                           credentialsId               : 'cmchoi',
                           depthOption                 : 'infinity',
                           ignoreExternalsOption       : true,
                           local                       : '.',
                           remote                      : "${svn_path}"]],
                  quietOperation        : true,
                  workspaceUpdater      : [$class: 'UpdateUpdater']])
    }

    stage('Build') {
        sh "echo build by ant"
        withAnt(installation: "${ant_version}") {
            sh "ant package"
        }
    }


    stage('IntegrationTest') {

        dir("${workspace}") {
            sh "rm -rf test"
            sh "mkdir -p test"
        }

        dir("${workspace}/h2/bin") {
            sh "chmod 755 *"
            //DB set
//            sh "runH2DB.sh &"
            sh "java -cp ./h2-1.3.168.jar org.h2.tools.RunScript -url jdbc:h2:tcp://localhost/./test -script ${workspace}/res/table.sql"
        }
        /*
         * ref https://issues.jenkins-ci.org/browse/JENKINS-44086
         */
        parallel(
                "run-01": {
                    sh "echo run server 01"
                },
                "run-02": {
                    sh "echo run server 01"
                }
        )
    }

    stage('Deploy') {
//    # bake docker images
        sh "docker build -t cmchoi/api:latest "

    }

}



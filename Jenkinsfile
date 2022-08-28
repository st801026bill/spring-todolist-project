pipeline {

    agent any
    parameters {
        string(name: 'LOG', defaultValue: '', description: 'log to deploy on prod')
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }
    tools {
        maven 'Maven'
    }
    environment {
        NEW_VERSION = '1.3.0'
    }
    stages {
        stage('build') {
            steps {
                echo 'building the application...'
                echo "building version ${NEW_VERSION}"
                echo "building version ${params.LOG}"
//                 sh "mvn install"
            }
        }

        stage('test') {
            when {
                expression {
                    BRANCH_NAME == 'master' && params.executeTests
                }
            }
            steps {
                echo 'testing the application...'
            }
        }

        stage('deploy') {
            steps {
                echo 'deploying the application...'
                echo "deploying version &{params.VERSION}"
            }
        }
    }
//     post {
//         always {
//             //
//         }
//
//         success {
//
//         }
//
//         failure {
//
//         }
//     }
}

node {

}
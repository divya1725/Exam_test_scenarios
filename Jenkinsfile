pipeline {
    agent {
        label 'docker'
    }
        
    parameters {
        choice(
            name: 'Environments',
            choices: ['G-D4', 'G-S1', 'G-D2', 'G-D5'],          
            description: 'Environment to run against'
        )
       choice(
            name: 'suite',
            choices: ['FraudPayment', 'NO_ASB_SECANA_CALL', 'ASB_ACCEPTED', 'ASB_CHALLENGE'],          
            description: 'Test suites to run'
        )
    }

  options {
        timeout(time: 45, unit: 'MINUTES')
        timestamps()
        buildDiscarder(logRotator(artifactDaysToKeepStr: '1', artifactNumToKeepStr: '1', daysToKeepStr: '30', numToKeepStr: '30'))
        skipStagesAfterUnstable()
    }   
    stages {
        
        stage('ReadyAPITest') { 
            steps {
                echo "Stage second Test"
               script{
              		// bat """docker run -v="${WORKSPACE}\\FraudPayments":/project -v="${WORKSPACE}\\FraudPayments\\Reports":/reports -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-EDefault environment' '/FraudPayments/'"  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
              	   // sh "./run-tests.sh"
                    sh """docker run -v="${WORKSPACE}/FraudPayments":/project -v="${WORKSPACE}/FraudPayments/reports":/reports -v="${WORKSPACE}/ext":/ext/ -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports%/ '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/%project%/' '-s${params.suite}'"  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                 
                 
                 
               }
            }
        }
        
    }
  post {
        always {           
          
          publishHTML (target : [allowMissing: false,
                       alwaysLinkToLastBuild: true,
                       keepAll: true,
                       reportDir: 'FraudPayments/reports',
                       reportFiles: 'index.html',
                       reportName: 'HTML Report',
                       reportTitles: 'The Report']
                      )
        }
        success {
            script {
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'good',
                    message: "${params.suite} ran successfully on ${params.Environments}. Check <${BUILD_URL} for details âœ…".stripIndent()

                )
            }
        }
        failure {
            script {
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'bad',
                    message: "${params.suite} failed in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
            }
        }        
    }
   
}

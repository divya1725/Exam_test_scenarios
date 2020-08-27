pipeline {
    agent {
        label 'docker'
    }
        
    parameters {
	
		choice(
            name: 'ReadyAPIProject',
            choices: ['FraudPayments', 'InspectionLogging', 'PreDefined-Creditor'],          
            description: 'Select a project to run'
        )
        choice(
            name: 'Environments',
            choices: ['G-D4', 'G-S1', 'G-D2', 'G-D5'],          
            description: 'Environment to run against'
        )
       choice(
            name: 'suite',
            choices: ['FraudPayment', 'Bank User Inspection Logging', 'PreDefinedCreditor', 'PreDefinedCreditor_V1.1'],          
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
                    sh """docker run -v="${WORKSPACE}/${params.ReadyAPIProject}":/project -v="${WORKSPACE}/${params.ReadyAPIProject}/reports":/reports -v="${WORKSPACE}/ext":/ext/ -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/%project%/' '-s${params.suite}'"  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                 
               }
            }
        }

    }
  post {
        always {           
          
          publishHTML (target : [allowMissing: false,
                       alwaysLinkToLastBuild: true,
                       keepAll: true,
                       reportDir: '${params.ReadyAPIProject}/reports',
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
                    message: "Project:${params.ReadyAPIProject}, suite:${params.suite} ran successfully on ${params.Environments}. Check <${BUILD_URL} for details âœ…".stripIndent()

                )
            }
        }
        failure {
            script {
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'bad',
                    message: "Project:${params.ReadyAPIProject}, suite:${params.suite} failed in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
            }
        }        
    }
   
}


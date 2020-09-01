#!groovy
#def projectList = ["FraudPayments","InspectionLogging","PreDefined-Creditor"]  
def projectList = ["FraudPayments"]  
def failed_email_to ='ullasa.srinivasa@evry.com'
def success_email_to ='ullasa.srinivasa@evry.com'

def emailNotification( email) {
	emailext to: email,
	from: 'noreply@qaTestResults.com',
	mimeType: 'text/html',
	subject: "[Build ${currentBuild.currentResult}:${env.BRANCH_NAME}] - Build ${currentBuild.displayName} on Branch",
	body: '${JELLY_SCRIPT,template="static-analysis"}',
	attachLog: true,
	attachmentsPattern: '**/reports/index.html'
} 

pipeline {
    agent {
        label 'docker'
    }
          
  triggers {
        cron('30 05 * * 1-5')
    }
  
    parameters {
	   choice(
            name: 'Environments',
            choices: ['G-D4', 'G-S1', 'G-D2', 'G-D5'],          
            description: 'Environment to run against'
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

                    //sh """docker run -v="${WORKSPACE}/${params.ReadyAPIProject}":/project -v="${WORKSPACE}/${params.ReadyAPIProject}/reports":/reports -v="${WORKSPACE}/ext":/ext/ -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/%project%/' '-s${params.suite}'"  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                 
                    //sh """docker run -v="${WORKSPACE}/InspectionLogging":/InspectionLogging -v="${WORKSPACE}/InspectionLogging/reports":/reports -v="${WORKSPACE}/ext":/ext/ -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/InspectionLogging/' "  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                 		
                 	//sh """docker run -v="${WORKSPACE}/":/project -v="${WORKSPACE}/reports":/reports -v="${WORKSPACE}/ext":/ext/ -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/%project%/' "  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                 
                 	
                    projectList.each{project->             
                 
                    // bat """docker run -v="${WORKSPACE}\\${project}":/project -v="${WORKSPACE}\\${project}\\reports":/reports -v="C:\\Program Files\\SmartBear\\ReadyAPI-3.3.0\\bin\\ext":/ext -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/reports '-RJUnit-Style HTML Report' -FHTML '-EDefault environment' '/project1/'"  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                      sh """docker run -v="${WORKSPACE}/${project}":/project -v="${WORKSPACE}/${project}/reports":/reports -v="${WORKSPACE}/ext":/ext/ -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/%project%/' "  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                     
                  }
                 
               }
            }
        }

    }
  post {
        always {           
          
          script{             
                    projectList.each{project-> 
                       publishHTML (target : [allowMissing: false,
                       alwaysLinkToLastBuild: true,
                       keepAll: true,
                       reportDir: "${project}/reports",
                       reportFiles: 'index.html',
                       reportName: "HTML Report-${project}",
                       reportTitles: 'The Report1']
                      )
                    }        
            
            
          }
          
          
        }
        success {
            script {
              emailNotification(success_email_to)
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'good',
                    message: "Testing MultiProjectRun : Projects:${projectList} ran successfully on ${params.Environments}. Check <${BUILD_URL} for details âœ…".stripIndent()

                )
            }
        }
        failure {
            script {
              emailNotification(failed_email_to)
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'bad',
                    message: "Testing MultiProjectRun : Projects:${projectList} failed in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
            }
        }  
                
    }
   
}


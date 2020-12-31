#!groovy
def failed_email_to ='ullasa.srinivasa@evry.com'
def success_email_to ='ullasa.srinivasa@evry.com'

def emailNotification( email) {
	emailext to: email,
	from: 'noreply@qaTestResults.com',
	mimeType: 'text/html',
	subject: "[Build ${currentBuild.currentResult}:${env.BRANCH_NAME}:${params.Environments}] - Build ${currentBuild.displayName} on Branch",
	body: '${JELLY_SCRIPT,template="static-analysis"}',
	attachLog: true,
	attachmentsPattern: '**/reports/index.html'
} 

def containerTemp = "soapucontainerRegressionTag${currentBuild.displayName}"
def containername = (containerTemp.contains('#'))?(containerTemp.replace('#','')):containerTemp
//def slackChannelName = "#slackmessgetest"
def slackChannelName = "#regressiontestresults"
pipeline {
    agent {
        label 'docker'
    }
          
  //triggers {
    //	cron(env.BRANCH_NAME == 'master' ? '30 04 * * 1-5' : '')
    //}
  
    parameters {
	   choice(
            name: 'Environments',
            choices: ['G-D4','G-S1','G-D2', 'G-D5', 'G-D6', 'G-D7', 'G-D8', 'G-D17','G-D10','G-D14','R-S6'],          
            description: 'Environment to run against'
        )
	   choice(
            name: 'ExecutionTags',
            choices: ['','SMOKETEST'],          
            description: 'Select a SMOKETEST tag to run pre-selected testcases, select empty to run all testcases'
        )
	   choice(
            name: 'SuiteName',
            choices: ['', 'Camt054_Advice','CAVA-PTI-readyapi-project','CPSEventLog','EditAndRetry','FilePaymentISPCAll','FiskPayments','FraudPayments','FraudSecanaPayments','FraudRevalidationBatch','InspectionLogging','OnlineReservation','PAIN002 ErrorCodes','PAIN002-Advice','PaymentCreateAllISPC','PINActions','PINSearches','PreDefined-Creditor','PredefinedCreditorCAVA','ReceiptOrder','Regulatory-Reporting','SkkoPayments','StandingOrder','VIP','PaymentCreateAllISPCSmokeTest','PreDefined-CreditorSmokeTest','ReceiptOrderSmokeTest','STOLBatchExecution','TransferSettlementBatch'],          
            description: 'Select a project to run'
        )
		
    }

  options {
        timeout(time: 240, unit: 'MINUTES')
        timestamps()
        buildDiscarder(logRotator(artifactDaysToKeepStr: '1', artifactNumToKeepStr: '1', daysToKeepStr: '5', numToKeepStr: '50'))
        skipStagesAfterUnstable()
    }   
    stages {
        
        stage('ReadyAPITest') { 
            steps {                
               script{
                    // sh 'chmod +x ./run-tests.sh'
                    //sh "./run-tests.sh ${params.Environments}" 
					
                 	sh "docker build -t soapui . -f Dockerfile"
                 	sh """docker run -e COMMAND_LINE="${params.Environments}" -e TAGS="${params.ExecutionTags}" -e SUITENAME="${params.SuiteName}" --name ${containername} soapui"""
               }
            }
        }

    }
  post {
        always {           
          
           script{
             	
                  sh "docker cp ${containername}:/usr/local/SmartBear/project ${WORKSPACE}"
             	  sh "docker stop ${containername}"
             	  sh "docker rm ${containername}"
                  
            	  junit "**/*/reports/*.xml"   
                  
             	  archiveArtifacts artifacts: 'project/*/results/*/*/*.txt', fingerprint: true, allowEmptyArchive: true


                }
         // cleanWs()

         }
        success {
            script {
              emailNotification(success_email_to)
                slackSend(
                    channel: "${slackChannelName}",
                    color: 'good',
                    message: "Regression Testing ${env.BRANCH_NAME} : Projects:${params.SuiteName} ran successfully on ${params.Environments}. Check <${BUILD_URL} for details âœ…".stripIndent()

                )
              
            }
        }
        failure {
            script {
              emailNotification(failed_email_to)
                slackSend(
                    channel: "${slackChannelName}",
                    color: 'danger',
                    message: "${env.BRANCH_NAME} : Automation Suite-${params.SuiteName} failed in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
              
            }
        }  
        
   		unstable {
            script {
              emailNotification(failed_email_to)
                slackSend(
                    channel: "${slackChannelName}",
                    color: 'warning',
                    message: "${env.BRANCH_NAME} : Automation Suite-${params.SuiteName} unstable in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
              
            }
        } 
    }
   
}


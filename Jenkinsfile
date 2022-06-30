#!groovy
def failed_email_to ='ullasa.srinivasa@tietoevry.com'
def success_email_to ='ullasa.srinivasa@tietoevry.com'

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
def wsReportFolder = "https://jenkins.finods.com/job/payment/job/automation/job/pr-regression-valuechain/job/${env.BRANCH_NAME}/${currentBuild.number}/execution/node/3/ws/project/${params.SuiteName}/"
def PROJECT_FOLDER="/usr/local/SmartBear/project"

pipeline {
    agent {
        label 'docker'
    }
          
  // triggers {cron(env.BRANCH_NAME == 'master' ? '0 4 * * 1' : '')  }
  
    parameters {
	   choice(
            name: 'Environments',
            choices: ['G-S1','G-D4','G-D1','G-D2', 'G-D5', 'G-D6', 'G-D7', 'G-D8','G-D9', 'G-D10','G-D14','G-D17','R-S6','G-S4'],          
            description: 'Environment to run against'
        )
		choice(
            name: 'ExecutionTags',
            choices: ['','SMOKETEST','CAVASMOKETESTDATAGEN', 'CAVAREGRESSIONTESTDATAGEN'],          
            description: 'Select a SMOKETEST tag to run pre-selected testcases(Tag must be added to testcase in ReadyAPI tool), select empty to run all testcases'
        )
	   choice(
            name: 'SuiteName',
            choices: ['','Bank Internal Comment','Pain002Codes','Camt054_Advice','CAVA-PTI-readyapi-project','CPSEventLog','EditAndRetry','FilePaymentISPCAll','FiskPayments','BusinessEvntlog','ForeignAccountPayments','FraudPayments','FraudSecanaPayments','FraudRevalidationBatch','InspectionLogging','KYCandAmountLimitValidation','OnlineReservation','WarningAndBlckng','PaymentStatusUpdateAsyncApprove','PaymentStatusUpdate-Project','PAIN002-Advice','PaymentCreateAllISPC','PINActions','PINSearches','VIP_Regre','PINValueChainSuite','PORBatchSuite','PreDefined-Creditor','PreAppr-Pain001','PredefinedCreditorCAVA','PaymentCreateAllISPCSmokeTest','PreDefined-CreditorSmokeTest','PwhToRbsCopy','ReceiptOrder','Regulatory-Reporting','ReceiptOrderSmokeTest','SkkoPayments','StandingOrder','SO NewCore','STOLBatchExecution','SettlementChargesAndInterest','TransferSettlementBatch','VIP','EnvironmentTestProject','PRM_1881_ProductSubTypes','PaymentUtil','PRM-4601-Approve-Project'],          
            description: 'Select a project to run'
        )
		booleanParam(
            name: 'loadEnvFromJsonFile',
            defaultValue: false,
            description: 'Select if you want to override project properties with env json file'
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
					
                 	sh "docker build -t soapui . -f Dockerfile"
		       	def workingspace= pwd()
		       	echo "${workingspace}"
          	      	sh """docker run -e SLM_LICENSE_SERVER="https://api.slm.manage.smartbear.com:443" -e API_KEY="ffc18e5e-03d7-44a2-b136-6d7cdd73a313" -v="${workingspace}/${params.SuiteName}":${PROJECT_FOLDER}/${params.SuiteName} -v="${workingspace}/ext":/ext -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '${PROJECT_FOLDER}/${params.SuiteName}'" --name ${containername} soapui"""
	       
	       }
            }
        }

    }
  post {
        always {           
          
           script {
             	
                  sh "docker cp ${containername}:/usr/local/SmartBear/project ${WORKSPACE}"
             	  sh "docker stop ${containername}"
             	  sh "docker rm ${containername}"
                  
		   archiveArtifacts artifacts: 'project/*/results/*/*/*.txt', fingerprint: true, allowEmptyArchive: true
            	  //junit "**/*/reports/*.xml"   
                  
             	  //archiveArtifacts artifacts: 'project/*/results/*/*/*.txt', fingerprint: true, allowEmptyArchive: true
                }
          cleanWs()

         }
        success {
            script {
              emailNotification(success_email_to)
                slackSend(
                    channel: "${slackChannelName}",
                    color: 'good',
                    message: "Regression Testing ${env.BRANCH_NAME} : Projects:${params.SuiteName} ran successfully on ${params.Environments}. Check <${BUILD_URL}|run Details> and download <${wsReportFolder}|artifacts> :jenkins_success:".stripIndent()

                )
              
            }
        }
        failure {
            script {
              emailNotification(failed_email_to)
                slackSend(
                    channel: "${slackChannelName}",
                    color: 'danger',
                    message: "${env.BRANCH_NAME} : Automation Suite-${params.SuiteName} failed in ${params.Environments}. Check <${BUILD_URL}|run Details> and download <${wsReportFolder}|artifacts> :fire:".stripIndent()

                )
              
            }
        }  
        
   		unstable {
            script {
              emailNotification(failed_email_to)
                slackSend(
                    channel: "${slackChannelName}",
                    color: 'warning',
                    message: "${env.BRANCH_NAME} : Automation Suite-${params.SuiteName} unstable in ${params.Environments}. Check <${BUILD_URL}|run Details> and download <${wsReportFolder}|artifacts> :fire:".stripIndent()

                )
              
            }
        } 
    }
   
}

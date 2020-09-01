#!groovy
def projectList = ["FraudPayments","InspectionLogging","PreDefined-Creditor"]  
//def projectList = ["FraudPayments"]  
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
                     sh 'chmod +x ./run-tests.sh'
                     sh "./run-tests.sh ${params.Environments}" 
               }
            }
        }

    }
  post {
        always {           
          
          script{          
            	//sh 'chmod +x ./publish-HTML.sh'
                //sh "./publish-HTML.sh"
                  projectList.each{project-> 
                       publishHTML (target : [allowMissing: false,
                       alwaysLinkToLastBuild: true,
                       keepAll: true,
                       reportDir: "${project}/reports",
                       reportFiles: "*.html",
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


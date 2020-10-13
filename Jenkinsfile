#!groovy
def projectList = [] 
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

pipeline {
    agent {
        label 'docker'
    }
          
//  triggers {
//    	cron(env.BRANCH_NAME == 'master' ? '30 04 * * 1-5' : '')
//    }
  
    parameters {
	   choice(
            name: 'Environments',
            choices: ['G-D4','G-S1','G-D2', 'G-D5'],          
            description: 'Environment to run against'
        )      
    }

  options {
        timeout(time: 130, unit: 'MINUTES')
        timestamps()
        buildDiscarder(logRotator(artifactDaysToKeepStr: '1', artifactNumToKeepStr: '1', daysToKeepStr: '5', numToKeepStr: '10'))
        skipStagesAfterUnstable()
    }   
    stages {
        
        stage('ReadyAPITest') { 
            steps {                
               script{
                    // sh 'chmod +x ./run-tests.sh'
                    //sh "./run-tests.sh ${params.Environments}" 
					
                 	sh "docker build -t soapui . -f Dockerfile"
                 	sh """docker run -e COMMAND_LINE="${params.Environments}" --name soapucontainermultiproj soapui"""
               }
            }
        }

    }
  post {
        always {           
          
           script{
             	
                  sh "docker cp soapucontainermultiproj:/usr/local/SmartBear/project ${WORKSPACE}"
             	  sh "docker stop soapucontainermultiproj"
             	  sh "docker rm soapucontainermultiproj"
                  
            	  junit "**/*/reports/*.xml"   
                  
             	  archiveArtifacts artifacts: 'project/*/results/*/*/*.txt', fingerprint: true, allowEmptyArchive: true

                  def files = findFiles(glob: "**/*/project.content")
                   files.each{ val->
                        def projStr = val.path
                        projStr = projStr.replace("project.content","")
                        projectList.add(projStr)                                  
                            }
                }
          cleanWs()

         }
        success {
            script {
              emailNotification(success_email_to)
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'good',
                    message: "Regression Testing MultiProjectRun : Projects:${projectList} ran successfully on ${params.Environments}. Check <${BUILD_URL} for details âœ…".stripIndent()

                )
              
            }
        }
        failure {
            script {
              emailNotification(failed_email_to)
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'danger',
                    message: "MultiProjectRun : Regression Testing job failed in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
              
            }
        }  
        
   		unstable {
            script {
              emailNotification(failed_email_to)
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'warning',
                    message: "MultiProjectRun : Regression Testing job unstable in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
              
            }
        } 
    }
   
}


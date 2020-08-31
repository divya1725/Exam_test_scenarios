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
		string(
			name: 'TestCase', 
			defaultValue: '', 
			description: 'Enter a testcase to run'
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
                 
                          def allProjFolder = "${WORKSPACE}\\"
                          File allProjFolderFiles =  new File(allProjFolder)
                 		  def exclusionList =["ext"]
                          if (allProjFolderFiles.exists() && allProjFolderFiles.isDirectory())
                          {
                             File[] listOfFiles = allProjFolderFiles.listFiles();
                             if (listOfFiles != null)
                             {
                                for (File childFolder : listOfFiles )
                                        {
                                           if (childFolder.exists() && childFolder.isDirectory() && !exclusionList.contains(childFolder.toString()))
                                               {
                                                  println "Folder found " + childFolder.toString()	 + "!!!"
                                                 def projectName = childFolder.toString()
                                                 sh """docker run -v="${WORKSPACE}/${projectName}":/project -v="${WORKSPACE}/${projectName}/reports":/reports -v="${WORKSPACE}/ext":/ext/ -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/%project%/' "  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                                               }
                                        }

                              }
                          }
                          else
                          {
                               println sourceFolder + "  Folder does not exists"
                          }
                 
                 
               }
            }
        }

    }
  post {
        always {           
          
          script{
            
           publishHTML (target : [allowMissing: false,
                       alwaysLinkToLastBuild: true,
                       keepAll: true,
                       reportDir: "FraudPayments/reports",
                       reportFiles: 'index.html',
                       reportName: 'HTML Report-FraudPayments',
                       reportTitles: 'The Report1']
                      )
            
            
            publishHTML (target : [allowMissing: false,
                       alwaysLinkToLastBuild: true,
                       keepAll: true,
                       reportDir: "InspectionLogging/reports",
                       reportFiles: 'index.html',
                       reportName: 'HTML Report-InspectionLogging',
                       reportTitles: 'The Report-InspectionLogging']
                      )
            
            
          }
          
          
        }
        success {
            script {
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'good',
                    message: "Testing MultiProjectRun : Project:${params.ReadyAPIProject} & InspectionLogging, suite:${params.suite} ran successfully on ${params.Environments}. Check <${BUILD_URL} for details âœ…".stripIndent()

                )
            }
        }
        failure {
            script {
                slackSend(
                    channel: "#regressiontestresults",
                    color: 'bad',
                    message: "Testing MultiProjectRun : Project:${params.ReadyAPIProject}, suite:${params.suite} failed in ${params.Environments}. Check ${BUILD_URL} for details ðŸ™ˆ".stripIndent()

                )
            }
        }  
                
    }
   
}


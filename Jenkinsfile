pipeline {
   agent any
    stages {
        stage('Build') { 
            steps {
            echo "Stage first Build"
                 script{
                     //bat "xcopy C:\\Ullasa\\Projects\\FraudRevalidation\\FraudPayments C:\\Ullasa\\Temp\\folder1\\folder2 /e"
                     def LOCALDIR= bat "chdir"
                     echo "value of current dir"
                    echo "bat chdir"
                     echo "value of current dir2"
                    echo "${LOCALDIR}"
                    echo "value of current workspace"
                    echo "${WORKSPACE}"
                 }
            }
        }
        stage('Test') { 
            steps {
                echo "Stage second Test"
               script{
              // bat """docker run -v="${WORKSPACE}\\FraudPayments":/project -v="${WORKSPACE}\\FraudPayments\\Reports":/reports -e LICENSE_SERVER="fslicense.evry.com:1099" -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-EDefault environment' '/FraudPayments/'"  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0"""
                sh "./run-tests.sh"
                 
               }
            }
        }
        stage('Deploy') { 
            steps {
                echo "Stage third Deploy" 
            }
        }
    }
}

# ReadyAPI Automation Framework
## _Steps to create and build ReadyAPI suite_
### 1.How to add new project to framework
 - Convert the project into Composite project
 - Add all the project properties present in env/TEMPLATE.json
 - Update the Composite project root folder name in JenkinsFile
        ex-choice(
                    name: 'SuiteName',
                    choices: ['','Project1','Project2'],          
                    description: 'Select a project to run'
                )
        
 -  Added a "ProjectRunListener.beforeRun" event and put below code
	```sh
    ProjectEnvironments.load(projectRunner,context,log)
    ```

 - Just build the jenkins job without selecting the project to load the newly added project into jenkins and stop it. You will be able to see the newly added project in Jenkins build parameter "SuiteName"


### 2.How to run a project in Jenkins.
 - Click on the Branch name https://jenkins.finods.com/job/payment/job/automation/job/pr-regression-valuechain/
 - Click on link "Build with Parameters" 
 - Select the Environments from dropdown
 - Select the ProjectName from dropdown "SuiteName"
 - Select the checkBox loadEnvFromJsonFile only if the folder /env/<env>.json file having latest properties and this json file will override all the project properties in tool and also Creates new env if its not present in ReadyAPI.
 - Click on "Build"

### 3. How to download html report
 - Click on the Build number
 - Click on the link "Test Result"
 - Click on the suiteName/Testcase Name to check the status

### 4.How to download results folder from workspace
 - Click on the Build Number link
 - Click on the Link 'Workspcases' on left side
 - Click on the link text "/data/jenkins/workspace/_pr-regression-valuechain_master".
 - Navigate to the folder subfolder "project'
 - Navigate to the respective suite folder. ex-ReceiptOrder
 - Navigate to the folder "results". ex- 	"results/20210722/ReceiptOrder"
 - Download the results by clicking link " (all files in zip)" at the botton of the page
       

### 5.Slack channel for notification.
#regressiontestresults


### 6.Refer wikipage for more details
https://wiki.finods.com/display/projects/ReadyAPI+Test+Automation+Framework



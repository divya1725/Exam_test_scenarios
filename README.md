read me
multibranch pipeline for regression testing

1. How to add new project to framework
a.Convert the project into Composite project
b.Add all the project properties present in env/TEMPLATE.json
c.Update the Composite project root folder name in JenkinsFile
ex-choice(
            name: 'SuiteName',
            choices: ['','Project1','Project2'],          
            description: 'Select a project to run'
        )
        
d.Just build the jenkins job without selecting the project to load the newly added project into jenkins and stop it. You will be able to see the newly added project in Jenkins build parameter "SuiteName"


2. How to run a project in Jenkins

3. How to download html report

4.How to download results folder from workspace

5. Slack channel for notification.
#regressiontestresults


#!/bin/bash

ENV=$1
excludeProjects=('PORBatchSuite' 'SkkoPayments' 'ext' 'Automation Standing Order');

echo "Env is $ENV"
exitCode=0
for subProject in */project.content ; do
		
    soapProject=$(echo "$subProject" | sed "s/\/project.content/""/g")
	excludedProj=`echo ${excludeProjects[*]} | grep "$soapProject"`
	if [ "${excludedProj}" == "" ]
	then
				
		echo "Run Composite SoapProject $soapProject"
		export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' "-E$ENV" '-FHTML' 
		          
          tempCode=$?
          echo "tempCode is $tempCode"
	      if [ "$tempCode" -ne "0" ]; then
              exitCode=$tempCode
              echo "Exid code is $exitCode"
          fi
      
	fi
done
echo "Final Exit Code is $exitCode"
exit $exitCode

#!/bin/bash

ENV=$1
SUITENAME=$2
TAGS=$3

excludeProjects=('PORBatchSuite' 'SkkoPayments' 'ext' 'CPSEventLog' 'PwhToRbsCopy' 'PreDefined-CreditorSmokeTest' 'ReceiptOrderSmokeTest' 'PaymentCreateAllISPCSmokeTest' 'CAVA-PTI-readyapi-project' 'PredefinedCreditorCAVA');

FOUNDFLAG=""
echo "Env is $ENV and Tag is $TAGS and suiteName is $SUITENAME"
exitCode=0


if [ "${SUITENAME}" == "" ] 
then
		for subProject in */project.content ; do
				
			soapProject=$(echo "$subProject" | sed "s/\/project.content/""/g")
			#excludedProj=`echo ${includeProject[*]} | grep "$soapProject"`
			if printf '%s\n' "${excludeProjects[@]}" | grep -q -P "^${soapProject}$"; then
				echo "Skipping excluded project $soapProject"
			elif [ "${soapProject}" != "" ]
			then
				FOUNDFLAG="TRUE"		
				echo "Run Composite SoapProject $soapProject"
				if [ "${TAGS}" == "" ]
				then
					export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' "-E$ENV" '-FHTML'
				else		
					export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' "-E$ENV" '-FHTML' "-TTestCase $TAGS"
				fi
				  tempCode=$?
				  echo "tempCode is $tempCode"
				  if [ "$tempCode" -ne "0" ]; then
					  exitCode=$tempCode
					  echo "Exid code is $exitCode"
				  fi
			  
			fi
		done	

else
		soapProject="$SUITENAME";
		FOUNDFLAG="TRUE"		
		echo "Run Composite SoapProject $soapProject"
		if [ "${TAGS}" == "" ]
		then
			export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' "-E$ENV" '-FHTML'
		else		
			export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' "-E$ENV" '-FHTML' "-TTestCase $TAGS"
		fi
          tempCode=$?
          echo "tempCode is $tempCode"
	      if [ "$tempCode" -ne "0" ]; then
              exitCode=$tempCode
              echo "Exid code is $exitCode"
          fi     

fi


if [ "${FOUNDFLAG}" == "" ]
then	
	echo "No Matching project found for suiteName -$SUITENAME"
	exitCode=255
fi
echo "Final Exit Code is $exitCode"
exit $exitCode

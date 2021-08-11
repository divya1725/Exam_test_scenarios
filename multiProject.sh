#!/bin/bash

ENV=$1
SUITENAME=$2
LOADFROMJSON=$3
TAGS=$4

excludeProjects=('SkkoPayments' 'ext' 'CPSEventLog' 'PreDefined-CreditorSmokeTest' 'ReceiptOrderSmokeTest' 'PaymentCreateAllISPCSmokeTest' 'CAVA-PTI-readyapi-project' 'PredefinedCreditorCAVA' 'PINValueChainSuite');

FOUNDFLAG=""
echo "Env is $ENV and LOADFROMJSON is $LOADFROMJSON and suiteName is $SUITENAME and tag is $TAGS"
exitCode=0
# COMMAND_LINE value is same as ENV value, wil be fetched from system variable( set in dockerfile)

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
				if [ "${LOADFROMJSON}" == "true" ]
				then					
					echo "Run All suites and LOADFROMJSON=$LOADFROMJSON (true) and COMMAND_LINE=$COMMAND_LINE "					
					if [ "${TAGS}" == "" ]
					then	
						export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML'
					else		
						export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML' "-TTestCase $TAGS"
					fi
				else
					unset COMMAND_LINE
					echo "Run All suites and LOADFROMJSON=$LOADFROMJSON (false) and COMMAND_LINE=$COMMAND_LINE"		
					if [ "${TAGS}" == "" ]
					then	
						export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML' "-E$ENV"
					else		
						export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML' "-E$ENV" "-TTestCase $TAGS"
					fi
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
		if [ "${LOADFROMJSON}" == "true" ]
		then			
			echo "Run One suite and LOADFROMJSON=$LOADFROMJSON(true) and COMMAND_LINE=$COMMAND_LINE and tag is $TAGS"			
			if [ "${TAGS}" == "" ]
			then
				export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML'
			else		
				export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML' "-TTestCase $TAGS"
			fi
		else
			unset COMMAND_LINE
			echo "Run One suite and LOADFROMJSON=$LOADFROMJSON (false) and COMMAND_LINE=$COMMAND_LINE and tag is $TAGS"					
			if [ "${TAGS}" == "" ]
			then
				export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML' "-E$ENV"
			else		
				export REPORTS_FOLDER="$PROJECT_FOLDER/$soapProject/reports" && cd $PROJECT_FOLDER && $READYAPI_FOLDER/bin/testrunner.sh "$soapProject" "-f/$REPORTS_FOLDER/" '-RJUnit-Style HTML Report' '-FHTML' "-E$ENV" "-TTestCase $TAGS"
			fi
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

#!/bin/bash

LOCALDIR=$PWD
ENV=$1
excludeProjects=('SkkoPayments' 'PORBatchSuite');


echo "LOCALDIR=$LOCALDIR and EnvironemntName is $ENV"
exitCode=0
for subProject in */project.content ; do
		
    soapProject=$(echo "$subProject" | sed "s/\/project.content/""/g")
	excludedProj=`echo ${excludeProjects[*]} | grep "$soapProject"`
	if [ "${excludedProj}" == "" ]
	then
		
		echo "Run Composite SoapProject $soapProject"
        docker run -v="$LOCALDIR/$soapProject":/project -v="$LOCALDIR/$soapProject/reports":/reports -v="$LOCALDIR/ext":/ext/ \
          -e LICENSE_SERVER="fslicense.evry.com:8443" \
          -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E$ENV' '/%project%/' "  \
           fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:latest           
          
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

#!/bin/bash

LOCALDIR=$PWD
ENV=$1

echo "LOCALDIR=$LOCALDIR and EnvironemntName is $ENV"

for subProject in */ ; do
	if [ "$subProject" != "ext/" ]
	then
		echo "Run Composite Project $subProject"
        docker run -v="$LOCALDIR/$subProject":/project -v="$LOCALDIR/$subProject/reports":/reports -v="$LOCALDIR/ext":/ext/ \
          -e LICENSE_SERVER="fslicense.evry.com:8443" \
          -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E$ENV' '/%project%/' "  \
           fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:latest           
          
          echo $?
	      echo "Status is given above"
      
	fi
done

#!/bin/bash

LOCALDIR=$PWD
ENV=$1

echo "LOCALDIR=$LOCALDIR and EnvironemntName is $ENV"

for project in */ ; do
    echo " Outside foldeer $project"
	if [ "$project" != "ext/" ]
	then
		echo "run Composite Project $project"
        docker run -v="$LOCALDIR/$project":/project -v="$LOCALDIR/$project/reports":/reports -v="$LOCALDIR/ext":/ext/ \
          -e LICENSE_SERVER="fslicense.evry.com:1099" \
          -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E$ENV' '/%project%/' "  \
           fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:latest
	fi
done

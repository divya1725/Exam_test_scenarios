#!/bin/bash

LOCALDIR=$PWD
PROJECT=$1
ENV=$2
echo "LOCALDIR=$LOCALDIR and ProjectName is $PROJECT , EnvironemntName is $ENV"

docker run -v="$LOCALDIR/$PROJECT":/project -v="$LOCALDIR/$PROJECT/reports":/reports -v="$LOCALDIR/ext":/ext/ \
-e LICENSE_SERVER="fslicense.evry.com:1099" \
-e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E$ENV' '/%project%/' "  \
fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0

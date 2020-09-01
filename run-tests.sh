#!/bin/bash

LOCALDIR=$PWD
PROJECT="FraudPayments"
ENV="G-D4"
echo "LOCALDIR=$LOCALDIR"

docker run -v="$LOCALDIR/$PROJECT":/project -v="$LOCALDIR/$PROJECT/reports":/reports -v="$LOCALDIR/ext":/ext/ \
-e LICENSE_SERVER="fslicense.evry.com:1099" \
-e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E$ENV' '/%project%/' "  \
fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0

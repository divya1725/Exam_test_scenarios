#!/bin/bash

LOCALDIR=$PWD

echo "LOCALDIR=$LOCALDIR"

docker run -v=$LOCALDIR/Project-1-readyapi-project/:/project/ -v=$LOCALDIR/Project-1-readyapi-project/scripts/:/scripts/ \
  -v=$LOCALDIR/Project-1-readyapi-project/reports/:/reports/ \
  -e LICENSE_SERVER="fslicense.evry.com:1099" \
  -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-EDefault environment' '/%project%/'" \
  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0

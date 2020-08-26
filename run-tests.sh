#!/bin/bash

LOCALDIR=$PWD

echo "LOCALDIR=$LOCALDIR"

docker run -v=$LOCALDIR/FraudPayments/:/project/ -v=$LOCALDIR/project/scripts/:/scripts/ \
  -v=$LOCALDIR/FraudPayments/reports/:/reports/ \
  -e LICENSE_SERVER="fslicense.evry.com:1099" \
  -e COMMAND_LINE="'/%project%/'" \
  fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0
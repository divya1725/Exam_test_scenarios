#!/bin/bash

LOCALDIR=$PWD
ENV=$1

echo "LOCALDIR=$LOCALDIR and EnvironemntName is $ENV"

# Declare an array of string with type
declare -a projectArray=("FraudPayments","InspectionLogging","PreDefined-Creditor")
 
# Iterate the string array using for loop
for val in ${projectArray[@]}; do
  echo "values is $val"
  docker run -v="$LOCALDIR/$val":/project -v="$LOCALDIR/$val/reports":/reports -v="$LOCALDIR/ext":/ext/ \
    -e LICENSE_SERVER="fslicense.evry.com:1099" \
    -e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E$ENV' '/%project%/' "  \
     fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0
done


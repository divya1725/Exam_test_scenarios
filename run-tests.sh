#!/bin/bash

LOCALDIR=$PWD

echo "LOCALDIR=$LOCALDIR"

docker run -v="$LOCALDIR/${project}":/project -v="$LOCALDIR/${project}/reports":/reports -v="$LOCALDIR/ext":/ext/ \
-e LICENSE_SERVER="fslicense.evry.com:1099" \
-e COMMAND_LINE="-f/%reports% '-RJUnit-Style HTML Report' -FHTML '-E${params.Environments}' '/%project%/' "  \
fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.1.0

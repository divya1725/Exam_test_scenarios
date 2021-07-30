FROM fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:3.8.0
RUN mkdir -p $PROJECT_FOLDER && \
   echo "$PROJECT_FOLDER"
COPY ./ $PROJECT_FOLDER

RUN export LICENSE_SERVER=fslicense.evry.com:8443
ARG ENVIRONMENT
ENV LICENSE_SERVER="fslicense.evry.com:8443"
ARG SUITENAME
ARG LOADFROMJSON

ADD /ext $READYAPI_FOLDER/bin/ext

WORKDIR $PROJECT_FOLDER

RUN chmod 755 $READYAPI_FOLDER/bin/ext/ready-api-license-manager-1.3.2.jar && \
    echo 1 | sh $READYAPI_FOLDER/bin/ext/license-manager.sh -s $LICENSE_SERVER

RUN chmod 755 multiProject.sh

ENTRYPOINT sh multiProject.sh $ENVIRONMENT "$SUITENAME" $LOADFROMJSON

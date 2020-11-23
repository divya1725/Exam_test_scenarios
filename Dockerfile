FROM fsnexus.evry.com:8085/smartbear/ready-api-soapui-testrunner:latest
RUN mkdir -p $PROJECT_FOLDER && \
   echo "$PROJECT_FOLDER"
COPY ./ $PROJECT_FOLDER

RUN export LICENSE_SERVER=fslicense.evry.com:8443
ARG COMMAND_LINE
ENV LICENSE_SERVER="fslicense.evry.com:8443"
ARG TAGS
ARG SUITENAME

ADD /ext $READYAPI_FOLDER/bin/ext

WORKDIR $PROJECT_FOLDER

RUN chmod 755 ready-api-license-manager-1.3.2.jar && \
    echo 1 | sh license-manager.sh -s $LICENSE_SERVER

RUN chmod 755 multiProject.sh

ENTRYPOINT sh multiProject.sh $COMMAND_LINE "$SUITENAME" $TAGS

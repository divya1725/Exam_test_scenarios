FROM smartbear/ready-api-soapui-testrunner:latest
RUN mkdir -p $PROJECT_FOLDER && \
   echo "$PROJECT_FOLDER"
COPY ./ $PROJECT_FOLDER

RUN export LICENSE_SERVER=https://manage.smartbear.com:443
ARG COMMAND_LINE
ENV LICENSE_SERVER="ffc18e5e-03d7-44a2-b136-6d7cdd73a313"
ARG SUITENAME
ARG LOADFROMJSON
ARG TAGS

ADD /ext $READYAPI_FOLDER/bin/ext

WORKDIR $PROJECT_FOLDER

RUN chmod 755 $READYAPI_FOLDER/bin/ext/ready-api-license-manager-1.3.2.jar && \
    echo 1 | sh $READYAPI_FOLDER/bin/ext/testrunner.bat -K $LICENSE_SERVER

RUN chmod 755 multiProject.sh

ENTRYPOINT /bin/bash multiProject.sh $COMMAND_LINE "$SUITENAME" $LOADFROMJSON $TAGS

#!/bin/sh

DIRNAME=`dirname $0`

# OS specific support (must be 'true' or 'false').
cygwin=false;
case "`uname`" in
    CYGWIN*)
        cygwin=true
        ;;
esac

if [ -f "$PWD/java" ]
then
        JAVA=$PWD/java
else
        if [ -f "$JAVA_HOME/bin/java" ]
        then
                JAVA=$JAVA_HOME/bin/java
        else
                echo JAVA_HOME is not set, unexpected results may occur.
		echo Set JAVA_HOME to the directory of your local Java installation to avoid this message.
		JAVA=java
        fi
fi

#JAVA 12
JAVA_OPTS="$JAVA_OPTS --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.invoke=ALL-UNNAMED --add-opens java.desktop/java.beans=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED"

export JAVA_OPTS

for file in $DIRNAME/ready-api-license-manager-*.jar; do
	$JAVA $JAVA_OPTS -jar $file "$@"
	break
done

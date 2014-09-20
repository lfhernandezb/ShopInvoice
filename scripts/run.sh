#!/bin/sh
##############################################################################
#
PROGRAM_PATH=`dirname $0`

JAVA_HOME=/opt/jdk1.6.0_38
#JAVA_HOME=/opt/jdk1.7.0_45
TMP_DIR=/var/tmp
CLASSPATH=\
$JAVA_HOME/lib/tools.jar:
CLASSPATH=\
/opt/apache-log4j-1.2.17/log4j-1.2.17.jar:\
/opt/ini4j-0.5.2-SNAPSHOT/ini4j-0.5.2-SNAPSHOT.jar:\
/opt/apache-log4j-1.2.17/log4j-1.2.17.jar:\
/opt/mysql-connector-java-5.1.22/mysql-connector-java-5.1.22-bin.jar:\
/opt/slf4j-1.7.5/slf4j-api-1.7.5.jar:\
/opt/slf4j-1.7.5/slf4j-simple-1.7.5.jar:\
/opt/guava-15.0.jar:\
/opt/commons-lang3-3.1/commons-lang3-3.1.jar:\
$PROGRAM_PATH/../bin/StoreInvoice.jar

# seteando el classpath
. $PROGRAM_PATH/env.sh

echo $CLASSPATH
#
# Start program
#
$JAVA_HOME/bin/java \
-Djava.io.tmpdir=$TMP_DIR \
-Dlog4j.debug=true \
-Dlog4j.configuration=file:$PROGRAM_PATH/../etc/log4j.xml \
-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.Log4JLogger \
-Dconfig_file=$PROGRAM_PATH/../etc/config.ini \
-cp $CLASSPATH \
$@

#$JAVACMD LoadCaf $@

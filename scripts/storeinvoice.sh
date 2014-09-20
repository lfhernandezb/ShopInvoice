#!/bin/sh
##############################################################################
#
DIR=`dirname $0`
cd $DIR

CLASS=ASDaemon
JAVA_HOME=/opt/jdk1.6.0_38/
DAEMON_HOME=/home/manada/ASDaemon
DAEMON_USER=manada
TMP_DIR=/var/tmp
CLASSPATH=\
$JAVA_HOME/lib/tools.jar:\
/opt/commons-daemon-1.0.12/commons-daemon-1.0.12.jar:\
/opt/apache-log4j-1.2.17/log4j-1.2.17.jar:\
/opt/mysql-connector-java-5.1.22/mysql-connector-java-5.1.22-bin.jar:\
/opt/commons-lang3-3.1/commons-lang3-3.1.jar:\
/opt/openmap-5.0/lib/openmap.jar:\
/opt/ini4j-0.5.2-SNAPSHOT/ini4j-0.5.2-SNAPSHOT.jar:\
/opt/bonecp-0.7.1.RELEASE/bonecp-0.7.1.RELEASE.jar:\
/opt/slf4j-1.7.5/slf4j-api-1.7.5.jar:\
/opt/slf4j-1.7.5/slf4j-simple-1.7.5.jar:\
/opt/guava-15.0.jar:\
$DAEMON_HOME/bin/ASDaemon.jar

start() {
    #
    # Start as_daemon
    #
    /opt/commons-daemon-1.0.12-src/src/native/unix/jsvc \
    -user $DAEMON_USER \
    -home $JAVA_HOME \
    -Djava.io.tmpdir=$TMP_DIR \
    -Dlog4j.debug=true \
    -Dlog4j.configuration=file:$DAEMON_HOME/etc/log4j.xml \
    -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.Log4JLogger \
    -Dconfig_file=$DAEMON_HOME/etc/asdaemon.conf \
    -errfile '&1' \
    -pidfile /var/run/jsvc.pid \
    -outfile $DAEMON_HOME/logs/daemon.out \
	-debug \
    -cp $CLASSPATH \
	$CLASS
    #
    # To get a verbose JVM
    #-verbose \
    # To get a debug of jsvc.
    #-debug \

}

stop(){
    #
    # Stop as_daemon
    #
    PID=`cat /var/run/jsvc.pid`
    kill $PID
    sleep 3

}

restart(){
    if [ -e /var/run/jsvc.pid ]; then
        printf "shutting down as_daemon \n"
    stop
    sleep 2
    fi
   printf "starting up as_daemon...\n"
       start
    sleep 2
   printf "as_daemon started.\n"

}

status(){
    if [ -e /var/run/jsvc.pid ]; then
                printf "as_daemon is running\n"

        fi
        if [ ! -e /var/run/jsvc.pid ]; then
                printf "as_daemon is *not* running.\n"

        fi
}

case "$1" in
  start)
    start
     ;;

   status)
    status
      ;;

  restart)
   if [ -e /var/run/jsvc.pid ]; then
        printf "shutting down as_daemon \n"
    stop
    sleep 3
    fi
       printf "starting up as_daemon...\n"
       start
    sleep 2
    printf "as_daemon started.\n"
      ;;
  stop)
    stop
      ;;
  *)
    echo "Usage as_daemon start/stop/restart/status"
    exit 1;;
esac

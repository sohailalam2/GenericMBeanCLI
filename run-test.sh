#!/bin/sh
### ==============================================================================###
##                                                                                 ##
##                    Generic MBean CLI Script for Linux based OS's                ##
##                                                                                 ##
### ==============================================================================###

### $Id: run-as.sh,v 1.1 2013/04/15 06:51:02 $ ###

DIRNAME=`dirname $0`
PROGNAME=`basename $0`
GREP="grep"

# Use the maximum available, or set MAX_FD != -1 to use that
MAX_FD="maximum"

#
# Helper to complain.
#
warn() {
    echo "${PROGNAME}: $*"
}

#
# Helper to puke.
#
die() {
    warn $*
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false;
darwin=false;
linux=false;
case "`uname`" in
    CYGWIN*)
        cygwin=true
        ;;

    Darwin*)
        darwin=true
        ;;
        
    Linux)
        linux=true
        ;;
esac

# Force IPv4 on Linux systems since IPv6 doesn't work correctly with jdk5 and lower
if [ "$linux" = "true" ]; then
   JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true -Xms256m -Xmx256m -server -XX:+UseNUMA -XX:+UseCompressedStrings -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=5566"
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
    [ -n "$JAVA_HOME" ] &&
        JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
    [ -n "$JAVAC_JAR" ] &&
        JAVAC_JAR=`cygpath --unix "$JAVAC_JAR"`
fi

# Increase the maximum file descriptors if we can
if [ "$cygwin" = "false" ]; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ]; then
	if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ]; then
	    # use the system max
	    MAX_FD="$MAX_FD_LIMIT"
	fi

	ulimit -n $MAX_FD
	if [ $? -ne 0 ]; then
	    warn "Could not set maximum file descriptor limit: $MAX_FD"
	fi
    else
	warn "Could not query system maximum file descriptor limit: $MAX_FD_LIMIT"
    fi
fi

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
	JAVA="$JAVA_HOME/bin/java"
    else
	JAVA="java"
    fi
fi

# Setup the classpath
Test="./test.jar"
if [ ! -f "$Test" ]; then
    die "Missing required file: $Test"
fi
AS_BOOT_CLASSPATH="$Test"

# Only include tools.jar if someone wants to use the JDK instead.
# compatible distribution which JAVA_HOME points to
if [ "x$JAVAC_JAR" = "x" ]; then
    JAVAC_JAR_FILE="$JAVA_HOME/lib/tools.jar"
else
    JAVAC_JAR_FILE="$JAVAC_JAR"
fi

if [ ! -f "$JAVAC_JAR_FILE" ]; then
   # MacOSX does not have a seperate tools.jar
   if [ "$darwin" != "true" -a "x$JAVAC_JAR" != "x" ]; then
      warn "Missing file: JAVAC_JAR=$JAVAC_JAR"
      warn "Unexpected results may occur."
   fi
   JAVAC_JAR_FILE=
fi

if [ "x$AS_CLASSPATH" = "x" ]; then
    AS_CLASSPATH="$AS_BOOT_CLASSPATH"
else
    AS_CLASSPATH="$AS_CLASSPATH:$AS_BOOT_CLASSPATH"
fi
if [ "x$JAVAC_JAR_FILE" != "x" ]; then
    AS_CLASSPATH="$AS_CLASSPATH:$JAVAC_JAR_FILE"
fi

# If -server not set in JAVA_OPTS, set it, if supported
SERVER_SET=`echo $JAVA_OPTS | $GREP "\-server"`
if [ "x$SERVER_SET" = "x" ]; then

    # Check for SUN(tm) JVM w/ HotSpot support
    if [ "x$HAS_HOTSPOT" = "x" ]; then
	HAS_HOTSPOT=`"$JAVA" -version 2>&1 | $GREP -i HotSpot`
    fi

    # Enable -server if we have Hotspot, unless we can't
    if [ "x$HAS_HOTSPOT" != "x" ]; then
	# MacOS does not support -server flag
	if [ "$darwin" != "true" ]; then
	    JAVA_OPTS="-server $JAVA_OPTS"
	fi
    fi
fi

# Setup JAVA specific properties
JAVA_OPTS="-Dprogram.name=$PROGNAME $JAVA_OPTS"

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  AS_CLASSPATH=`cygpath --path --windows "$AS_CLASSPATH"`
fi

#------------------------- MAIN STARTUP --------------------------

while true; do
   if [ "x$LAUNCH_AS_IN_BACKGROUND" = "x" ]; then
      # Execute the JVM in the foreground
      "$JAVA" $JAVA_OPTS \
         -classpath "$AS_CLASSPATH" \
         tests.mbean.TestBootstrap"$@"
      AS_STATUS=$?
   else
      # Execute the JVM in the background
      "$JAVA" $JAVA_OPTS \
         -classpath "$AS_CLASSPATH" \
         tests.mbean.TestBootstrap&
      AS_PID=$!
      # Trap common signals and relay them to the Generic MBean CLI process
      trap "kill -HUP  $AS_PID" HUP
      trap "kill -TERM $AS_PID" INT
      trap "kill -QUIT $AS_PID" QUIT
      trap "kill -PIPE $AS_PID" PIPE
      trap "kill -TERM $AS_PID" TERM
      # Wait until the background process exits
      WAIT_STATUS=128
      while [ "$WAIT_STATUS" -ge 128 ]; do
         wait $AS_PID 2>/dev/null
         WAIT_STATUS=$?
         if [ "${WAIT_STATUS}" -gt 128 ]; then
            SIGNAL=`expr ${WAIT_STATUS} - 128`
            SIGNAL_NAME=`kill -l ${SIGNAL}`
            echo "*** Generic MBean CLI process (${AS_PID}) received ${SIGNAL_NAME} signal ***" >&2
         fi          
      done
      if [ "${WAIT_STATUS}" -lt 127 ]; then
         AS_STATUS=$WAIT_STATUS
      else
         AS_STATUS=0
      fi      
   fi
   
   if [ "$AS_STATUS" -eq 10 ]; then
      echo "Restarting Test..."
   else
      exit $AS_STATUS
   fi
done

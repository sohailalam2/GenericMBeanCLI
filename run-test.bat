echo off
set Test=.\test.jar
if exist "%Test%" goto Test
echo Could not locate the Test JAR FILE. MAKE SURE YOU EXECUTE THIS SCRIPT FROM THE DIRECTORY OF THE JAR FILE
GOTO END

:Test

set JAVA=%JAVA_HOME%\bin\java
set JAVAC_JAR=%JAVA_HOME%\lib\tools.jar

"%JAVA%" -server -version 2>&1 | findstr /I hotspot > nul
if not errorlevel == 1 (set JAVA_OPTS=%JAVA_OPTS% -server)
set JAVA_OPTS=%JAVA_OPTS% -Djava.net.preferIPv4Stack=true -Xms256m -Xmx256m  -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=5566
if not "%JAVAC_JAR%" == "" set IMSASJAR=%JAVAC_JAR%;%Test%
set RUN_CLASSPATH=%Test%
if "%RUN_CLASSPATH%" == "" set RUN_CLASSPATH=%Test%

set AS_CLASSPATH=%RUN_CLASSPATH%

"%JAVA%" %JAVA_OPTS% ^
 -classpath "%AS_CLASSPATH%" ^
tests.mbean.TestBootstrap%*

:END

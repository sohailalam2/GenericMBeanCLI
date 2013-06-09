echo off
set GenericMBeanCli=.\GenericMBeanCli.jar
if exist "%GenericMBeanCli%" goto GenericMBeanCli
echo Could not locate GenericMBeanCli JAR FILE. MAKE SURE YOU EXECUTE THIS SCRIPT FROM THE DIRECTORY OF THE JAR FILE
GOTO END

:GenericMBeanCli

set JAVA=%JAVA_HOME%\bin\java
set JAVAC_JAR=%JAVA_HOME%\lib\tools.jar

"%JAVA%" -server -version 2>&1 | findstr /I hotspot > nul
if not errorlevel == 1 (set JAVA_OPTS=%JAVA_OPTS% -server)
set JAVA_OPTS=%JAVA_OPTS% -Djava.net.preferIPv4Stack=true -Xms256m -Xmx256m -server -XX:+UseNUMA
if not "%JAVAC_JAR%" == "" set IMSASJAR=%JAVAC_JAR%;%GenericMBeanCli%
set RUN_CLASSPATH=%GenericMBeanCli%
if "%RUN_CLASSPATH%" == "" set RUN_CLASSPATH=%GenericMBeanCli%

set AS_CLASSPATH=%RUN_CLASSPATH%

"%JAVA%" %JAVA_OPTS% ^
 -classpath "%AS_CLASSPATH%" ^
com.sohail.alam.generic.mbean.cli.GenericMBeanCliBootstrap%*

:END
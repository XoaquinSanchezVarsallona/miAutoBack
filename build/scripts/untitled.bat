@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  untitled startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and UNTITLED_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\untitled-1.0 SNAPSHOT-plain.jar;%APP_HOME%\lib\hibernate-core-5.6.5.Final.jar;%APP_HOME%\lib\hsqldb-2.7.2.jar;%APP_HOME%\lib\spark-core_2.12-3.5.1.jar;%APP_HOME%\lib\spark-core-2.9.3.jar;%APP_HOME%\lib\spring-boot-starter-web-2.5.6.jar;%APP_HOME%\lib\spark-network-shuffle_2.12-3.5.1.jar;%APP_HOME%\lib\spark-network-common_2.12-3.5.1.jar;%APP_HOME%\lib\tink-1.9.0.jar;%APP_HOME%\lib\gson-2.9.1.jar;%APP_HOME%\lib\jjwt-0.9.1.jar;%APP_HOME%\lib\hibernate-commons-annotations-5.1.2.Final.jar;%APP_HOME%\lib\jboss-logging-3.4.2.Final.jar;%APP_HOME%\lib\javax.persistence-api-2.2.jar;%APP_HOME%\lib\byte-buddy-1.10.22.jar;%APP_HOME%\lib\antlr-2.7.7.jar;%APP_HOME%\lib\jboss-transaction-api_1.2_spec-1.1.1.Final.jar;%APP_HOME%\lib\jandex-2.4.2.Final.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\jaxb-api-2.3.1.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\jaxb-runtime-2.3.5.jar;%APP_HOME%\lib\avro-mapred-1.11.2.jar;%APP_HOME%\lib\avro-ipc-1.11.2.jar;%APP_HOME%\lib\avro-1.11.2.jar;%APP_HOME%\lib\spark-unsafe_2.12-3.5.1.jar;%APP_HOME%\lib\chill_2.12-0.10.0.jar;%APP_HOME%\lib\chill-java-0.10.0.jar;%APP_HOME%\lib\xbean-asm9-shaded-4.23.jar;%APP_HOME%\lib\hadoop-client-runtime-3.3.4.jar;%APP_HOME%\lib\hadoop-client-api-3.3.4.jar;%APP_HOME%\lib\spark-launcher_2.12-3.5.1.jar;%APP_HOME%\lib\spark-kvstore_2.12-3.5.1.jar;%APP_HOME%\lib\spark-common-utils_2.12-3.5.1.jar;%APP_HOME%\lib\activation-1.1.1.jar;%APP_HOME%\lib\curator-recipes-2.13.0.jar;%APP_HOME%\lib\curator-framework-2.13.0.jar;%APP_HOME%\lib\curator-client-2.13.0.jar;%APP_HOME%\lib\zookeeper-3.6.3.jar;%APP_HOME%\lib\jakarta.servlet-api-4.0.4.jar;%APP_HOME%\lib\commons-codec-1.15.jar;%APP_HOME%\lib\commons-compress-1.23.0.jar;%APP_HOME%\lib\commons-text-1.10.0.jar;%APP_HOME%\lib\commons-lang3-3.12.0.jar;%APP_HOME%\lib\commons-math3-3.6.1.jar;%APP_HOME%\lib\commons-io-2.13.0.jar;%APP_HOME%\lib\commons-collections-3.2.2.jar;%APP_HOME%\lib\commons-collections4-4.4.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\compress-lzf-1.1.2.jar;%APP_HOME%\lib\snappy-java-1.1.10.3.jar;%APP_HOME%\lib\lz4-java-1.8.0.jar;%APP_HOME%\lib\zstd-jni-1.5.5-4.jar;%APP_HOME%\lib\RoaringBitmap-0.9.45.jar;%APP_HOME%\lib\scala-xml_2.12-2.1.0.jar;%APP_HOME%\lib\scala-reflect-2.12.18.jar;%APP_HOME%\lib\json4s-jackson_2.12-3.7.0-M11.jar;%APP_HOME%\lib\metrics-json-4.1.26.jar;%APP_HOME%\lib\spring-boot-starter-json-2.5.6.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.12.5.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.12.5.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.12.5.jar;%APP_HOME%\lib\jackson-databind-2.12.5.jar;%APP_HOME%\lib\jackson-core-2.12.5.jar;%APP_HOME%\lib\jackson-annotations-2.12.5.jar;%APP_HOME%\lib\jackson-module-scala_2.12-2.12.5.jar;%APP_HOME%\lib\spark-tags_2.12-3.5.1.jar;%APP_HOME%\lib\json4s-core_2.12-3.7.0-M11.jar;%APP_HOME%\lib\json4s-ast_2.12-3.7.0-M11.jar;%APP_HOME%\lib\json4s-scalap_2.12-3.7.0-M11.jar;%APP_HOME%\lib\scala-library-2.12.18.jar;%APP_HOME%\lib\jersey-container-servlet-2.33.jar;%APP_HOME%\lib\jersey-container-servlet-core-2.33.jar;%APP_HOME%\lib\jersey-server-2.33.jar;%APP_HOME%\lib\jersey-client-2.33.jar;%APP_HOME%\lib\jersey-hk2-2.33.jar;%APP_HOME%\lib\jersey-common-2.33.jar;%APP_HOME%\lib\netty-all-4.1.69.Final.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.69.Final-linux-x86_64.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.69.Final-linux-aarch_64.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.69.Final.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.69.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.69.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.69.Final.jar;%APP_HOME%\lib\stream-2.9.6.jar;%APP_HOME%\lib\metrics-jvm-4.1.26.jar;%APP_HOME%\lib\metrics-graphite-4.1.26.jar;%APP_HOME%\lib\metrics-jmx-4.1.26.jar;%APP_HOME%\lib\metrics-core-4.1.26.jar;%APP_HOME%\lib\ivy-2.5.1.jar;%APP_HOME%\lib\oro-2.0.8.jar;%APP_HOME%\lib\pickle-1.3.jar;%APP_HOME%\lib\py4j-0.10.9.7.jar;%APP_HOME%\lib\commons-crypto-1.1.0.jar;%APP_HOME%\lib\spring-boot-starter-2.5.6.jar;%APP_HOME%\lib\spring-boot-starter-logging-2.5.6.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.32.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.32.jar;%APP_HOME%\lib\log4j-slf4j2-impl-2.20.0.jar;%APP_HOME%\lib\logback-classic-1.2.6.jar;%APP_HOME%\lib\log4j-to-slf4j-2.14.1.jar;%APP_HOME%\lib\slf4j-api-1.7.32.jar;%APP_HOME%\lib\jetty-webapp-9.4.44.v20210927.jar;%APP_HOME%\lib\websocket-server-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-servlet-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-security-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-server-9.4.44.v20210927.jar;%APP_HOME%\lib\websocket-servlet-9.4.44.v20210927.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-2.5.6.jar;%APP_HOME%\lib\spring-webmvc-5.3.12.jar;%APP_HOME%\lib\spring-web-5.3.12.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.3.jar;%APP_HOME%\lib\txw2-2.3.5.jar;%APP_HOME%\lib\istack-commons-runtime-3.0.12.jar;%APP_HOME%\lib\jakarta.activation-1.2.2.jar;%APP_HOME%\lib\kryo-shaded-4.0.2.jar;%APP_HOME%\lib\commons-logging-1.1.3.jar;%APP_HOME%\lib\leveldbjni-all-1.8.jar;%APP_HOME%\lib\rocksdbjni-8.3.2.jar;%APP_HOME%\lib\log4j-core-2.14.1.jar;%APP_HOME%\lib\log4j-1.2-api-2.14.1.jar;%APP_HOME%\lib\log4j-api-2.14.1.jar;%APP_HOME%\lib\zookeeper-jute-3.6.3.jar;%APP_HOME%\lib\audience-annotations-0.5.0.jar;%APP_HOME%\lib\shims-0.9.45.jar;%APP_HOME%\lib\jakarta.ws.rs-api-2.1.6.jar;%APP_HOME%\lib\hk2-locator-2.6.1.jar;%APP_HOME%\lib\hk2-api-2.6.1.jar;%APP_HOME%\lib\hk2-utils-2.6.1.jar;%APP_HOME%\lib\jakarta.inject-2.6.1.jar;%APP_HOME%\lib\jakarta.annotation-api-1.3.5.jar;%APP_HOME%\lib\osgi-resource-locator-1.0.3.jar;%APP_HOME%\lib\jakarta.validation-api-2.0.2.jar;%APP_HOME%\lib\javassist-3.25.0-GA.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.69.Final.jar;%APP_HOME%\lib\netty-handler-4.1.69.Final.jar;%APP_HOME%\lib\netty-codec-4.1.69.Final.jar;%APP_HOME%\lib\netty-transport-4.1.69.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.69.Final.jar;%APP_HOME%\lib\netty-codec-http-4.1.69.Final.jar;%APP_HOME%\lib\netty-codec-http2-4.1.69.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.1.69.Final.jar;%APP_HOME%\lib\aalto-xml-1.0.0.jar;%APP_HOME%\lib\stax2-api-4.0.0.jar;%APP_HOME%\lib\netty-resolver-4.1.69.Final.jar;%APP_HOME%\lib\netty-common-4.1.69.Final.jar;%APP_HOME%\lib\netty-handler-proxy-4.1.69.Final.jar;%APP_HOME%\lib\rxtx-2.1.7.jar;%APP_HOME%\lib\barchart-udt-bundle-2.3.0.jar;%APP_HOME%\lib\paranamer-2.8.jar;%APP_HOME%\lib\javax.servlet-api-4.0.1.jar;%APP_HOME%\lib\websocket-client-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-client-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-http-9.4.44.v20210927.jar;%APP_HOME%\lib\websocket-common-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-io-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-xml-9.4.44.v20210927.jar;%APP_HOME%\lib\websocket-api-9.4.44.v20210927.jar;%APP_HOME%\lib\spring-boot-autoconfigure-2.5.6.jar;%APP_HOME%\lib\spring-boot-2.5.6.jar;%APP_HOME%\lib\spring-context-5.3.12.jar;%APP_HOME%\lib\spring-expression-5.3.12.jar;%APP_HOME%\lib\spring-aop-5.3.12.jar;%APP_HOME%\lib\spring-beans-5.3.12.jar;%APP_HOME%\lib\spring-core-5.3.12.jar;%APP_HOME%\lib\snakeyaml-1.28.jar;%APP_HOME%\lib\tomcat-embed-websocket-9.0.54.jar;%APP_HOME%\lib\tomcat-embed-core-9.0.54.jar;%APP_HOME%\lib\tomcat-embed-el-9.0.54.jar;%APP_HOME%\lib\xz-1.9.jar;%APP_HOME%\lib\minlog-1.3.0.jar;%APP_HOME%\lib\objenesis-2.5.1.jar;%APP_HOME%\lib\protobuf-java-3.19.6.jar;%APP_HOME%\lib\joda-time-2.12.5.jar;%APP_HOME%\lib\aopalliance-repackaged-2.6.1.jar;%APP_HOME%\lib\jetty-util-ajax-9.4.44.v20210927.jar;%APP_HOME%\lib\jetty-util-9.4.44.v20210927.jar;%APP_HOME%\lib\spring-jcl-5.3.12.jar;%APP_HOME%\lib\guava-16.0.1.jar;%APP_HOME%\lib\logback-core-1.2.6.jar


@rem Execute untitled
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %UNTITLED_OPTS%  -classpath "%CLASSPATH%" main %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable UNTITLED_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%UNTITLED_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

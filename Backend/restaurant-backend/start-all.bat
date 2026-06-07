@echo off
setlocal enabledelayedexpansion

title Restaurant Backend

set "BASE=%~dp0"
set "PSQL=C:\Program Files\PostgreSQL\18\bin\psql.exe"
set "PGPASSWORD=postgres"
set "JV=java -jar"

echo.
echo =====================================================
echo  RESTAURANT BACKEND - INICIO COMPLETO
echo =====================================================
echo.

:: --- DOCKER ---
echo [1/5] Docker...
docker info >nul 2>&1
if errorlevel 1 (
    echo   Iniciando Docker Desktop...
    start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe"
    timeout /t 30 /nobreak >nul
)
docker compose -f "%BASE%docker-compose.yml" up -d rabbitmq redis prometheus grafana >nul 2>&1
echo   Esperando 15s...
timeout /t 15 /nobreak >nul
echo   OK

:: --- POSTGRESQL ---
echo.
echo [2/5] Bases de datos PostgreSQL...
"%PSQL%" -U postgres -h localhost -c "CREATE DATABASE db_auth;"     >nul 2>&1
"%PSQL%" -U postgres -h localhost -c "CREATE DATABASE db_maestros;" >nul 2>&1
"%PSQL%" -U postgres -h localhost -c "CREATE DATABASE db_ventas;"   >nul 2>&1
echo   OK

:: --- BUILD ---
echo.
echo [3/5] Compilando Maven (1-2 min)...
cd /d "%BASE%"
call mvn clean install -DskipTests -q
if errorlevel 1 (
    echo.
    echo   ERROR en el build. Revisa los errores de Maven.
    pause
    exit /b 1
)
echo   OK

:: --- MICROSERVICIOS ---
echo.
echo [4/5] Iniciando microservicios...
echo.

set "JAR1=%BASE%eureka-server\target\eureka-server-1.0.0.jar"
set "JAR2=%BASE%config-server\target\config-server-1.0.0.jar"
set "JAR3=%BASE%ms-auth-security\target\ms-auth-security-1.0.0.jar"
set "JAR4=%BASE%ms-core-maestros\target\ms-core-maestros-1.0.0.jar"
set "JAR5=%BASE%ms-ventas\target\ms-ventas-1.0.0.jar"
set "JAR6=%BASE%ms-notificaciones\target\ms-notificaciones-1.0.0.jar"
set "JAR7=%BASE%ms-reportes\target\ms-reportes-1.0.0.jar"
set "JAR8=%BASE%api-gateway\target\api-gateway-1.0.0.jar"

echo   [1/8] eureka-server    :8761
start "eureka-server"    /min %JV% "%JAR1%"
timeout /t 25 /nobreak >nul

echo   [2/8] config-server    :8888
start "config-server"    /min %JV% "%JAR2%"
timeout /t 10 /nobreak >nul

echo   [3/8] ms-auth-security :8081
start "ms-auth"          /min %JV% "%JAR3%"
timeout /t 15 /nobreak >nul

echo   [4/8] ms-core-maestros :8082
start "ms-maestros"      /min %JV% "%JAR4%"
timeout /t 15 /nobreak >nul

echo   [5/8] ms-ventas        :8083
start "ms-ventas"        /min %JV% "%JAR5%"
timeout /t 12 /nobreak >nul

echo   [6/8] ms-notificaciones:8084
start "ms-notif"         /min %JV% "%JAR6%"
timeout /t 8  /nobreak >nul

echo   [7/8] ms-reportes      :8085
start "ms-reportes"      /min %JV% "%JAR7%"
timeout /t 8  /nobreak >nul

echo   [8/8] api-gateway      :8080
start "api-gateway"      /min %JV% "%JAR8%"
timeout /t 20 /nobreak >nul

:: --- VERIFICACION ---
echo.
echo [5/5] Verificando servicios...
echo.

set OK=0
set FAIL=0

call :CHECK Eureka          8761 actuator/health
call :CHECK Auth            8081 actuator/health
call :CHECK Maestros        8082 actuator/health
call :CHECK Ventas          8083 actuator/health
call :CHECK Notificaciones  8084 actuator/health
call :CHECK Reportes        8085 actuator/health
call :CHECK Gateway         8080 actuator/health

echo.
echo =====================================================
echo  OK: !OK!   FAIL: !FAIL!
echo =====================================================
echo.
echo  Eureka    : http://localhost:8761
echo  Swagger   : http://localhost:8080/swagger-ui.html
echo  RabbitMQ  : http://localhost:15672  (guest/guest)
echo  Grafana   : http://localhost:3001   (admin/admin)
echo  Prometheus: http://localhost:9090
echo.
echo  USUARIO: admin / admin123
echo  FRONT  : ejecuta start-frontend.bat
echo.
pause
endlocal
goto :EOF

:CHECK
set "SVC=%1"
set "PORT=%2"
set "EP=%3"
curl -s -o nul -w "%%{http_code}" "http://localhost:%PORT%/%EP%" 2>nul | find "200" >nul 2>&1
if errorlevel 1 (
    echo   [ FAIL ] %SVC% ^(:%PORT%^)
    set /a FAIL=!FAIL!+1
) else (
    echo   [  OK  ] %SVC% ^(:%PORT%^)
    set /a OK=!OK!+1
)
goto :EOF

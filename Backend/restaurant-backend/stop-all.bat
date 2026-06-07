@echo off
title Restaurant Backend - Detener

echo.
echo =====================================================
echo  DETENIENDO TODOS LOS SERVICIOS
echo =====================================================
echo.

echo [1/2] Cerrando microservicios Java...
for %%W in (eureka-server config-server ms-auth ms-maestros ms-ventas ms-notif ms-reportes api-gateway) do (
    taskkill /fi "WINDOWTITLE eq %%W" /f >nul 2>&1
    echo   Detenido: %%W
)

echo.
echo [2/2] Deteniendo Docker...
docker compose -f "%~dp0docker-compose.yml" down >nul 2>&1
echo   OK

echo.
echo  Todos los servicios detenidos.
echo.
pause

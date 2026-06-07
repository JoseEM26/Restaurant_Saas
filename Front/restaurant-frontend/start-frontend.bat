@echo off
chcp 65001 >nul
color 0B
title Restaurant Frontend

echo.
echo  =========================================================
echo   RESTAURANT FRONTEND - Angular 20 + Ionic
echo  =========================================================
echo.

cd /d "%~dp0"

:: Instalar dependencias si no existen
if not exist "node_modules" (
    echo [1/2] Instalando dependencias npm ^(primera vez, puede tardar^)...
    npm install --legacy-peer-deps
    if errorlevel 1 (
        echo   ERROR: Fallo npm install
        pause
        exit /b 1
    )
) else (
    echo [1/2] Dependencias ya instaladas. OK
)

echo.
echo [2/2] Iniciando servidor Angular...
echo.
echo   Frontend disponible en: http://localhost:4200
echo.
echo   CREDENCIALES:
echo     Usuario  : admin
echo     Password : admin123
echo.

npm start

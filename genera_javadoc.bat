@echo off
echo ================================
echo   Generazione JavaDoc...
echo ================================

REM Percorso JavaFX
set JAVAFX=lib\javafx-sdk-21.0.2\lib

REM Percorso librerie del progetto
set LIB=lib\*

REM Genera JavaDoc
javadoc -d docs -sourcepath src -classpath "%LIB%;%JAVAFX%\*" -subpackages "server:client:common"

echo.
echo ================================
echo   JavaDoc generato in /docs
echo ================================
pause


@echo off
cd src

echo Compiling Java files...
javac *.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b
)

echo Running Sudoku App...
java App

pause

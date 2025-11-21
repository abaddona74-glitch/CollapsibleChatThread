@echo off
rem Simple helper: connect to device, build debug APK, install, and launch
rem Usage: run from repo root in cmd.exe. Optionally set ADB_HOST env var.

if "%ADB_HOST%"=="" (
  set ADB_HOST=192.168.1.181:5555
)

echo Connecting to device %ADB_HOST%...
adb connect %ADB_HOST%
if errorlevel 1 (
  echo Warning: adb connect failed or adb not available. Ensure ADB is installed and in PATH.
)

echo Building and installing debug APK (this may take a while)...
call gradlew.bat installDebug
if errorlevel 1 (
  echo gradle installDebug failed. You can try: call gradlew.bat assembleDebug
  exit /b 1
)

set APK_PATH=app\build\outputs\apk\debug\app-debug.apk
if exist "%APK_PATH%" (
  echo Installing %APK_PATH% to %ADB_HOST%...
  adb -s %ADB_HOST% install -r "%APK_PATH%"
) else (
  echo APK not found at %APK_PATH%. The Gradle installDebug step usually installs directly.
)

echo Launching app on device...
adb -s %ADB_HOST% shell am start -n com.maxmudbek.collapsiblechatthread/.MainActivity

echo Done.

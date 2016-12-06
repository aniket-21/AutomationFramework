@echo off
setlocal enabledelayedexpansion

echo Checking whether ANDROID_HOME is defined
if not defined ANDROID_HOME (
	echo ANDROID_HOME is not defined. Please set ANDROID_HOME to android sdk path
	exit -1
)
cd "%ANDROID_HOME%\platform-tools"

echo Checking whether any device is connected before starting genymotion emulator...
FOR /F "tokens=1-2 skip=1" %%G IN ('adb devices') DO (	
	if /I %%H==device set device_connected=true
	if /I %%H==emulator set device_connected=true

	if /I !device_connected!==true (
			@echo %%G is already connected. Won't start the genymotion emulator	
			exit 0
	)
)

echo Checking whether Genymotion is installed
cd "C:\Program Files\Genymobile\Genymotion" > nul 2> nul
if %ERRORLEVEL% GTR 0 (
	echo Genymotion path not found. Kindly install Genymotion
	exit -1
) 

if [%1]==[] ( echo No emulator name is passed to the script. Default emulator will be started.
) else ( 
	echo Checking whether %~1 is available.
	FOR /F "tokens=1-6 delims=^|" %%G IN ('genyshell.exe -c "devices list" ^| findstr %1') DO ( 
		set actualName=%%L
		set expectedName=%~1

		if /I "!actualName!"==" !expectedName!" (
			set deviceToBeLaunched=!expectedName!
			Goto LAUNCH
		)
	)

	echo %~1 is not available. 
	echo Will try to launch default emulator.
)

FOR /F "tokens=1-6 delims=^|" %%G IN ('genyshell.exe -c "devices list" ^| findstr "0.0.0.0"') DO ( 
		set defaultDevice=%%L		
		set deviceToBeLaunched=!defaultDevice!
)

if not defined defaultDevice (
	echo No default Genymotion device available. Please add atleast one device.
	pause
	exit -1
)


:LAUNCH		

	rem //TODO: code to trim the emulator name if its being fetched from available devices	
	for /f "tokens=* delims= " %%a in ("!deviceToBeLaunched!") do set deviceToBeLaunched=%%a
	set deviceToBeLaunched=!deviceToBeLaunched:~0,-2!
	echo Launching [!deviceToBeLaunched!]....

	start player.exe --vm-name "!deviceToBeLaunched!"
	cd "%ANDROID_HOME%\platform-tools"
	Set /a iCnt=0
	GoTo WAIT_TILL_DEVICE_READY

:WAIT_TILL_DEVICE_READY
	
	Set /a iCnt+=1
	echo !iCnt!
	if !iCnt! GTR 30 (
		echo Device didnt boot up in expected time
		exit -1
	)

	echo Waiting for device to be ready ...
	FOR /F "tokens=1" %%G IN ('adb shell getprop init.svc.bootanim') Do (
		Set device_status=%%G
		Set device_status=!device_status:~0,-1!
		echo status [!device_status!]
	)

	if /I not [!device_status!]==[stopped] (
		ping -n 10 127.0.0.1 > NUL
		GoTo WAIT_TILL_DEVICE_READY	
	)

exit 0
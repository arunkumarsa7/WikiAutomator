@echo off
REM "Switching to current folder"
cd /d %~dp0

echo  _    _  __  _ _   __     __  _  _  ____  __  __  __   __  ____  __  ___  
echo ( \/\/ )(  )( ) ) (  )   (  )( )( )(_  _)/  \(  \/  ) (  )(_  _)/  \(  ,) 
echo  \    /  )(  )  \  )(    /__\ )()(   )( ( () ))    (  /__\  )( ( () ))  \ 
echo   \/\/  (__)(_)\_)(__)  (_)(_)\__/  (__) \__/(_/\/\_)(_)(_)(__) \__/(_)\_)
echo	 Tool that automates the creation, updation and summary report generation of Entwikler News
echo.

SET var=%cd%
java -Xmx1536m -Dfile.encoding=UTF-8 -jar "%var%\lib\wiki-automator.jar"
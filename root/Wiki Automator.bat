@echo off
REM "Switching to current folder"
cd /d %~dp0

echo   __  _  _  ___   __  _ _   __  _  _    _  _  ___  __    ___  ___  ___ 
echo  / _)( )( )(  _) / _)( ) ) (  )( \( )  ( )( )(  _)(  )  (  ,\(  _)(  ,)
echo ( (_  )__(  ) _)( (_  )  \  )(  )  (    )__(  ) _) )(__  ) _/ ) _) )  \
echo  \__)(_)(_)(___) \__)(_)\_)(__)(_)\_)  (_)(_)(___)(____)(_)  (___)(_)\_)
echo   Tool that automates the creation and updation of files for Test Iteration check-in process
echo.

SET var=%cd%
java -Xmx1536m -Dfile.encoding=UTF-8 -jar "%var%\lib\wiki-automator.jar"
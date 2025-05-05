@echo off
cd .\deployed\conwaycellsqak-1.0\bin
for /l %%i in (1,1,2) do (
	echo Avvio N=%%i...
    start cmd /c "cd /d %cd% && conwaycellsqak.bat"
) 
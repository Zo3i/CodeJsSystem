@echo off
rem /**
rem  * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
echo.
echo [��Ϣ] ������Ŀ�汾�š�
echo.

%~d0
cd %~dp0

set /p new=�������°汾�ţ�
echo.

set /p choice=�������ʽ���밴 "y" ���������������
if /i "%choice%" neq "y" set new=%new%-SNAPSHOT
echo.

cd ../jeesite

rem ����pom�汾��
cd ../parent
call mvn versions:set -DnewVersion=%new%

cd ../web
call mvn versions:set -DnewVersion=%new%

pause
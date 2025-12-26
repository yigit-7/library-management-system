@echo off
setlocal

:: Configuration
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=library
set DB_USER=postgres

echo Starting Database Setup for Library Management System...

:: Check if psql is installed
where psql >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: 'psql' is not installed or not in your PATH.
    exit /b 1
)

echo Connecting to %DB_NAME% as %DB_USER%...

:: Run Scripts
echo 1. Cleaning Database (00-clean.sql)...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f schema/00-clean.sql

echo 2. Creating Tables (01-tables.sql)...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f schema/01-tables.sql

echo 3. Adding Constraints (02-constraints.sql)...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f schema/02-constraints.sql

echo 4. Creating Triggers (03-triggers.sql)...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f schema/03-triggers.sql

echo 5. Seeding Data (04-data.sql)...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f schema/04-data.sql

echo Database Setup Complete!
endlocal

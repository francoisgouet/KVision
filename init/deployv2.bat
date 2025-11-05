@echo off
REM ====================================================
REM Script pour enrichir tous les index.html existants
REM ====================================================

REM ------------------------
REM Définir le dossier racine
REM ------------------------
set "racine=C:\Users\Utilisateur\Documents\Culture"

if not exist "%racine%" (
    echo Le dossier racine "%racine%" n'existe pas !
    pause
    exit /b
)

REM ------------------------
REM Fonction pour modifier index.html
REM ------------------------
:modifier_html
set "fichier=%~1"
set "tmp=%fichier%.tmp"

setlocal enabledelayedexpansion
set "navAjoute=0"
set "cssAjoute=0"
set "jsAjoute=0"

(
    for /f "usebackq delims=" %%l in ("%fichier%") do (
        set "ligne=%%l"

        REM Ajouter <link> si dans <head> et absent
        if !cssAjoute! equ 0 (
            echo !ligne! | findstr /c:"<link rel=""stylesheet"" href=""style.css"">" ^>nul
            if errorlevel 1 (
                echo !ligne! | findstr /c:"<head>" ^>nul
                if not errorlevel 1 (
                    echo !ligne!
                    echo     ^<link rel="stylesheet" href="style.css"^>
                    set cssAjoute=1
                    goto :nextline
                )
            ) else (
                set cssAjoute=1
            )
        )

        REM Ajouter <nav> après <body> si absent
        if !navAjoute! equ 0 (
            echo !ligne! | findstr /c:"<nav>" ^>nul
            if errorlevel 1 (
                echo !ligne! | findstr /c:"<body>" ^>nul
                if not errorlevel 1 (
                    echo !ligne!
                    echo     ^<nav^>^<a href=".."\^>⬅ Retour^</a^>^</nav^>
                    set navAjoute=1
                    goto :nextline
                )
            ) else (
                set navAjoute=1
            )
        )

        REM Ajouter <script> avant </body> si absent
        if !jsAjoute! equ 0 (
            echo !ligne! | findstr /c:"<script src=""script.js""></script>" ^>nul
            if errorlevel 1 (
                echo !ligne! | findstr /c:"</body>" ^>nul
                if not errorlevel 1 (
                    echo     ^<script src="script.js"^>^</script^>
                    set jsAjoute=1
                )
            ) else (
                set jsAjoute=1
            )
        )

        echo !ligne!

        :nextline
    )
) ^> "%tmp%"

move /y "%tmp%" "%fichier%" >nul
endlocal
exit /b

REM ------------------------
REM Boucle pour tous les index.html
REM ------------------------
for /r "%racine%" %%f in (index.html) do (
    call :modifier_html "%%f"
)

echo Tous les index.html ont été enrichis avec CSS, JS et nav !
pause

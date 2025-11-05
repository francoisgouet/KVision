echo "sdq"
set "racine=C:\Users\Utilisateur\Documents\Culture"

REM Parcourt tous les dossiers et sous-dossiers
for /r "%racine%" %%D in (.) do (
    if exist "%%D" (
        REM Récupère le nom du dossier courant
        for %%A in ("%%D") do set "nom=%%~nxA"

        REM index.html
        > "%%D\index.html" echo ^<!DOCTYPE html^>
        >>"%%D\index.html" echo ^<html lang="fr"^>
        >>"%%D\index.html" echo ^<head^>
        >>"%%D\index.html" echo   ^<meta charset="UTF-8"^>
        >>"%%D\index.html" echo   ^<title^>!nom!^</title^>
        >>"%%D\index.html" echo   ^<link rel="stylesheet" href="style.css"^>
        >>"%%D\index.html" echo ^</head^>
        >>"%%D\index.html" echo ^<body^>
        >>"%%D\index.html" echo   ^<h1^>!nom!^</h1^>
        >>"%%D\index.html" echo   ^<script src="script.js"^>^</script^>
        >>"%%D\index.html" echo ^</body^>
        >>"%%D\index.html" echo ^</html^>

        REM style.css
        > "%%D\style.css" echo body { font-family: Arial; }

        REM script.js
        > "%%D\script.js" echo console.log("Hello from !nom!");
    )
)

echo.
echo Structure web générée dans %racine%
pause

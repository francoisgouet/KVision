# ================================================
# Script PowerShell : enrichir tous les index.html
# ================================================

# Dossier racine à traiter
$racine = "C:\Users\TonNom\Desktop\MonProjet"

# Chercher tous les index.html dans tous les sous-dossiers
Get-ChildItem -Path $racine -Filter "index.html" -Recurse | ForEach-Object {

    $fichier = $_.FullName
    # Lire tout le contenu du fichier
    $html = Get-Content $fichier -Raw

    # Ajouter <link> dans <head> si absent
    if (-not ($html -match '<link rel="stylesheet" href="style.css">')) {
        $html = $html -replace '(?<=<head>)', "`r`n    <link rel=""stylesheet"" href=""style.css"">"
    }

    # Ajouter <nav> après <body> si absent
    if (-not ($html -match '<nav>')) {
        $html = $html -replace '(?<=<body>)', "`r`n    <nav><a href="".."">⬅ Retour</a></nav>"
    }

    # Ajouter <script> avant </body> si absent
    if (-not ($html -match '<script src="script.js"></script>')) {
        $html = $html -replace '(?=</body>)', "`r`n    <script src=""script.js""></script>"
    }

    # Réécrire le fichier avec les modifications
    Set-Content -Path $fichier -Value $html -Encoding UTF8
}

Write-Host "Tous les index.html ont été enrichis avec CSS, JS et nav !" -ForegroundColor Green

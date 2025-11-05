# Dossier racine à traiter
$racine = "C:\Users\Utilisateur\Documents\Culture"

# Chercher tous les index.html dans tous les sous-dossiers
Get-ChildItem -Path $racine -Filter "index.html" -Recurse | ForEach-Object {

    $fichier = $_.FullName
    $html = Get-Content $fichier -Raw

    # Ajouter <nav> après <body> si absent
    if (-not ($html -match '<nav>')) {
        $html = $html -replace '(?<=<body>)', "`r`n    <nav><a href="".."">⬅ Retour</a></nav>"
    }

    # Réécrire le fichier
    Set-Content -Path $fichier -Value $html -Encoding UTF8
}

Write-Host "Nav ajouté dans tous les index.html !" -ForegroundColor Green

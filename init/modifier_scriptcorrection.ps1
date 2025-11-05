# Chemin du dossier racine contenant les sous-dossiers
$racine = "C:\Users\Utilisateur\Documents\Culture"

# Contenu minimal du script_correction.js à créer s'il n'existe pas
$scriptCorrectionContent = @"
Write-Host 'Script correction initialisé dans le dossier : $($dossier.Name)'
# Ajoutez ici votre code de correction PowerShell spécifique
"@

# Liste des dossiers à exclure (ici noms simples)
$exclusions = @("init", "server","Apprentissage") 

# Liste des dossiers (seulement les répertoires)
# sauf ceux à exclure
$dossiers = Get-ChildItem -Path $racine -Directory | Where-Object { $exclusions -notcontains $_.Name }

foreach ($dossier in $dossiers) {
    Write-Host "Je traite : $($dossier.FullName)" -ForegroundColor Cyan
    
	# Chemin complet vers le script de correction dans ce dossier
    $scriptPath = Join-Path $dossier.FullName "scriptCorrection.ps1"
	# Échapper les apostrophes en doublant les '
    $safeFolderName = $dossier.Name -replace "'", "''"
	# Vérifie si le script existe, sinon le crée
    if (-not (Test-Path -Path $scriptPath)) {
        Write-Host "Création du script de correction dans $($dossier.Name)" -ForegroundColor Yellow
        $scriptCorrectionContent | Out-File -FilePath $scriptPath -Encoding utf8
    } else {
        Write-Host "Script de correction déjà présent dans $($dossier.Name)" -ForegroundColor Green
		# Sinon, créer un nouveau script avec le contenu de base
        Write-Host "Création d'un nouveau script dans $($dossier.Name)" -ForegroundColor Green
        #########################
		######## new content 
		###########################
		$newContent = @"
		# Récupérer le chemin du script actuel
		# Récupérer le chemin du script en cours
"@
		try {
    # Récupérer le chemin du script en cours
    # $scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
	Write-Host "$scriptDir" -ForegroundColor Green
    # Chemin vers index.html dans ce dossier
    $indexPath = Join-Path $safeFolderName "index.html"

    if (Test-Path $indexPath) {
        # Lire le contenu complet d'index.html
        $content = Get-Content -Path $indexPath -Raw

        # Récupérer le nom du dossier (utilisé pour le titre)
        $folderName = Split-Path $safeFolderName -Leaf

        # Échapper les apostrophes dans le nom du dossier
        $safeFolderName = $folderName -replace "'", "''"

        # Modifier ou insérer la balise <title>
        if ($content -match '<title>.*?<\/title>') {
            $content = $content -replace '<title>.*?<\/title>', "<title>$safeFolderName</title>"
        } else {
            $content = $content -replace '(<head.*?>)', "`$1`n<title>$safeFolderName</title>"
        }
		
		
		
		# Vérifier si une balise <h1> existe
    if ($content -match '<h1>.*?</h1>') {
        # Remplacer le contenu de la balise <h1>
        $content = $content -replace '<h1>.*?</h1>', "<h1>$safeFolderName</h1>"
		Write-Host "balise h1 modif" -ForegroundColor Green
    } else {
        # Si aucune balise <h1> n'existe, on l'ajoute juste après <body>
        $content = $content -replace '(<body.*?>)', "`$1`n<h1>$safeFolderName</h1>"
    }
	
	
	
	# Chercher toutes les balises <script ...> avec src="
        # Pour faire un remplacement simple, on peut faire :
        # 1. Extraire les src="valeur"
        # 2. Pour chacune, si elle ne commence pas par ./ ni /, on remplace par ./valeur

        # 1. Extraire tous les src=""
        $matches = [regex]::Matches($content, 'src="([^"]+)"')

        # On remplace chaque src trouvé qui ne commence pas par ./ ou /
        foreach ($match in $matches) {
            $srcVal = $match.Groups[1].Value
            if ($srcVal -notlike "./*" -and $srcVal -notlike "/*") {
                $ancien = "src=`"$srcVal`""
                $nouveau = "src=`"./$srcVal`""
                # Remplacer toutes occurrences dans le contenu
                $content = $content.Replace($ancien, $nouveau)
            }
        }

       
		# Écrire le contenu modifié dans index.html (UTF8)
        $content | Set-Content -Path $indexPath -Encoding UTF8
		if ($newContent -ne $content) {
            # Écrire la modification dans le fichier
            #$newContent | Set-Content -Path $htmlFile.FullName -Encoding UTF8
            Write-Host "Modifié : src script préfixé par ./"
        } else {
            Write-Host "Aucune modification nécessaire"
        }
		
        
    } else {
        Write-Warning "Fichier index.html non trouvé dans $safeFolderName"
    }
}
catch {
    Write-Error "Erreur lors du traitement dans $scriptDir : $_"
}
		Write-Host "Titre mis à jour dans $indexPath avec '$safeFolderName'."

		$newContent | Out-File -FilePath $scriptPath -Encoding UTF8
    }
	
    # Se positionner dans le dossier courant
    Set-Location $dossier.FullName

    # Exécuter la commande ou script ici (exemple : script_correction.ps1, node script.js, etc.)
    # Par exemple :
    # & "node" "script_correction.js"
    # ou si script PowerShell : 
    # & ".\script_correction.ps1"

    # Exemple fictif de commande (à remplacer)
    try {
        # Lancer votre commande ici. Exemple :
        # & powershell.exe -ExecutionPolicy Bypass -File .\scriptCorrection.ps1
		# & ".\script_correction.ps1"
		# Exécuter le script correction .ps1 dans le dossier courant
		# & powershell.exe -ExecutionPolicy Bypass -File (Join-Path $dossier.FullName "script_correction.ps1")
		# Invoke-Expression($newContent)
        Write-Host "Succès pour $($dossier.Name)" -ForegroundColor Green
    }
    catch {
        Write-Host "Erreur lors du traitement de $($dossier.Name) : $_" -ForegroundColor Red
    }

    # Pause : attendre que l'utilisateur appuie sur une touche pour continuer
    Write-Host "Appuyez sur une touche pour continuer..."
    # $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

    # Revenir au dossier racine pour le prochain tour
    Set-Location $racine
}

Write-Host "Traitement terminé." -ForegroundColor Yellow

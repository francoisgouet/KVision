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
		$scriptDir = Split-Path -Path $MyInvocation.MyCommand.Definition -Parent
		Write-Host "Création d'un nouveau script dans $(scriptDir)" -ForegroundColor Green
			
"@
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
        & powershell.exe -ExecutionPolicy Bypass -File .\scriptCorrection.ps1
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

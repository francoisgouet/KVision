function Get-FunctionRange {
    param(
        [string]$content,
        [string]$functionName
    )
    # Trouver l'index de début de la fonction
    $pattern = [regex]::Escape("function $functionName(")
    $match = [regex]::Match($content, $pattern)
    if (-not $match.Success) { return $null }

    $startIndex = $match.Index

    # Trouver début du corps (première accolade après la signature)
    $braceIndex = $content.IndexOf('{', $startIndex)
    if ($braceIndex -lt 0) { return $null }

    # Parcours des caractères pour trouver l'accolade fermante équilibrée
    $depth = 0
    for ($i = $braceIndex; $i -lt $content.Length; $i++) {
        if ($content[$i] -eq '{') { $depth++ }
        elseif ($content[$i] -eq '}') { 
            $depth--
            if ($depth -eq 0) { 
                # Fin de fonction trouvée
                return @{Start=$startIndex; Length=($i - $startIndex + 1)}
            }
        }
    }
    return $null
}

$clearContent = $false
$racine = "C:\Users\Utilisateur\Documents\Culture"
$exclusions = @("init", "server","Apprentissage") 

# Code JS à insérer (sans duplication)
<# body function v1
$newFunctionBody = @"
    const result = [];
	debugger;
    function traverse(node) {
        if (node.nodeType === Node.TEXT_NODE) {
            const text = node.textContent.trim();
            if (text.startsWith('*')) {
                result.push(text);
            }
        } else {
            node.childNodes.forEach(child => traverse(child));
        }
    }
    traverse(document.body);
    return result;
"@
 #>
 $newFunctionBody = @"
    const result = [];
	debugger;
    function traverse(node) {
        if (node.nodeType === Node.TEXT_NODE) {
            const text = node.textContent.trim();
            if (text.startsWith('*')) {
                 // Nettoyer le texte en enlevant l'*'
                    let cleanText = node.textContent.trim().slice(1).trim();
                    
                    // Vérifier si dossier existe ou créer (si on peut faire côté client, sinon côté server)
                    // Ici, on suppose qu'on donne un lien
                    let link = document.createElement('a');
                    link.textContent = cleanText;
                    link.href = './' + cleanText + '/index.html'; // exemple de chemin
                    
                    // remplacer le textNode par ce lien
                    if (node.parentNode) {
                        node.parentNode.replaceChild(link, node);
                    }
					result.push(cleanText);
            }
        } else {
            node.childNodes.forEach(child => traverse(child));
        }
    }
    traverse(document.body);
	// Exemple de tableau contenant les noms extraits de la fonction getTextsStartingWithStar modifiée
	const folderNames = ["Dossier1", "Dossier2", "AutreDossier"];

	// Simulation : envoyer ce JSON au serveur via fetch POST (nécessite serveur backend)
	fetch('/save-folders', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(folderNames)
	})
.then(response => response.json())
.then(data => console.log("Serveur a créé les dossiers :", data))
.catch(error => console.error("Erreur:", error));
    return result;
"@

$dossiers = Get-ChildItem -Path $racine -Directory | Where-Object { $exclusions -notcontains $_.Name }

foreach ($dossier in $dossiers) {
    $scriptJsPath = Join-Path $dossier.FullName "script.js"
	
	# effacer le contenu si c'est n'importe quoi
	if ($clearContent){
		Clear-Content -Path $scriptJsPath
		Write-Host "Contenu effacé dans $($scriptJsPath)"	
	}
	
	
    if (-Not (Test-Path $scriptJsPath)) {
        Write-Warning "script.js absent dans $($dossier.Name)"
        continue
    }

    $content = Get-Content -Path $scriptJsPath -Raw


    $funcRange = Get-FunctionRange -content $content -functionName "getTextsStartingWithStar"
	
	# Contenu JS libre à ajouter (ex : fonctions auxiliaires, variables globales...)
	$additionalJsContent = @"
	console.log('Contenu JS supplémentaire chargé.');
	// Ajouter ici d'autres fonctions ou variables globales
	getTextsStartingWithStar();
"@
	
	#
	#
    if ($funcRange -ne $null) {
        # Construire la nouvelle fonction complète
        $funcHeaderPattern = [regex]::Escape("function getTextsStartingWithStar()") + "\s*\([^)]*\)\s*{"
        $headerMatch = [regex]::Match($content.Substring($funcRange.Start, $funcRange.Length), $funcHeaderPattern)
        if ($headerMatch) {
            $funcHeader = "function getTextsStartingWithStar() {"
        }
        else {
            $funcHeader = "function getTextsStartingWithStar() {"
        }
        $newFunc = $funcHeader + "`r`n" + $newFunctionBody + "`r`n}"

        # Remplacer la fonction dans le contenu
        $newContent = $content.Remove($funcRange.Start, $funcRange.Length).Insert($funcRange.Start, $newFunc)

        Set-Content -Path $scriptJsPath -Value $newContent -Encoding UTF8
        #Write-Host "Fonction $($newFunc) modifiée dans $($dossier.Name)\script.js"
		Write-Host "Fonction modifiée dans $($dossier.Name)\script.js"
    }
    else {
        # Fonction non trouvée, ajouter à la fin
        $newFunc = "function getTextsStartingWithStar() {`r`n" + $newFunctionBody + "`r`n}"
        $newContent = $content + "`r`n" + $newFunc
        Set-Content -Path $scriptJsPath -Value $newContent -Encoding UTF8
        #Write-Host "Fonction $($newFunc) ajoutée dans $($dossier.Name)\script.js"
		Write-Host "Fonction $($newFunc) ajoutée dans $($dossier.Name)\script.js"
    }
	
	# Ajoute le contenu JS supplémentaire à la fin sans écraser
    Add-Content -Path $scriptJsPath -Value "`r`n$additionalJsContent" -Encoding UTF8
}
Write-Host "Traitement terminé."

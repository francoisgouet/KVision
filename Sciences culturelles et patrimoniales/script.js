
function getTextsStartingWithStar() {
    const result = [];
	debugger;
    function traverse(node) {
        if (node.nodeType === Node.TEXT_NODE) {
            const text = node.textContent.trim();
            if (text.startsWith('*')) {
                 // Nettoyer le texte en enlevant l'*'
                    let cleanText = node.textContent.trim().slice(1).trim();
                    
                    // VÃ©rifier si dossier existe ou crÃ©er (si on peut faire cÃ´tÃ© client, sinon cÃ´tÃ© server)
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
    return result;
}




	console.log('Contenu JS supplÃ©mentaire chargÃ©.');
	// Ajouter ici d'autres fonctions ou variables globales


	console.log('Contenu JS supplÃ©mentaire chargÃ©.');
	// Ajouter ici d'autres fonctions ou variables globales
	getTextsStartingWithStar();


	console.log('Contenu JS supplÃ©mentaire chargÃ©.');
	// Ajouter ici d'autres fonctions ou variables globales
	getTextsStartingWithStar();

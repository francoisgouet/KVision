import os

def creer_sous_dossier_si_absent(chemin, sous_dossier):
    chemin_complet = os.path.join(chemin, sous_dossier)
    if not os.path.exists(chemin_complet):
        os.makedirs(chemin_complet)
    return chemin_complet

def lister_contenu_dossier(chemin_a_explorer):
    contenu = {
        "dossier": chemin_a_explorer,
        "sous_dossiers": [],
        "fichiers": []
    }
    dossiers = []
    fichiers = []
    for entry in os.listdir(chemin_a_explorer):
        full_path = os.path.join(chemin_a_explorer, entry)
        if os.path.isdir(full_path):
            contenu["sous_dossiers"].append(entry)
        else:
            contenu["fichiers"].append(entry)
    return dossiers, fichiers

def creer_structure_chemin(path):
    
    print(f"GESTION_FICHIERS request for path: {path}")
    
    # Chemin complet absolu basé sur le dossier de base
    base_dir = os.getcwd()
    print(f"GESTION_FICHIERS base_dir : {base_dir}")
    
    #Toujours nettoyer path avec lstrip des séparateurs 
    #pour s’assurer que c’est un chemin relatif 
    chemin_rel = path.lstrip("\\/")  # Enlève les slashs au début ( evite les probleme de sperateur (/ ou \)
    chemin_absolu = os.path.join(base_dir, chemin_rel)
    print(f"GESTION_FICHIERS absolute path: {chemin_absolu}")
    
    #normaliser le chemin :
    chemin_normalise = os.path.normpath(chemin_absolu)
    print(f"GESTION_FICHIERS normal path: {chemin_normalise}")# # Crée l’arborescence complète (y compris dossiers parents manquants)
    if not os.path.exists(chemin_normalise):
        os.makedirs(chemin_normalise)

    # # Vérifie si index.html existe, sinon créer un index minimal
    index_file = os.path.join(chemin_absolu, "index.html")
    if not os.path.exists(index_file):
        with open(index_file, "w", encoding="utf-8") as f:
            f.write(f"<html><body><h1>Page par défaut pour {path}</h1><a href='javascript:history.back()'>retour</a></body></html>")

    return chemin_normalise
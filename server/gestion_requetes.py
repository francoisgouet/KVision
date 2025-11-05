from http.server import BaseHTTPRequestHandler, HTTPServer
import os 
import sys
import json
import subprocess
import mimetypes
from urllib.parse import unquote
from HTML.utils import _send_json,_send_response,_send_html
from gestion_fichiers import creer_structure_chemin

class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):
    # Indiquer ici le répertoire parent absolu à servir
    base_directory = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
        
    def translate_path(self, path):
        """Convertir le chemin HTTP en chemin fichier basé sur base_directory"""
        # Normaliser le chemin reçu
        path = path.split('?',1)[0].split('#',1)[0]
        path = os.path.normpath(path.lstrip('/'))
        # Construire le chemin absolu basé sur base_directory
        full_path = os.path.join(self.base_directory, path)
        print(full_path)
        return full_path

    def do_GET(self):
        # Chemin demandé via URL, ex: /dossier1/dossier2
        chemin_url = unquote(self.path)  
        # On part du répertoire courant où le serveur est lancé
        repertoire_courant = os.getcwd()

        # Si URL est "/" on reste au répertoire courant
        if chemin_url == "/":
            chemin_a_explorer = repertoire_courant
        else:
            # Combine le répertoire courant + chemin relatif demandé, sans partir de /
            chemin_a_explorer = os.path.join(repertoire_courant, chemin_url.lstrip("/"))
            
        #sous_projet = "mon_sous_projet"  # exemple : le nom du sous dossier attendu
        #sous_dossier_path = os.path.join(dossier_a_explorer, sous_projet)
        #if not os.path.exists(sous_dossier_path):
        #   os.makedirs(sous_dossier_path)
        
        # Extraire dossier contenant le fichier demandé
        dossier_parent = os.path.dirname(chemin_url)

        # Création automatique de la structure manquante
        creer_structure_chemin(dossier_parent)

        #chemin_a_ouvrir = os.path.join(repertoire_courant, chemin_url)
        
        print(f"GET request for path: {chemin_a_explorer}")    
        
        if os.path.exists(chemin_a_explorer):
            if os.path.isdir(chemin_a_explorer):
                index_path = os.path.join(chemin_a_explorer, "index.html")
                contenu = {
                    "dossier": chemin_a_explorer,
                    "sous_dossiers": [],
                    "fichiers": []
                }
                for entry in os.listdir(chemin_a_explorer):
                    full_path = os.path.join(chemin_a_explorer, entry)
                    if os.path.isdir(full_path):
                        contenu["sous_dossiers"].append(entry)
                    else:
                        contenu["fichiers"].append(entry)
                
                if os.path.exists(index_path):
                    with open(index_path, "r", encoding="utf-8") as f:
                        contenu_html = f.read()
                    #self._send_html(contenu_html)
                    _send_html(self,contenu_html)
                else:   
                    #self._send_json(contenu)
                    _send_json(self,contenu)
            else:
                # Si fichier, servir le fichier directement avec le bon type mime
                mime_type, _ = mimetypes.guess_type(chemin_a_explorer)
                mime_type = mime_type or 'application/octet-stream'
                with open(chemin_a_explorer, 'rb') as f:
                    data = f.read()
                _send_response(self,data,mime_type)
        else:
            self.send_response(404)
            self.end_headers()

            
    def do_POST(self):
        print(f"POST request for path: {self.path}")

        if self.path == '/api/create-folders':
            content_length = int(self.headers.get('Content-Length', 0))
            post_data = self.rfile.read(content_length)
            try:
                folder_names = json.loads(post_data)

                json_path = os.path.join(self.base_directory, 'folders.json')
                with open(json_path, 'w') as f:
                    json.dump(folder_names, f)

                # Modifier le chemin du script PowerShell si besoin
                ps_script_path = os.path.join(self.base_directory, 'createFolders.ps1')
                completed = subprocess.run(
                    ["powershell.exe", "-File", ps_script_path, "-JsonFilePath", json_path],
                    capture_output=True,
                    text=True
                )

                if completed.returncode == 0:
                    self.send_response(200)
                    self.send_header('Content-type', 'application/json')
                    self.end_headers()
                    response = {'status': 'success', 'message': completed.stdout}
                    self.wfile.write(json.dumps(response).encode())
                else:
                    self.send_response(500)
                    self.send_header('Content-type', 'application/json')
                    self.end_headers()
                    response = {'status': 'error', 'message': completed.stderr}
                    self.wfile.write(json.dumps(response).encode())
            except Exception as e:
                self.send_response(400)
                self.end_headers()
                self.wfile.write(f'Erreur lors du traitement POST: {str(e)}'.encode('utf-8'))
        else:
            self.send_response(404)
            self.end_headers()
            self.wfile.write(b'Endpoint inconnu.')

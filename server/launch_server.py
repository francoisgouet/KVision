from http.server import HTTPServer
from gestion_requetes import SimpleHTTPRequestHandler
import os 

print(f"Serveur lancé depuis le répertoire : {os.getcwd()}")
        
def run(server_class=HTTPServer, handler_class=SimpleHTTPRequestHandler, port=8000):
    #os.chdir(os.path.dirname(os.path.abspath(__file__)))
    
    #se deplacer vers rep parent
    os.chdir(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..'))

    # Chemin absolu du script Python
    #script_dir = os.path.dirname(os.path.abspath(sys.argv[0]))

    # Positionne le dossier courant sur celui du script
    # os.chdir(script_dir)
    
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    # print(f"Serving from {handler_class.directory} on port {port}")
    print(f"Serving from {os.getcwd()} on port {port}")
    httpd.serve_forever()

if __name__ == '__main__':
    run()
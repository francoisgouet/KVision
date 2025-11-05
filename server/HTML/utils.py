def _send_response(self, content, content_type='text/html'):
    self.send_response(200)
    self.send_header('Content-type', content_type + '; charset=utf-8')
    self.end_headers()
    self.wfile.write(content if isinstance(content, bytes) else content.encode('utf-8'))

def _send_json(self, data):
    self.send_response(200)
    self.send_header('Content-type', 'application/json')
    self.end_headers()
    self.wfile.write(json.dumps(data).encode())

def _send_html(self, html):
    self.send_response(200)
    self.send_header('Content-type', 'text/html; charset=utf-8')
    self.end_headers()
    self.wfile.write(html.encode('utf-8'))
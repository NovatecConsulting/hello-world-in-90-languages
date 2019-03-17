#!/usr/bin/env python3

'''
Simple and functional hello world REST server for Python 3 using no dependencies beyond the Python standard library.

"curl -X POST http://localhost:8080/say-hello?name=Adam"
"curl -X POST http://localhost:8080/say-hello?name=Eva"
'''

from urllib.parse import urlparse
from http.server import BaseHTTPRequestHandler,HTTPServer

PORT_NUMBER = 8080

class myHandler(BaseHTTPRequestHandler):

	def do_POST(self):
		request_param = urlparse(self.path).query.split('=')[1]
		message = bytes('{"message":"Hello ' + request_param + '!"}', 'UTF-8')
		self.protocol_version = 'HTTP/1.1'
		self.send_response(200)
		self.send_header('Content-Type','application/json;charset=UTF-8')
		self.send_header('Content-Length',len(message))
		self.end_headers()
		self.wfile.write(message)
		return
try:
	server = HTTPServer(('', PORT_NUMBER), myHandler)
	print('Started http server on port %s' % PORT_NUMBER)
	
	server.serve_forever()

except KeyboardInterrupt:
	print('Shutting down the web server')
	server.socket.close()

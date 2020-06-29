var express = require('express'),
	http = require('http'),
	app = express(),     
	server = http.createServer(app);

app.get('/hello', function(req, res) {
	res.sendfile('hello.html', {root : __dirname }) ;
});

server.listen(8000, function() {
	console.log('express server listening on port ' + server.address().port)
});
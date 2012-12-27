/*
 * Esta versão utiliza o framework Express para NodeJS:
 * npm install express
 */
var app = require('express')();
var server = require('http').createServer(app);
var io = require('socket.io').listen(server);

server.listen(80); // ATENÇÃO: precisa ser root/sudoer para usar porta < 1024

app.get('/', function (req, res) {
	res.sendfile(__dirname + "/index.html");
});

io.sockets.on('connection', function (socket) {
	socket.emit('news', {hello: 'world'});
	socket.on('my other event', function (data) {
		console.log(data);
	});
});

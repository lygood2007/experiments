var app = require('http').createServer(handler);
var io = require('socket.io').listen(app);
var fs = require('fs');

// ATENÇÃO, que sofri com isso: usuários comuns (não root) não podem usar porta menor que a 1024. Por isso, para rodar esse server é preciso fazê-lo usando sudo.
app.listen(80);

function handler (req, res) {
  fs.readFile(__dirname + '/index.html',
  function (err, data) {
    if (err) {
      res.writeHead(500);
      return res.end('Error loading index.html');
    }

    res.writeHead(200, {'content-type': 'text/html'});
    res.end(data);
  });
}

io.sockets.on('connection', function (socket) {
  socket.emit('news', { hello: 'world' });
  socket.on('my other event', function (data) {
    console.log(data);
  });
});

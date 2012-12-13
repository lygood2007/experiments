var formidable = require("formidable");
var http = require("http");
var sys = require("sys");

http.createServer(function(request, response) {
	if (request.url == "/upload" && request.method.toLowerCase() == "post") {
	var form = new formidable.IncomingForm();
	form.parse(request, function (error, fields, files) {
		response.writeHead(200, {"Content-Type": "text/plain"});
		response.write("Receive upload:\n\n");
		response.end(sys.inspect({fields: fields, files:files}));
	});

	return;
}

	response.writeHead(200, {"Contet-Type": "text/html"});
	response.end(
		'<form action="/upload" enctype="multipart/form-data" method="post">' +
		'<input type="text" name="title"><br>' +
		'<input type="file" name="upload" multiple="multiple"><br>' +
		'<input type="submit" value="Upload">' +
		'</form>'
	);

}).listen(8888);

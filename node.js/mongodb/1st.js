(function () {

	const DB_NAME = "test";
	const DB_HOST = "127.0.0.1";
	const DB_PORT = 27017;
	const DB_COLLECTION = "test_insert";

	var Db = require("mongodb").Db;
	var Server = require("mongodb").Server;

	var insertData = function (err, collection) {
		collection.insert({name: "Ivan Ramos Pagnossin"});
		collection.insert({name: "Fabiana Aparecida de Farias"});
		collection.insert({name: "Giovani Cagnotto Pagnossin"});
	}

	var removeData = function (err, collection) {
		collection.remove({name: "Giovani Cagnotto Pagnossin"});
	}

	var updateData = function (err, collection) {
		collection.update({name: "Fabiana Aparecida de Farias"}, {name: "Fabiana Cagnotto de Farias"});
	}

	var listAll = function (err, collection) {
		collection.find().toArray(function(error, results) {
			console.log(results);
		});
	}

	var removeAll = function (err, collection) {
		collection.remove({name: "Ivan Ramos Pagnossin"});
		collection.remove({name: "Fabiana Aparecida de Farias"});
		collection.remove({name: "Fabiana Cagnotto de Farias"});
		collection.remove({name: "Giovani Cagnotto Pagnossin"});
	}

	var client = new Db(DB_NAME, new Server(DB_HOST, DB_PORT, {safe:true}));
	client.open(function (err, pClient) {
		client.collection(DB_COLLECTION, insertData);
		client.collection(DB_COLLECTION, removeData);
		client.collection(DB_COLLECTION, updateData);
		client.collection(DB_COLLECTION, listAll);
		//client.collection(DB_COLLECTION, removeAll);
	});

}());

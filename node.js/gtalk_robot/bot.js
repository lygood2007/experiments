const config = require("./config.js").settings;
const xmpp = require("node-xmpp");
const request_helper = require("request");
const util = require("util");

var commands = {};

const conn = new xmpp.Client(config.client);
conn.socket.setTimeout(0);
conn.socket.setKeepAlive(true, 10000);
conn.on("online", function () {
	set_status_message(config.status_message, conn);
});


if (config.allow_auto_subscribe) {
	conn.addListener("online", function () {
		request_google_roster(conn);
	});
	conn.addListener("stanza", function (stanza) {
		accept_subscription_requests(stanza, conn);
	});
}

// Registra a presença do cliente, disponibilizando-o para chat
function set_status_message (message, connection) {
	var presence_element = new xmpp.Element("presence", {});
	presence_element.c("show").t("chat");
	presence_element.c("status").t(message);
	console.log(presence_element.toString());
	connection.send(presence_element);
}

function request_google_roster (connection) {

	var roster_element = new xmpp.Element("iq", {
		from: connection.jid,
		type: "get",
		id: "google-roster"
	});

	roster_element.c("query", {
		xmlns: "jabber:iq:roster",
		"xmlns:gr": "google-roster",
		"gr:ext": "2"
	});

	connection.send(roster_element);	
}

function accept_subscription_requests (stanza, connection) {
	if (stanza.is("presence") && stanza.attrs.type == "subscribe") {
		var subscribe_element = new xmpp.Element("presence", {
			to: stanza.attrs.from,
			type: "subscribed"
		});

		connection.send(subscribe_element);
	}
}

function add_command (command, callback) {
	commands[command] = callback;
}

function execute_command (request) {
	if (typeof commands[request.command] === "function") {
		return commands[request.command](request);
	}
	
	return false;
}

function message_dispatcher (stanza) {
	if ('error' === stanza.attrs.type) {
		util.log('[error]' + stanza.toString());
	}
	else if (stanza.is('message')) {
		util.log('[message] RECV:' + stanza.toString());
		var request = split_request(stanza);
		if (request) {
			if (!execute_command(request)) {
				send_unknown_command_message(request);
			}
		}
	}
}

function split_request (stanza) {
	var message_body = stanza.getChildText('body');
	if (message_body != null) {
		message_body = message_body.split(config.command_argument_separator);
		var command = message_body[0].trim().toLowerCase();
		if (command === 'help' || command === '?') {
			send_help_information(stanza.attrs.from);
		}
		else if (typeof message_body[1] !== "undefined") {
			return {"command": command, "argument": message_body[1].trim(), "stanza": stanza};
		}
	}

	return false;
}

function send_help_information (to_jid) {
	var message_body = "Currently bounce (b), twitter (t) and status (s) are supported.\n";
	message_body += "b;example text\n";
	message_body += "t;some search string\n";
	message_body += "s;A new status message\n";
	send_message(to_jid, message_body);
}

function send_unknown_command_message (request) {
	send_message(request.stanza.attrs.from, 'Unknown command: "' + request.command + '". Send "help" for more information.');
}

function send_message(to_jid, message_body) {
	var elem = new xmpp.Element('message', {to: to_jid, type: 'chat'});
	elem.c('body').t(message_body);
	conn.send(elem);
	util.log('[message] SENT: ' + elem.up().toString());
}



// Isto deveria estar numa função própria...
add_command('s', function (request) {
	set_status_message(request.argument, conn);
	return true;
});

add_command('b', function (request) {
	send_message(request.stanza.attrs.from, request.stanza.getChildText('body'));
	return true;
});

conn.addListener('stanza', message_dispatcher);
conn.on('error', function (stanza) {
	util.log('[error] ' + stanza.toSpring());
});

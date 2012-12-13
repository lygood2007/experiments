const config = require("./config.js").settings;
const xmpp = require("node-xmpp");
const request_helper = require("request");
const util = require("util");

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

from google.appengine.ext import db

class WikiDB (db.Model):
	title = db.StringProperty(required = True)
	content = db.TextProperty(required = True)
	version = db.IntegerProperty(required = True)
	timestamp = db.DateTimeProperty(auto_now_add = True)
	username = db.StringProperty(required = True)
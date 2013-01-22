from utils import *
from google.appengine.ext import db

def users_key(group = 'default'):
    return db.Key.from_path('users', group)


# Users database
class User (db.Model):
	username = db.StringProperty(required = True)
	password = db.StringProperty(required = True)
	email = db.EmailProperty(required = False)

	@classmethod
	def by_id(cls, uid):
		return User.get_by_id(uid, parent = users_key())

	@classmethod
	def by_name(cls, name):
		u = User.all().filter('username =', name).get()
		return u

	@classmethod
	def register(cls, name, password, email = None):
		return User(parent = users_key(),
					username = name,
					password = encrypt_password(name, password),
					email = email)

	# TODO: ESTE NOME NAO ESTA LEGAL PQ EH IGUAL AQUELE NO HANDLERS.WIKIHANDLER... CONFUNDE!!!
	@classmethod
	def login(cls, name, password):
		user = cls.by_name(name)
		if user and valid_password(name, password, user.password):
			return user
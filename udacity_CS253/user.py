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
        user = User.all().filter('username =', name).get()
        return user
    
    @classmethod
    def register(cls, name, password, email = None):
        user = cls.by_name(name)
        if not user:
            return User(parent = users_key(),
                        username = name,
                        password = encrypt_password(name, password),
                        email = email)
    
    # TODO: ESTE NOME NAO ESTA LEGAL PQ EH IGUAL AQUELE NO HANDLERS.WIKIHANDLER... CONFUNDE!!!
    @classmethod
    def login(cls, name, password):
        user = cls.by_name(name)
        if user and check_password(name, password, user.password):
            return user
import hmac
import re
from google.appengine.ext import db
from google.appengine.api import memcache
import json
import time
import random
import string
import hashlib
import webapp2
from utils import *
import logging
from user import *

TEMPLATE_SIGNUP = "wiki_signup.html"

PATH_WIKI = "/wiki"
PATH_SIGNUP = "/wiki/signup"
PATH_LOGIN = "/wiki/login"
PATH_LOGOUT = "/wiki/logout"

FORMAT = '%(asctime)-15s %(clientip)s %(user)-8s %(message)s'
logging.basicConfig(format=FORMAT)
logger = logging.getLogger('wiki_handlers')

	


class WikiHandler(webapp2.RequestHandler):

	UID_COOKIE_NAME = 'user_id'

	def write(self, *a, **kw):
		self.response.out.write(*a, **kw)

	def render_str(self, template, **params):
		params['user'] = self.user
		return render_str(template, **params)

	def render(self, template, **kw):
		self.write(self.render_str(template, **kw))

	# TODO: implementar o expires (associado ao check box "remember me")
	def set_secure_cookie(self, name, val, expires = None):
		cookie_val = make_secure_val(val)
		self.response.headers.add_header('Set-Cookie', '%s=%s; Path=/' % (name, cookie_val))

	def get_secure_cookie(self, name):
		cookie_val = self.request.cookies.get(name)
		return cookie_val and check_secure_val(cookie_val)
	
	def login(self, user):
		self.set_secure_cookie(self.UID_COOKIE_NAME, str(self.user.key().id()))

	def logout(self):
		self.response.headers.add_header('Set-Cookie', '%s=; Path=/' % self.UID_COOKIE_NAME)

	def initialize(self, *a, **kw):
		webapp2.RequestHandler.initialize(self, *a, **kw)
		uid = self.get_secure_cookie(self.UID_COOKIE_NAME)
		self.user = uid and User.by_id(int(uid))
		
# Signup page
class Signup (WikiHandler):

	def get(self):
		self.render(TEMPLATE_SIGNUP)

	def post(self):
		have_error = False
		username = self.request.get('username')
		password = self.request.get('password')
		verify = self.request.get('verify')
		email = self.request.get('email')

		params = dict(username = username, email = email)

		if not valid_username(username):
			params['error_username'] = "That's not a valid username."
			have_error = True

		if not valid_password(password):
			params['error_password'] = "That wasn't a valid password."
			have_error = True
		elif password != verify:
			params['error_verify'] = "Your passwords didn't match."
			have_error = True

		if not valid_email(email):
			params['error_email'] = "That's not a valid email."
			have_error = True

		if have_error:
			self.render(TEMPLATE_SIGNUP, **params)
		else:
			#encrypted_password = encrypt_password(username, password)
			#user = User(username = username, password = encrypted_password, email = email).put()			
			#self.set_secure_cookie(self.UID_COOKIE_NAME, str(user.id()))
			
			user = User.register(username, password, email if email else None).put()
			self.login(user)
			self.redirect(PATH_WIKI)

# Welcome page
class Welcome (WikiHandler):
	def get(self):

		#user_id = self.get_secure_cookie(self.UID_COOKIE_NAME)

		#if user_id:
		if self.user:
			#user = User.get_by_id(int(user_id))
			self.response.out.write("Welcome, %s!" % user.username)
		else:
			self.redirect(PATH_SIGNUP)

			

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
from user import *
from wikipage import *
import logging
from time import gmtime, strftime

TEMPLATE_SIGNUP = "wiki-signup.html"
TEMPLATE_LOGIN = "wiki-login.html"
TEMPLATE_NEWPAGE = "page-edit.html"
TEMPLATE_WIKIPAGE = "page-view.html"
TEMPLATE_HISTORY = "page-history.html"
PATH_WIKIROOT = "/wiki"
PATH_SIGNUP = "/wiki/signup"
PATH_LOGIN = "/wiki/login"
PATH_LOGOUT = "/wiki/logout"
PATH_EDIT = "/wiki/_edit"

# Base handler for this wiki.
class WikiHandler(webapp2.RequestHandler):

	UID_COOKIE_NAME = 'user_id'

	def write(self, *a, **kw):
		self.response.out.write(*a, **kw)

	def render_str(self, template, **params):
		params['user'] = self.user
		return render_str(template, **params) # obs.: utils::render_str

	def render(self, template, **kw):
		self.write(self.render_str(template, **kw))

	# TODO: implementar o expires (associado ao check box "remember me")
	def set_secure_cookie(self, name, val, expires = None):
		cookie_val = make_secure_val(val)
		if expires:
			self.response.headers.add_header('Set-Cookie', '%s=%s; Expires=%s; Path=/' % (name, cookie_val, expires))
		else:
			self.response.headers.add_header('Set-Cookie', '%s=%s; Path=/' % (name, cookie_val))

	def get_secure_cookie(self, name):
		cookie_val = self.request.cookies.get(name)
		return cookie_val and check_secure_val(cookie_val)
	
	def login(self, user, expires = False):		
		if expires:
			expires_at = str(strftime("%a, %d-%b-%Y %H:%M:%S GMT", gmtime(time.time() + 24 * 60 * 60))) # Now + one day. TODO: how to do arithmetic with date/time?
		else:
			expires_at = ""
				
		self.set_secure_cookie(self.UID_COOKIE_NAME, str(user.key().id()), expires_at)

	def logout(self):
		self.response.headers.add_header('Set-Cookie', '%s=; Path=/' % self.UID_COOKIE_NAME)

	def initialize(self, *a, **kw):
		webapp2.RequestHandler.initialize(self, *a, **kw)
		uid = self.get_secure_cookie(self.UID_COOKIE_NAME)
		self.user = uid and User.by_id(int(uid))
		
	def redirect_with_return(self, uri):
		self.redirect(uri + "?redirect=%s" % self.request.path)
		
# Signup a new user
class Signup (WikiHandler):

	def get(self):
		self.render(TEMPLATE_SIGNUP)

	def post(self):
		have_error = False
		username = self.request.get('username')
		password = self.request.get('password')
		verify = self.request.get('verify')
		email = self.request.get('email')
		redirect = self.request.get('redirect')
		remember_me = self.request.get('remember_me')

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
			user = User.register(username, password, email if email else None)
			if user:
				user.put()
				self.login(user, remember_me)
				self.redirect(redirect if redirect else PATH_WIKIROOT)
			else:
				params['error_username'] = "This user already exists."
				self.render(TEMPLATE_SIGNUP, **params)
			
# Handles login action
class Login (WikiHandler):
	def get(self):
		redirect = self.request.get('redirect')
		self.render(TEMPLATE_LOGIN, redirect = redirect)
		
	def post(self):
		username = self.request.get('username')
		password = self.request.get('password')
		redirect = self.request.get('redirect')
		remember_me = self.request.get('remember_me')

		logging.debug("redirect = %s" % redirect)

		user = User.login(username, password)
		if user:
			self.login(user, remember_me)
			self.redirect(redirect if redirect else PATH_WIKIROOT)
		else:
			params['error_login'] = 'Invalid login'
			self.render(TEMPLATE_LOGIN, **params)
	
# Handles logout action
class Logout (WikiHandler):
	def get(self):
		
		redirect = self.request.get("redirect")
		
		self.logout()
		self.redirect(redirect if redirect else PATH_WIKIROOT)

# View of an existing wikipage. If a nonexisting page is requested, it redirects to the handle responsible for creation
class PageView (WikiHandler):
	def get(self, page_name = ""):
		
		version = self.request.get("version")
		page = Page.by_name(page_name, version)
		
		if page:
			self.render(TEMPLATE_WIKIPAGE, page = page)
		else:
			self.redirect(PATH_EDIT + "/" + page_name)

# Edition of an existing wikipage or creation of a new one
class PageEdit (WikiHandler):
	def get(self, page_name = ""):
		
		if self.user:
			version = self.request.get("version")
			page = Page.by_name(page_name, version if version else None)
			content = page.content if page else "<h1>%s</h1>" % page_name
			self.render(TEMPLATE_NEWPAGE, title = page_name, content = content)
		else:
			self.redirect_with_return(PATH_LOGIN)
		
	def post(self, page_name = ""):
		
		content = self.request.get("content")
		
		if content:
			page = Page.register(page_name, content, self.user.key().id())			
			self.redirect(PATH_WIKIROOT + "/" + page_name)

# View of the last 10 versions (editions) of a given wikipage
class PageHistory (WikiHandler):
	def get(self, page_name):
		pages = Page.all().filter("title = ", page_name).order("-timestamp").fetch(10)
		
		for page in pages:
			page.author = User.by_id(page.uid).username
		
		self.render(TEMPLATE_HISTORY, pages = pages)

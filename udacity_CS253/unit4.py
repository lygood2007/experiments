import jinja2
import os
import webapp2
import unit3
import hmac
import re

from google.appengine.ext import db

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir), autoescape = True)

class User (db.Model):
	username = db.StringProperty(required = True)
	password = db.StringProperty(required = True)
	email = db.EmailProperty(required = False)
	created = db.DateTimeProperty(auto_now_add = True)

class Unit4 (unit3.Handler):
	def get(self):
		self.response.headers["Content-Type"] = "text/plain"
		
		visits = 0
		visits_cookie_str = self.request.cookies.get("visits")
		
		if visits_cookie_str:
			cookie_val = check_secure_val(visits_cookie_str)
			if cookie_val:
				visits = int(cookie_val)
		
		visits += 1
		
		visits_cookie_str = make_secure_val(str(visits))
		self.response.headers.add_header("Set-Cookie", "visits=%s" % visits_cookie_str)
		
		if visits > 10:
			self.write("You are the best ever!")
		else:
			self.write("You've been here %s times!" % visits)
			
class Signup (unit3.Handler):
	def get(self):
		self.render("signup.html")
	
	def post(self):
		username = self.request.get("username")
		password = self.request.get("password")
		verify   = self.request.get("verify")
		email    = self.request.get("email")

		valid_username  = USERNAME_RE.match(username)
		valid_password  = PASSWORD_RE.match(password) and (password == verify)
		valid_email     = email == "" or EMAIL_RE.match(email)
		username_exists = valid_username and username == get_user(self)
		
		username_error = "This user already exists" if username_exists else "That is not a valid username" if not valid_username else ""		
		password_error = "You must set a password" if not (password or verify) else ""
		verify_error   = "Your passwords do not match" if not valid_password and password else ""
		email_error    = "That is not a valid email" if not valid_email else ""
			
		if username_exists or not (valid_username and valid_password and valid_email):
			self.render("signup.html", username = username,
									   email = email,
									   username_error = username_error,
									   password_error = password_error,
									   verify_error = verify_error,
									   email_error = email_error)
		else:
			encrypted_password = hash_str(password)
			if email:
				user = User(username = username, password = encrypted_password, email = email) 
			else:
				user = User(username = username, password = encrypted_password) 
			rc = user.put()
			
			set_user(self, str(rc.id()))
			
			self.redirect("/homework4/welcome")
	
class Welcome (unit3.Handler):
	def get(self):
	
		user_id = get_user(self)
		
		if user_id:
			entry = User.get_by_id(int(user_id))#db.GqlQuery("select * from User where id = %s" % user_id)
			username = entry.username
			self.response.out.write("Welcome, %s!" % username)
		else:
			self.redirect("/homework4/signup")
	

class Login (unit3.Handler):
	def get(self):
		self.render("login.html")
		
	def post(self):
		client_username = self.request.get("username")
		client_password = hash_str(self.request.get("password"))
		
		entries = db.GqlQuery("select * from User where username = '%(un)s' and password = '%(pwd)s'" % {"un":client_username, "pwd":client_password})
		if entries.count() > 0:
			set_user(self, str(entries[0].key().id()))
			self.redirect("/homework4/welcome")
		else:
			self.render("login.html", username = client_username, error = "Invalid login!")
			
class Logout (unit3.Handler):
	def get(self):
		self.response.headers.add_header("Set-Cookie", "user=; Path=/")
		self.redirect("/homework4/signup")
	
USERNAME_RE = re.compile(r"^[a-zA-Z0-9_-]{3,20}$")
PASSWORD_RE = re.compile(r"^.{3,20}$")
EMAIL_RE    = re.compile(r"^[\S]+@[\S]+\.[\S]+$")
SECRET = "harcoded_string"
	
def set_user (self, user):
	crypto = make_secure_val(user)
	self.response.headers.add_header("Set-Cookie", "user=%s; Path=/" % str(crypto))
	
def get_user (self):
	crypto = self.request.cookies.get("user")
	if crypto:
		return check_secure_val(crypto)
	else:
		return None
	
def hash_str(s):
	return hmac.new(SECRET, s).hexdigest()

def make_secure_val(s):
    return "%s|%s" % (s, hash_str(s))

def check_secure_val(h):
	value = h.split("|")[0]
	if h == make_secure_val(value):
		return value
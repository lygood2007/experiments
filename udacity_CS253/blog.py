import hmac
import re
from base_handler import BaseHandler
from google.appengine.ext import db
import json

USERNAME_RE = re.compile(r"^[a-zA-Z0-9_-]{3,20}$") # RE para verificar username
PASSWORD_RE = re.compile(r"^.{3,20}$") # RE para verificar password
EMAIL_RE    = re.compile(r"^[\S]+@[\S]+\.[\S]+$") # RE para verificar e-mail

SECRET = "harcoded_string"

# Users database
class UserDB (db.Model):
	username = db.StringProperty(required = True)
	password = db.StringProperty(required = True)
	email = db.EmailProperty(required = False)
	created = db.DateTimeProperty(auto_now_add = True)

# Blog database
class BlogDB (db.Model):
	subject = db.StringProperty(required = True)
	content = db.TextProperty(required = True)
	created = db.DateTimeProperty(auto_now_add = True)

# Signup page
class Signup (BaseHandler):
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
				user = UserDB(username = username, password = encrypted_password, email = email) 
			else:
				user = UserDB(username = username, password = encrypted_password) 
			rc = user.put()
			
			set_user(self, str(rc.id()))
			
			self.redirect("/blog/welcome")

# Welcome page
class Welcome (BaseHandler):
	def get(self):
	
		user_id = get_user(self)
		
		if user_id:
			entry = UserDB.get_by_id(int(user_id))#db.GqlQuery("select * from User where id = %s" % user_id)
			username = entry.username
			self.response.out.write("Welcome, %s!" % username)
		else:
			self.redirect("/blog/signup")

# Login page
class Login (BaseHandler):
	def get(self):
		self.render("login.html")
		
	def post(self):
		client_username = self.request.get("username")
		client_password = hash_str(self.request.get("password"))
		
		entries = db.GqlQuery("select * from User where username = '%(un)s' and password = '%(pwd)s'" % {"un":client_username, "pwd":client_password})
		if entries.count() > 0:
			set_user(self, str(entries[0].key().id()))
			self.redirect("/blog/welcome")
		else:
			self.render("login.html", username = client_username, error = "Invalid login!")

# Logout page
class Logout (BaseHandler):
	def get(self):
		self.response.headers.add_header("Set-Cookie", "user=; Path=/")
		self.redirect("/blog/signup")

# Blog's front page
class BlogFront (BaseHandler):
    def get(self):
		entries = db.GqlQuery("select * from BlogDB order by created desc")
		self.render("front.html", entries = entries)

# Returns the JSON of blog index
class BlogJSON (BaseHandler):
	def get(self):
		entries = db.GqlQuery("select * from BlogDB order by created desc")
		
		entries_obj = []
		
		for entry in entries:
			entries_obj.append({"subject":entry.subject, "content":entry.content})
		
		entries_json = json.dumps(entries_obj)
		
		self.response.headers['Content-Type'] = 'application/json'
		self.response.out.write(entries_json)
		pass
		
# New blog post page
class NewPost (BaseHandler):
	def render_form (self, error = "", subject="", content=""):
		self.render("newpost.html", error = error, subject = subject, content = content)
		
	def get(self):
		self.render_form()

	def post(self):
		subject = self.request.get("subject")
		content = self.request.get("content")
		
		# Verifica se subject/content 
		if subject and content:
			post = BlogDB(subject = subject, content = content)
			rc = post.put()
			self.redirect("/blog/%d" % int(rc.id()))
		else:
			error = "I need both subject and content."
			self.render_form(error, subject, content)

# Permalink (blog) page
class Permalink (BaseHandler):
	def get(self, blog_id):
		entries = [BlogDB.get_by_id(int(blog_id))]		

		if entries:
			self.render("front.html", entries = entries)
		else:
			self.request.out.write("This is not a valid post!")
	
# Returns the JSON of a blog post (permalink)
class PermalinkJSON (BaseHandler):
	def get(self, blog_id):
	
		entry = BlogDB.get_by_id(int(blog_id))		
		entry_json = json.dumps({"subject":entry.subject, "content":entry.content})
		
		self.response.headers['Content-Type'] = 'application/json'
		self.response.out.write(entry_json)
		pass
		
		
###### Useful functions ######

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
			
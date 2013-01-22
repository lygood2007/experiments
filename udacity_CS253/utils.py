import hmac
import re
import random
import string
import hashlib
import os
from string import letters

import jinja2

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir),
                               autoescape = True)


def render_str(template, **params):
    t = jinja_env.get_template(template)
    return t.render(params)

USER_RE = re.compile(r"^[a-zA-Z0-9_-]{3,20}$")
def valid_username(username):
    return username and USER_RE.match(username)

PASSWORD_RE = re.compile(r"^.{3,20}$")
def valid_password(password):
    return password and PASSWORD_RE.match(password)

EMAIL_RE  = re.compile(r'^[\S]+@[\S]+\.[\S]+$')
def valid_email(email):
    return not email or EMAIL_RE.match(email)


def set_user (self, user, expires = ""):
	crypto = str(make_secure_val(user))
	if expires:
		cookie_val = "user=%s; Path=/; expires=%s" % crypto, expires
	else:
		cookie_val = "user=%s; Path=/" % crypto

	self.response.headers.add_header("Set-Cookie", cookie_val)
	
def get_user (self):
	cookie_val = self.request.cookies.get("user")
	return check_secure_val(cookie_val)
	
def hash_str(s):
	SECRET = "_THIS_HARDCODED_STRING_MUST_BE_KEPT_SECRET_WITHIN_THE_SERVER_"
	return hmac.new(SECRET, s).hexdigest()

def make_secure_val(s):
    return "%s|%s" % (s, hash_str(s))

def check_secure_val(h):
	if h:
		value = h.split("|")[0]
		if h == make_secure_val(value):
			return value

def make_salt():
    return ''.join(random.choice(string.letters) for x in xrange(5))

def encrypt_password(name, password, salt = ""):
    if not salt:
        salt = make_salt()
        
    h = hashlib.sha256(name + password + salt).hexdigest()
    return '%s,%s' % (h, salt)

def check_password(name, password, h):
    salt = h.split(",")[1]
    if h == encrypt_password(name, password, salt):
        return True
import jinja2
import os
import webapp2
import unit3
import hashlib

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir), autoescape = True)

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
			


def hash_str(s):
    return hashlib.md5(s).hexdigest()

def make_secure_val(s):
    return "%s|%s" % (s, hash_str(s))

def check_secure_val(h):
	value = h.split("|")[0]
	if h == make_secure_val(value):
		return value
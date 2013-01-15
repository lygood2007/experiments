import jinja2
import os
import webapp2

from google.appengine.ext import db

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir), autoescape = True)

class BlogEntry (db.Model):
	title = db.StringProperty(required = True)
	content = db.TextProperty(required = True)
	created = db.DateTimeProperty(auto_now_add = True)

class Handler (webapp2.RequestHandler):
	def write (self, *a, **kw):
		self.response.out.write(*a, **kw)
	
	def render_str (self, template, **params):
		t = jinja_env.get_template(template)
		return t.render(params)
		
	def render (self, template, **kw):
		self.write(self.render_str(template, **kw))
	
class Homework3 (Handler):
    def get(self):
		entries = db.GqlQuery("select * from BlogEntry order by created desc")
		self.render("front.html", entries = entries)
    
class NewPost (Handler):
	def render_form (self, error = "", title="", content=""):
		self.render("newpost.html", error = error, title = title, content = content)
		
	def get(self):
		self.render_form()

	def post(self):
		title = self.request.get("title")
		content = self.request.get("content")
		
		# Verifica se title/content 
		if title and content:
			post = BlogEntry(title = title, content = content)
			post.put()
			self.redirect("/homework3") # TROCAR PELO PERMALINK
		else:
			error = "I need both title and content."
			self.render_form(error, title, content)

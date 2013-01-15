import jinja2
import os
import webapp2

from google.appengine.ext import db

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir), autoescape = True)

class BlogEntry (db.Model):
	subject = db.StringProperty(required = True)
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
	def render_form (self, error = "", subject="", content=""):
		self.render("newpost.html", error = error, subject = subject, content = content)
		
	def get(self):
		self.render_form()

	def post(self):
		subject = self.request.get("subject")
		content = self.request.get("content")
		
		# Verifica se subject/content 
		if subject and content:
			post = BlogEntry(subject = subject, content = content)
			rc = post.put()
			self.redirect("/homework3/%d" % int(rc.id()))
		else:
			error = "I need both subject and content."
			self.render_form(error, subject, content)

class Permalink (Handler):
	def get(self, blog_id): # Não entendi esse blog_id aqui, que tirei de http://forums.udacity.com/questions/6014750/a-couple-helpful-links-for-hw-3#cs253
		entries = [BlogEntry.get_by_id(int(blog_id))]		

		if entries:
			self.render("front.html", entries = entries)
		else:
			self.request.out.write("This is not a valid post!")

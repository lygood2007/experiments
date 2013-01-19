import jinja2
import os
import webapp2

# Base handler
class BaseHandler (webapp2.RequestHandler):

	jinja_env = None

	def __init__(self, request, response):
		self.initialize(request, response)
		template_dir = os.path.join(os.path.dirname(__file__), 'templates')
		self.jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir), autoescape = True)
	
	def write (self, *a, **kw):
		self.response.out.write(*a, **kw)
	
	def render_str (self, template, **params):
		t = self.jinja_env.get_template(template)
		return t.render(params)
		
	def render (self, template, **kw):
		self.write(self.render_str(template, **kw))
import cgi
import unit2 # Tarefas da unidade 2
import blog
import re
import webapp2
import handlers
import logging

# A página principal: um "hello, world!"
class MainPage (webapp2.RequestHandler):
    def get (self):
        self.response.out.write("Hello, Udacity!");

class PageNotFound (webapp2.RequestHandler):
	def get (self):
		self.response.out.write("-- Page not found --")
		
#PAGE_RE = r'(/(?:[a-zA-Z0-9_-]+/?)*)'
PAGE_RE = r'[a-zA-Z0-9_-]+'
		

logging.getLogger().setLevel(logging.DEBUG)

        
# Router
app = webapp2.WSGIApplication([
        ('/', MainPage),
		('/homework1/?', unit2.Homework1),
        ('/homework2/?', unit2.Homework2),
        ('/homework2/welcome/?', unit2.Homework2_WelcomePage),
		('/blog/?', blog.BlogFront),
		('/blog/?.json', blog.BlogJSON),
		('/blog/signup/?', blog.Signup),
		('/blog/welcome/?', blog.Welcome),
		('/blog/login/?', blog.Login),
		('/blog/logout/?', blog.Logout),
		('/blog/newpost/?', blog.NewPost),
		('/blog/(\d+)/?', blog.Permalink),
		('/blog/(\d+)/?.json', blog.PermalinkJSON),
		('/blog/flush/?', blog.FlushMemcache),
		('/wiki/signup/?', handlers.Signup),
		('/wiki/login/?', handlers.Login),
		('/wiki/logout/?', handlers.Logout),
        ('/wiki/_edit/?', handlers.PageEdit),
		('/wiki/_edit/(%s)/?' % PAGE_RE, handlers.PageEdit),
        ('/wiki/_history/?', handlers.PageHistory),
        ('/wiki/_history/(%s)/?' % PAGE_RE, handlers.PageHistory),        
        ('/wiki/?', handlers.PageView),
        ('/wiki/(%s)/?' % PAGE_RE, handlers.PageView),		
		('.*', PageNotFound)
], debug=True)

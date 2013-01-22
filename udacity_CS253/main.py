import cgi
import unit2 # Tarefas da unidade 2
import blog
import re
import webapp2
import handlers

# A página principal: um "hello, world!"
class MainPage (webapp2.RequestHandler):
    def get (self):
        self.response.out.write("Hello, Udacity!");

class PageNotFound (webapp2.RequestHandler):
	def get (self):
		self.response.out.write("-- Page not found --")
		
PAGE_RE = r'(/(?:[a-zA-Z0-9_-]+/?)*)'
		
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
		('/wiki/?', handlers.Welcome),
		('/wiki/signup/?', handlers.Signup),
		#('/wiki/login/?', wiki.Login),
		#('/wiki/logout/?', wiki.Logout),
		#('/wiki/_edit/' + PAGE_RE + '/?', wiki.EditPage),
		#('/wiki/_history/' + PAGE_RE + '/?', wiki.HistoryPage),
		#('/wiki/' + PAGE_RE + '/?', wiki.WikiPage),
		('.*', PageNotFound)
], debug=True)

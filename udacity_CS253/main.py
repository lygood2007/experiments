import cgi
import unit2 # Tarefas da unidade 2
import blog
import re
import webapp2

# A página principal: um "hello, world!"
class MainPage (webapp2.RequestHandler):
    def get (self):
        self.response.out.write("Hello, Udacity!");

class PageNotFound (webapp2.RequestHandler):
	def get (self):
		self.response.out.write("-- Page not found --")
		
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
		('.*', PageNotFound)
], debug=True)

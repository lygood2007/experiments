import cgi
import unit2 # Tarefas da unidade 2
import unit3 # Tarefas da unidade 3
import unit4
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
		('/homework1', unit2.Homework1),
        ('/homework2', unit2.Homework2),
        ('/homework2/welcome', unit2.Homework2_WelcomePage),
        ('/homework3', unit3.Homework3),
        ('/homework3/newpost', unit3.NewPost),
		('/homework3/(\d+)', unit3.Permalink),
		('/homework4', unit4.Unit4),
		('/homework4/signup', unit4.Signup),
		('/homework4/welcome', unit4.Welcome),
		('/homework4/login', unit4.Login),
		('/homework4/logout', unit4.Logout),
		('.*', PageNotFound)
], debug=True)

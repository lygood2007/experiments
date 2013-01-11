import cgi
import homeworks # Meu módulo com as tarefas de casa
import re
import webapp2

# A página principal: um "hello, world!"
class MainPage (webapp2.RequestHandler):
    def get (self):
        self.response.out.write("Hello, Udacity!");

# Router
app = webapp2.WSGIApplication([
        ('/', MainPage),
	('/unit2/homework1', homeworks.Homework1),
        ('/unit2/homework2', homeworks.Homework2),
        ('/unit2/homework2/welcome', homeworks.Homework2_WelcomePage)
], debug=True)

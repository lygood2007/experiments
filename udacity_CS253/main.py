import cgi
import re
import webapp2
import wiki.handlers
import blog.handlers

# A página principal: um "hello, world!"
class MainPage (webapp2.RequestHandler):
    def get (self):
        self.response.out.write("Hello, Udacity!");

class PageNotFound (webapp2.RequestHandler):
	def get (self):
		self.response.out.write("-- Page not found --")
		
#PAGE_RE = r'(/(?:[a-zA-Z0-9_-]+/?)*)'
PAGE_RE = r'[a-zA-Z0-9_-]+'
        
# Router
app = webapp2.WSGIApplication([
        ('/', MainPage),
		('/blog/?', blog.handlers.BlogFront),
		('/blog/?.json', blog.handlers.BlogJSON),
		('/blog/signup/?', blog.handlers.Signup),
		('/blog/welcome/?', blog.handlers.Welcome),
		('/blog/login/?', blog.handlers.Login),
		('/blog/logout/?', blog.handlers.Logout),
		('/blog/newpost/?', blog.handlers.NewPost),
		('/blog/(\d+)/?', blog.handlers.Permalink),
		('/blog/(\d+)/?.json', blog.handlers.PermalinkJSON),
		('/blog/flush/?', blog.handlers.FlushMemcache),
		('/wiki/signup/?', wiki.handlers.Signup),
		('/wiki/login/?', wiki.handlers.Login),
		('/wiki/logout/?', wiki.handlers.Logout),
        ('/wiki/_edit/?', wiki.handlers.PageEdit),
		('/wiki/_edit/(%s)/?' % PAGE_RE, wiki.handlers.PageEdit),
        ('/wiki/_history/?', wiki.handlers.PageHistory),
        ('/wiki/_history/(%s)/?' % PAGE_RE, wiki.handlers.PageHistory),        
        ('/wiki/?', wiki.handlers.PageView),
        ('/wiki/(%s)/?' % PAGE_RE, wiki.handlers.PageView),
        ('/wiki/?.json', wiki.handlers.PageJSON),
        ('/wiki/(%s)/?.json', wiki.handlers.PageJSON),
        ('/wiki/flush', wiki.handlers.FlushMemcache),
		('.*', PageNotFound)
], debug=True)

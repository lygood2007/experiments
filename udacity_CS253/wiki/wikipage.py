from google.appengine.ext import db
import logging

class Page (db.Model):
	title = db.StringProperty(required = False)
	content = db.TextProperty(required = True)
	timestamp = db.DateTimeProperty(auto_now_add = True)
	uid = db.IntegerProperty(required = True)
	newer = db.BooleanProperty(required = True)
	version = db.IntegerProperty(required = True)
	
	@classmethod
	def by_name(cls, name, version = None):
		if version:
			page = Page.all().filter('title =', name).filter('version = ', int(version)).get()
		else:
			page = Page.all().filter('title =', name).filter('newer = ', True).get()
			
		return page
	
	@classmethod
	def create(cls, name, content, uid):
		
		previous_page = Page.all().filter("title = ", name).filter("newer = ", True).get()
		
		if not previous_page:
			version = 1
		else:			
			version = previous_page.version + 1
			previous_page.newer = False
			previous_page.put()
			
		current_page = Page(title = name, content = content, uid = uid, newer = True, version = version)
	
		current_page.put()
			

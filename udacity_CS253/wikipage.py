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
	def register(cls, name, content, uid):
		
		current_version = 1
		
		previous_page = Page.all().filter("title = ", name).filter("newer = ", True).get()
		
		if previous_page:
			previous_page.newer = False
			previous_page.put()
			current_version = previous_page.version + 1
			
		current_page = Page(title = name, content = content, uid = uid, newer = True, version = current_version)
		current_page.put()		
		# TODO: se der pau entre o primeiro e o segundo put(), acima, a base de dados fica inconsistente.
			
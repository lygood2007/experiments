import webapp2
import cgi
import re

unit2_homework1_form="""
<form method="post">
	<br>
	<textarea name="text">%(text)s</textarea>
	<br>
	<input type="submit">
</form>
"""

unit2_homework2_form = """
    <h2>Signup</h2>
    <form method="post">
      <table>
        <tr>
          <td class="label">
            Username
          </td>
          <td>
            <input type="text" name="username" value="%(username)s">
          </td>
          <td class="error">
            <div style="color: red; display: %(display_username_error_message)s">That's not a valid username</div>
          </td>
        </tr>

        <tr>
          <td class="label">
            Password
          </td>
          <td>
            <input type="password" name="password" value="">
          </td>
          <td class="error">
          </td>
        </tr>

        <tr>
          <td class="label">
            Verify Password
          </td>
          <td>
            <input type="password" name="verify" value="">
          </td>
          <td class="error">
            <div style="color: red; display: %(display_password_error_message)s">Your passwords didn't match</div>
          </td>
        </tr>

        <tr>
          <td class="label">
            Email (optional)
          </td>
          <td>
            <input type="text" name="email" value="%(email)s">
          </td>
          <td class="error">
            <div style="color: red; display: %(display_email_error_message)s">That's not a valid email</div>
          </td>
        </tr>
      </table>

      <input type="submit">
    </form>
"""

USERNAME_RE = re.compile(r"^[a-zA-Z0-9_-]{3,20}$")
PASSWORD_RE = re.compile(r"^.{3,20}$")
EMAIL_RE    = re.compile(r"^[\S]+@[\S]+\.[\S]+$")

def escape (s):
    return cgi.escape(s, quote=True)

#----------------------------------------
class Homework1 (webapp2.RequestHandler):
	def write_form(self, text=""):
		escaped_text = escape(text)
		self.response.out.write(unit2_homework1_form % {"text":escaped_text})

	def get(self):
		self.write_form()

	def post(self):
		user_text = self.request.get("text")		
		self.write_form(user_text.encode('rot13'))

#----------------------------------------
class Homework2 (webapp2.RequestHandler):
        def write_form(self, username="",
                             email="",
                             display_username_error_message="none",
                             display_password_error_message="none",
                             display_email_error_message="none"):
            
                self.response.out.write(unit2_homework2_form % {"username":username,
                                                                "email":email,
                                                                "display_username_error_message":display_username_error_message,
                                                                "display_password_error_message":display_password_error_message,
                                                                "display_email_error_message":display_email_error_message})
                
        def get(self):
                self.write_form()

        def post(self):

                username = self.request.get("username")
                password = self.request.get("password")
                verify   = self.request.get("verify")
                email    = self.request.get("email")

                valid_username = USERNAME_RE.match(username)
                valid_password = PASSWORD_RE.match(password) and (password == verify)
                valid_email    = email == "" or EMAIL_RE.match(email)

                if not (valid_username and valid_password and valid_email):
                    display_username_error_message = "none" if valid_username else "block"
                    display_password_error_message = "none" if valid_password else "block"
                    display_email_error_message = "none" if valid_email else "block"
                    self.write_form(escape(username), escape(email), display_username_error_message, display_password_error_message, display_email_error_message)
                else:
                    self.redirect("/unit2/homework2/welcome?username=%s" % username)
                
class Homework2_WelcomePage (webapp2.RequestHandler):
        def get(self):
                username = self.request.get("username")
                self.response.out.write("Welcome, %s!" % username)

#app = webapp2.WSGIApplication([
#	('/unit2/homework1', Homework1),
#        ('/unit2/homework2', Homework2),
#        ('/unit2/homework2/welcome', Homework2_WelcomePage)
#], debug=True)



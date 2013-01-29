class Action ():
	m = 0
	c = 0
	b = -1 # -1 when moving from left to right; +1 otherwise
	
	def __init__ (self, m, c, b):
		if (m + c > 0) and (m + c < 3) and (b == 1 or b == -1):
			self.m = m
			self.c = c
			self.b = b
			
class R2LAction (Action):
	def __init__ (self, m, c):
		Action.__init__(self, m, c, +1)

class L2RAction (Action):
	def __init__ (self, m, c):
		Action.__init__(self, -m, -c, -1)
	

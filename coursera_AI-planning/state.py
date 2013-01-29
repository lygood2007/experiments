
class State ():
	m = 0 # 0 to 3
	c = 0 # 0 to 3
	b = 1 # 1 or 0
	
	def __init__ (self, m, c, b):
		self.m = m
		self.c = c
		self.b = b
	
	def evolve(self, action):
		state = State(self.m, self.c, self.b)
		state.m += action.m
		state.c += action.c
		state.b += action.b
		
		if self.valid(state):
			return state
		else:
			print "invalid state"
		
	def valid (self, state):
		return state.m in range(0,4) and state.c in range(0,4) and state.m >= state.c
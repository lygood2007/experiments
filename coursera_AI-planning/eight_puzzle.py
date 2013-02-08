# A state s is given by the vector of length 9, where the index i indicates the position in the 8 puzzle square
# and s[i] is the piece in that square. An action is just an integer number, which can be -3, -1, +1 or +3.

from Queue import PriorityQueue

DEBUG = False
N = 10000

# Defines the problem to be solved
eight_puzzle_problem = {
	"initial-state": [1, 6, 4, 8, 7, 0, 3, 2, 5], # Initial state: piece 1 at position 0, piece 6 at position 1 etc
	#"initial-state": [1, 2, 0, 3, 4, 5, 6, 7, 8],
	"goal-state": 	 [0, 1, 2, 3, 4, 5, 6, 7, 8]  # Goal state: hole (0) at position 0, piece 1 at position 1 etc
}

# Performs the A* search
def aStar_search (problem, heuristic):

	n0 = (0, 0, None, problem["initial-state"]) # node = (f, g, action, state)
	goal_state = problem["goal-state"]

	fringe = PriorityQueue(N)
	fringe.put(n0)

	verbose("Adding the initial state to the priority queue.")

	path = []

	visited_states = []

	proceed = True
	while proceed:

		verbose("Number of visited states so far: %s\nFringe has %s candidates..." % (len(visited_states), fringe.qsize()))

		if fringe.qsize() == 0:
			verbose("... Search has failed.")
			path = []
			proceed = False
		else:
			node = fringe.get()
			state = node[3]
			visited_states.append(state)

			verbose("... Next state: %s" % state)

			if is_goal_state(state, goal_state):
				verbose("Current state is a goal: search has finished.")
				path.append(node)
				proceed = False
				print("Search has finished successfully with %s evaluations." % len(visited_states))
			else:
				verbose("Current state is not a goal. Checking accessible states...")
				available_states = filter(lambda (a,s): not (s in visited_states), successors(state))
				#available_states = filter(lambda (a,s): evolve(s,inverse(a)) != state, successors(state))
				#available_states = successors(state)

				verbose("... There are %s available (action,state) pairs: %s" % (len(available_states), available_states))

				for (a,s) in available_states:					
					next_state = s
					next_state_action = a
					next_state_g = g(node) + action_cost(next_state_action)
					next_state_f = next_state_g + heuristic(next_state, goal_state)
					next_node = (next_state_f, next_state_g, next_state_action, next_state)

					verbose("%s Adding node %s to the fringe." % (1 ,next_node))

					if fringe.qsize() < N:
						fringe.put(next_node)
					else:
						print("WARNING: fringe is growing faster than search. I am missing states!...")
						proceed = False

	return path

# Returns the successor states of a given state <s>, ie, the set of reachable states from <s>
def successors (s):
	i = s.index(0)
	return [(a, evolve(s,a)) for a in APPLICABLE_ACTIONS[i]]

# Applies the action <a> in state <s>.
def evolve (s, a):

	s_copy = list(s)

	if is_applicable(s_copy,a):
		current_index = s_copy.index(0)
		target_index = current_index + a	
		s_copy[current_index] = s_copy[target_index]
		s_copy[target_index] = 0

	return s_copy

# Returns the opposite action of a given action <a>
def inverse (a):
	if a:
		return -a
	else:
		return 0

# Evaluation function: estimation of the cost of going from initial state (associated with node <n0>) to goal state <gs> through state <s> (node <n>)
# Notice: the first two arguments are nodes; the third is a state
# TODO: does the first argument need to be a node? Or it can be a state?
def f (n0, n, gs):
	return g(n0, n) + h(n[1], gs)

# Heuristic function: estimation of the cost of going from the current state <s> to the goal state <gs>
# Notice: h depends on the STATES s and gs
def h (s, gs):
	return h1(s, gs)

# Actual cost of going from initial node <n0> to the current node <n>
def g (n):
	return n[1]

# Heuristic 1: number of misplaced pieces
# (it suposes both s and gs are valid states)
def h1 (s, gs):
	count = 0
	for i in range(0,9):
		if not (s[i] == 0) and (s[i] != gs[i]): count += 1

	return count

# The cost of any action <a> is +1
def action_cost (a):
	return 1

# Heuristic 2: Manhattan distance (TODO)
# (it suposes both s and gs are valid states)
def h2 (s, gs):
	i1 = gs.index(0)
	i2 = s.index(0)
	p1 = (i1 // 3, i1 % 3)
	p2 = (i2 // 3, i2 % 3)
	return (p2[0] - p1[0]) + (p2[1] - p1[1])

# Checks whether a given action <a> is applicable to state <s>
def is_applicable (s, a):
	i = s.index(0)
	return a in APPLICABLE_ACTIONS[i]

# Checks if a given state <s> is the goal state.
# (it suposes both <s> and <gs> are valid states)
def is_goal_state (s, gs):
	return s == gs

# Checks if a given state <s> is valid.
def is_valid_state (s):
	ans = True

	if len(s) == 9:
		for piece in s: ans = ans and (piece in range(0,9))
	else:
		ans = False

	return ans

# Checks if a given action <a> is valid.
def is_valid_action (a):
	return a == -3 or a == -1 or a == +1 or a == +3

# Defines the applicable actions (see below)
APPLICABLE_ACTIONS = [
	[        +1, +3], # In a given state, if the hole is in position 0, only actions +1 and +3 can be applied
	[    -1, +1, +3], # If hole's position is 1, only -1, +1 and +3 are applicable
	[    -1,     +3],
	[-3,     +1, +3],
	[-3, -1, +1, +3],
	[-3, -1,     +3],
	[-3,     +1    ], # Only action -3 and +1 can be applied
	[-3, -1, +1    ],
	[-3, -1        ]
]

# Verbose
def verbose (msg):
	if DEBUG: raw_input(msg)

class TreeNode:
	state    = None	
	action   = None
	cost     = 0
	parent   = None
	children = []

	def __init__ (self, state, action, cost, parent = None):
		self.state = list(state)
		self.action = action
		self.cost = cost
		self.parent = parent

	def appendChild (self, t):
		t.parent = self
		self.children.append(t)

	def is_root (self):
		return self.parent == None

	def depth (self):

		count = 0
		node = self.parent

		while (node):
			count += 1
			node = node.parent

		return count

	def height (self):

		ans = 0

		if not has_children():
			ans = max([height(child) for child in self.children])

		return ans

	def has_children(self):
		return len(children) > 0
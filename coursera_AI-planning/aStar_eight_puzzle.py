# A state s is given by the vector of length 9, where the index i indicates the position in the 8 puzzle square
# and s[i] is the piece in that square. An action is just an integer number, which can be -3, -1, +1 or +3.

from Queue import PriorityQueue
import time

DEBUG = False
N = 10000

# Defines the problem to be solved
eight_puzzle_problem = {
	"initial-state": [1, 6, 4, 8, 7, 0, 3, 2, 5], # Initial state: piece 1 at position 0, piece 6 at position 1 etc
	"goal-state": 	 [0, 1, 2, 3, 4, 5, 6, 7, 8]  # Goal state: hole (0) at position 0, piece 1 at position 1 etc
}

# Performs the A* search
def aStar_search (problem, heuristic):

	t0 = time.clock()

	n0 = TreeNode(problem["initial-state"])
	GOAL_STATE = problem["goal-state"]

	fringe = PriorityQueue(N)
	fringe.put((n0.total_cost,n0))

	verbose("Adding the initial state to the fringe.")

	plan = None

	visited_states = []

	keep_searching = True
	while keep_searching:
		
		verbose("Number of visited states so far: %s\nFringe has %s candidate%s..." % (len(visited_states), fringe.qsize(), ("" if fringe.qsize() == 1 else "s")))

		if fringe.empty():
			plan = None
			keep_searching = False

			verbose("Fringe is empty: SEARCH HAS FAILED!")

		else:
			node = fringe.get()[1]
			visited_states.append(node.state)

			verbose("... Best state candidate: %s" % node.state)

			if node.state == GOAL_STATE:
				plan = getPlan(node)
				keep_searching = False

				verbose("Current state is a goal: SEARCH HAS FINISHED SUCCESSFULLY!\n\
						 Plan (%s actions): %s\n\
						 Number of nodes visited: %s.\n" % (len(plan), plan, len(visited_states)))

			else:				
				available_states = filter(lambda (a,s): not (s in visited_states), successors(node.state))

				verbose("Current state is not a goal. Checking accessible states...")
				verbose("There are %s available (action,state) pairs: %s" % (len(available_states), available_states))

				for (a,s) in available_states:
					cost = node.cost + action_cost(a) # g(n)
					total_cost = cost + heuristic(s, GOAL_STATE) # f(n)
					child_node = TreeNode(s, a, cost, total_cost)
					
					if fringe.qsize() == N:
						print("WARNING: fringe is growing too fast. Increase its maximum allowed size N or enhance heuristic.")
						keep_searching = False
					else:
						fringe.put((child_node.total_cost,child_node))
						node.appendChild(child_node)
						verbose("Adding node %(node)s to the fringe." % {"node": child_node})
						
	print("Elapsed time: %s seconds" % (time.clock() - t0))
	return plan

# Given a leaf node in the tree search, return the plan from initial state to the current state/node
def getPlan (node):
	plan = [] # "nalp" is the reverse of "plan"
	verbose("node.is_root() = %s" % node.is_root())
	while not node.is_root():
		verbose("node.action = %s" % node.action)
		plan.append(node.action)
		verbose("nalp = %s" % plan)
		verbose("node.parent = %s" % node.parent)
		node = node.parent

	plan.reverse()
	return plan

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

# The cost of any action <a> is +1
def action_cost (a):
	return 1

# Heuristic 1: number of misplaced pieces
# (it suposes both s and gs are valid states)
def h1 (s, gs):
	count = 0
	for i in range(0,9):
		if not (s[i] == 0) and (s[i] != gs[i]): count += 1

	return count

# Heuristic 2: Manhattan distance
# (it suposes both s and gs are valid states)
def h2 (s, gs):

	d = 0

	for i in range(1,9):
		i1 = gs.index(i)
		i2 = s.index(i)
		p1 = (i1 // 3, i1 % 3)
		p2 = (i2 // 3, i2 % 3)
		tmp = abs(p2[0] - p1[0]) + abs(p2[1] - p1[1])
		verbose("d(%s) = %s" % (i,tmp))
		d += tmp

	return d

# Checks whether a given action <a> is applicable to state <s>
def is_applicable (s, a):
	i = s.index(0)
	return a in APPLICABLE_ACTIONS[i]

# Checks if a given state <s> is valid.
def is_valid_state (s):
	ans = True

	if len(s) == 9:
		for piece in s: ans = ans and (piece in range(0,9))
	else:
		ans = False

	return ans

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

# Represents a node in the search tree
class TreeNode:
	state      = None	
	action     = None
	cost       = 0
	total_cost = 0
	parent     = None
	children   = []

	def __init__ (self, state, action = None, cost = 0, total_cost = 0, parent = None):
		self.state = list(state)
		self.action = action
		self.cost = cost
		self.total_cost = total_cost
		self.parent = parent

	def appendChild (self, t):
		t.parent = self
		self.children.append(t)

	def is_root (self):
		return self.parent == None
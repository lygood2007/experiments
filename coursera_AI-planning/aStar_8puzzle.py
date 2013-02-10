# A state s is given by the vector of length 9, where the index i indicates the position in the 8 puzzle square
# and s[i] is the piece in that square. An action is just an integer number, which can be -3, -1, 0, +1 or +3.

from Queue import PriorityQueue
import time

DEBUG = False
STEP_BY_STEP = False
N = 1000000

# Performs the A* tree/graph-search.
# The main difference between the graph- and a tree-search is that in the graph-search we keep a list
# of visited nodes, which is used to avoid visiting the same node twice. So, when expanding a node,
# we apply a filter to its successors, which removes from it all nodes already visited. In the tree
# search, however, the only filter we apply to the successors is the one that removes from it the
# node (only one) that is the parent of the current node (in this case we were going back in the search).
def aStar_search (problem, heuristic, tree_search = False):

	t0 = time.clock()
	n0 = Node(problem["initial-state"])
	GOAL_STATE = problem["goal-state"]
	plan = None

	if is_valid_state(n0.state) and is_valid_state(GOAL_STATE):

		indentation = 0
		verbose("Performing A* %s-search" % ("tree" if tree_search else "graph"), indentation)
		verbose("Add the initial node to the fringe.", indentation)

		fringe = PriorityQueue(N) # Also known as "open list"
		fringe.put((n0.total_cost,n0))

		visited_states = {}
		n_visited_state = 0
		
		keep_searching = True

		while keep_searching:
			
			verbose("# of visited nodes so far: %s\nFringe has %s node%s." \
					 % (n_visited_state, fringe.qsize(), ("" if fringe.qsize() == 1 else "s")), indentation)

			if fringe.empty():
				plan = None
				keep_searching = False

				verbose("Fringe is empty: SEARCH HAS FAILED!", indentation)

			else:
				node = fringe.get()[1]
				visited_states[getHash(node.state)] = node.state
				n_visited_state += 1

				indentation = 2 * node.depth()

				verbose("Cheapest node in the fringe: %s" % node.state, indentation)

				if node.state == GOAL_STATE:
					plan = getPlan(node)
					keep_searching = False

					verbose("Node's state is a goal: SEARCH HAS FINISHED SUCCESSFULLY!\nPlan (%s actions): %s" \
						    % (len(plan), plan), indentation)

				else:		
					available_states = successors(node.state)

					if tree_search:
						if not node.is_root():
							available_states = filter(lambda (a,s): not (s == node.parent.state), available_states)
					else:
						available_states = filter(lambda (a,s): not (getHash(s) in visited_states), successors(node.state))

					singular = len(available_states) == 1
					verbose("Current node's state is not a goal. Expand the node.\nThere %(is_or_are)s %(n_pairs)s available (action,state) pair%(s_or_none)s: %(pairs)s" \
					         % {"is_or_are" : "is" if singular else "are",
					            "n_pairs"   : len(available_states),
					            "s_or_none" : "" if singular else "s",
					            "pairs"     : available_states}, indentation)

					for (a,s) in available_states:
						cost = node.cost + action_cost(a) # g(n)
						total_cost = cost + heuristic(s, GOAL_STATE) # f(n)
						child_node = Node(s, a, cost, total_cost)
						
						if fringe.qsize() == N:
							print("WARNING: not enough memory. Increase fringe's maximum allowed size N or enhance heuristic.")
							keep_searching = False
						else:
							fringe.put((child_node.total_cost,child_node))
							node.appendChild(child_node)
							
		print("Elapsed time: %(dt)s seconds.\nVisited nodes: %(n)s." \
		       % {"dt" : time.clock() - t0,
		          "n"  : n_visited_state})
	else:
		print("ERROR: initial or goal state is invalid.")

	return plan

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
		p1 = (i1 // 3, i1 % 3)
		i2 = s.index(i)		
		p2 = (i2 // 3, i2 % 3)
		d += abs(p2[0] - p1[0]) + abs(p2[1] - p1[1])

	return d

# Returns a hash of the state, which is just the concatenation of the pieces
def getHash (s):
	h = 0
	for i in range(9): h += s[i] * 10**i
	return h


# Given a <node> in the tree, return the plan from initial state to the current state/node
def getPlan (node):
	plan = []
	
	while not node.is_root():
		plan.append(node.action)
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
def verbose (msg, indentation = 0):	
	if DEBUG:
		indent = " " * indentation
		msg = msg.replace("\n", "\n" + indent)

		if STEP_BY_STEP:
			raw_input(indent + msg)
		else:
			print(indent + msg)

# Represents a node in the search tree, which is associated to a state.
class Node:
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

	# Appends a child Node to this node
	def appendChild (self, t):
		t.parent = self
		self.children.append(t)

	# Checks whether this is a root node (ie, if it has no parent)
	def is_root (self):
		return self.parent == None

	# Returns the depth of this node in the tree
	def depth (self):
		count = 0

		parent = self.parent
		while parent:
			count += 1
			parent = parent.parent

		return count
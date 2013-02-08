# A state s is given by the vector of length 9, where the index i indicates the position in the 8 puzzle square
# and s[i] is the piece in that square. An action is just an integer number, which can be -3, -1, +1 or +3.

# Initial state: piece 1 at position 0, piece 6 at position 1 etc
s = [1, 6, 4, 8, 7, 0, 3, 2, 5]

# Goal state: hole (0) at position 0, piece 1 at position 1 etc
goal = [0, 1, 2, 3, 4, 5, 6, 7, 8]

fringe = [s]

# Returns the successor states of a given state <s>, ie, the set of reachable states from <s>
def successors (s):
	i = s.index(0)
	return [evolve(s,a) for a in applicable_actions[i]]

# Applies the action <a> in state <s>.
def evolve (s, a):

	s_copy = [piece for piece in s]

	if is_applicable(s_copy,a):
		current_index = s_copy.index(0)
		target_index = current_index + a	
		s_copy[current_index] = s_copy[target_index]
		s_copy[target_index] = 0

	return s_copy

# Evaluation function: estimation of the cost of going from initial state <s0> to goal state <gs> through state <s>
def f (s0, s, gs):
	return g(s0, s) + h(s, gs)

# Heuristic function: estimation of the cost of going from the current state <s> to the goal state <gs>
def h (s, gs):
	return h1(s, gs)

# Heuristic 1: number of misplaced pieces
# (it suposes both s and gs are valid states)
def h1 (s, gs):
	count = 0

	for i in range(0,9):
		if not (s[i] == 0) and (s[i] != gs[i]): count += 1

	return count

# Heuristic 2: Manhattan distance (TODO)
def h2 (s, gs):
	pass

# Actual cost of going from initial state <s0> to the current state <s>
def g (s0, s):
	return 0

# Checks whether a given action <a> is applicable to state <s>
def is_applicable (s, a):
	i = s.index(0)
	return a in applicable_actions[i]

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
applicable_actions = [
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
	# (define (problem random-pbl1)
	#   (:domain random-domain)
	#   (:init
	#      (S B B) (S C B) (S A C)
	#      (R B B) (R C B))
	#   (:goal (and (S A A))))

	# (define (domain random-domain)
	#   (:requirements :strips)
	#   (:action op1
	#     :parameters (?x1 ?x2 ?x3)
	#     :precondition (and (S ?x1 ?x2) (R ?x3 ?x1))
	#     :effect (and (S ?x2 ?x1) (S ?x1 ?x3) (not (R ?x3 ?x1))))
	#   (:action op2
	#     :parameters (?x1 ?x2 ?x3)
	#     :precondition (and (S ?x3 ?x1) (R ?x2 ?x2))
	#     :effect (and (S ?x1 ?x3) (not (S ?x3 ?x1)))))

S = 0
R = 1
A = 0
B = 1
C = 2

# Compare the triples below to :init above
s0 = [(S,B,B), (S,C,B), (S,A,C), (R,B,B), (R,C,B)]

# Compare the triple below to :goal above
goal = [(S,A,A)]

# Compare the condition below to :precondition of op1 above
def op1_is_applicable (x1, x2, x3, s):
	return ((S, x1, x2) in s) and ((R, x3, x1) in s)

# Compare the condition below to :effect of op1 above
def op1_is_relevant (x1, x2, x3, state):

	effects_plus = [(S,x2,x1), (S,x1,x3)]
	effects_minus = [(R,x3,x1)]

	cond1 = False
	cond2 = True
	for triple in state:
		cond1 = cond1 or (triple in effects_plus) # Any of the 'and'-effects is in <state>
		cond2 = cond2 and (not (triple in effects_minus)) # All of the 'not'-effects aren't in <state>

	return cond1 and cond2

# Compare the condition below to :precondition of op2 above
def op2_is_applicable (x1, x2, x3, s):
	return ((S, x3, x1) in s) and ((R, x2, x2) in s)

# Compare the condition below to :effect of op1 above
def op2_is_relevant (x1, x2, x3, state):

	effects_plus = [(S,x1,x3)]
	effects_minus = [(S,x3,x1)]

	cond1 = False
	cond2 = True
	for triple in state:
		cond1 = cond1 or (triple in effects_plus) # Any of the 'and'-effects is in <state>
		cond2 = cond2 and (not (triple in effects_minus)) # All of the 'not'-effects aren't in <state>

	return cond1 and cond2

# Tests the <operation> against <state> for all possible input parameters
def test (operation, state, verbose = False):
	count = 0

	for x1 in range(3):
		for x2 in range(3):
			for x3 in range(3):
				cond = operation(x1,x2,x3,state)				
				if cond: count += 1

				if verbose: print "op(%s,%s,%s) --> %s" % (x1,x2,x3,cond)

	return count

# --------------------------------------------------------

print "Number of applicable op1(x1,x2,x3) to state: %s" % test(op1_is_applicable, s0)
print "Number of applicable op2(x1,x2,x3) to state: %s" % test(op2_is_applicable, s0)
print ""
print "Number of relevant op1(x1,x2,x3) to goal: %s" % test(op1_is_relevant, goal)
print "Number of relevant op2(x1,x2,x3) to goal: %s" % test(op2_is_relevant, goal)

# The output (2013.02.10):
# Number of applicable op1(x1,x2,x3) to state: 2
# Number of applicable op2(x1,x2,x3) to state: 3
# Number of relevant op1(x1,x2,x3) to goal: 5
# Number of relevant op2(x1,x2,x3) to goal: 0
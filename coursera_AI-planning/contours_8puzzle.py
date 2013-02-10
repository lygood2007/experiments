from aStar_8puzzle import getHash, successors
from time import clock

# Evaluates the number of reachable states from s0 within i <= n applied actions
def contours (s0, n):

	n_nodes = []

	current_list = [s0]
	closed_list = {getHash(s0) : s0}

	for i in range(n):
		n_nodes.append(len(current_list))

		if i == n-1: break
	
		next_list = []

		for state in current_list:
			for (a,s) in successors(state):
				h = getHash(s)
				if not (h in closed_list):
					closed_list[h] = 0 # Only hash key is important
					next_list.append(s)

		current_list = next_list

	return n_nodes

# Prints the output in tabular format
def toString (s):
	output = ""
	for i in range(len(s)):
		output += "%s\t%s\n" % (i, s[i])
	return output

t0 = clock()
result = contours([0,1,2,3,4,5,6,7,8], 33)
print "Elapsed time: %s seconds" % (clock() - t0)
print toString(result)

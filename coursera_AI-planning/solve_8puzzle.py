from aStar_8puzzle import aStar_search, h1, h2

# The problem to be solved
EIGHT_PUZZLE_PROBLEM = {
	"initial-state": [1, 6, 4, 8, 7, 0, 3, 2, 5], # Initial state: piece 1 at position 0, piece 6 at position 1 etc
	"goal-state": 	 [0, 1, 2, 3, 4, 5, 6, 7, 8]  # Goal state: hole (0) at position 0, piece 1 at position 1 etc
}

# What kind of search: tree (True) or graph-search (False)
TREE_SEARCH = False

# Heuristic: h1 or h2 (from module imported)
HEURISTIC = h2

print("A* %(search_type)s-search with heuristic %(heuristic)s." %
	  {"search_type" : "tree" if TREE_SEARCH else "graph",
	   "heuristic"   : "h1 (miplaced pieces)" if HEURISTIC == h1 else "h2 (Manhattan distance)"})

plan = aStar_search(EIGHT_PUZZLE_PROBLEM, HEURISTIC, TREE_SEARCH)

if plan:
	print("Plan (%s actions): %s" % (len(plan), plan))
else:
	print("No solution found.")
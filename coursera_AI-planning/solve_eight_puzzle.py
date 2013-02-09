from aStar_eight_puzzle import aStar_search

print("Search with heuristic h1 (number of misplaced pieces):")
result_h1 = aStar_search(eight_puzzle_problem, h1)
print("%s actions: %s" % (len(result_h1), result_h1))

print("Search with heuristic h1 (Manhattan distance):")
result_h2 = aStar_search(eight_puzzle_problem, h2)
print("%s actions: %s" % (len(result_h2), result_h2))
from eight_puzzle import *

goal = [0, 1, 2, 3, 4, 5, 6, 7, 8]

# Goal state
state_1 = [0,1,2,3,4,5,6,7,8]
print is_valid_state(state_1) # True
print is_goal_state(state_1, goal) # True

# In a better approach, this is also a goal state. By now, it is not.
state_2 = [1,0,2,3,4,5,6,7,8] 
print is_valid_state(state_2) # True
print is_goal_state(state_2, goal) # False

# This is not even a state!
state_3 = [0,1,2,3,4,5,6,7,8,9] 
print is_valid_state(state_3) # False
print is_goal_state(state_3, goal) # False

print h1(state_1, goal) # 0
print h1(state_2, goal) # 1

state_4 = [1,2,3,4,5,6,7,8,0]
print h1(state_4, goal) # 8

print (evolve(state_4,-3) == [1,2,3,4,5,0,7,8,6]) # True
print (evolve(state_2,-1) == goal) # True

state = [0,1,2,3,4,5,6,7,8]
print ("False == %s" % is_applicable(state, -3))
print ("False == %s" % is_applicable(state, -1))
print ("True  == %s" % is_applicable(state, +1))
print ("True  == %s" % is_applicable(state, +3))

state = [1,0,2,3,4,5,6,7,8]
print ("False == %s" % is_applicable(state, -3))
print ("True  == %s" % is_applicable(state, -1))
print ("True  == %s" % is_applicable(state, +1))
print ("True  == %s" % is_applicable(state, +3))

state = [1,2,0,3,4,5,6,7,8]
print ("False == %s" % is_applicable(state, -3))
print ("True  == %s" % is_applicable(state, -1))
print ("False == %s" % is_applicable(state, +1))
print ("True  == %s" % is_applicable(state, +3))

state = [1,2,3,0,4,5,6,7,8]
print ("True  == %s" % is_applicable(state, -3))
print ("False == %s" % is_applicable(state, -1))
print ("True  == %s" % is_applicable(state, +1))
print ("True  == %s" % is_applicable(state, +3))

state = [1,2,3,4,0,5,6,7,8]
print ("True  == %s" % is_applicable(state, -3))
print ("True  == %s" % is_applicable(state, -1))
print ("True  == %s" % is_applicable(state, +1))
print ("True  == %s" % is_applicable(state, +3))

state = [1,2,3,4,5,0,6,7,8]
print ("True  == %s" % is_applicable(state, -3))
print ("True  == %s" % is_applicable(state, -1))
print ("False == %s" % is_applicable(state, +1))
print ("True  == %s" % is_applicable(state, +3))

state = [1,2,3,4,5,6,0,7,8]
print ("True  == %s" % is_applicable(state, -3))
print ("False == %s" % is_applicable(state, -1))
print ("True  == %s" % is_applicable(state, +1))
print ("False == %s" % is_applicable(state, +3))

state = [1,2,3,4,5,6,7,0,8]
print ("True  == %s" % is_applicable(state, -3))
print ("True  == %s" % is_applicable(state, -1))
print ("True  == %s" % is_applicable(state, +1))
print ("False == %s" % is_applicable(state, +3))

state = [1,2,3,4,5,6,7,8,0]
print ("True  == %s" % is_applicable(state, -3))
print ("True  == %s" % is_applicable(state, -1))
print ("False == %s" % is_applicable(state, +1))
print ("False == %s" % is_applicable(state, +3))

state = [0,1,2,3,4,5,6,7,8]
hope = [[1,0,2,3,4,5,6,7,8], [3,1,2,0,4,5,6,7,8]]
print ("%s == %s" % (hope, successors(state)))
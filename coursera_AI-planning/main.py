from state import State
from action import L2RAction, R2LAction

s1 = State(3, 3, 1)
a1 = L2RAction(1,1)
s2 = s1.evolve(a1)
a2 = R2LAction(1,0)
s3 = s2.evolve(a2)

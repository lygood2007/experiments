import MCSearch

states_test = [
	(State (3,3,1), True), -- INITIAL STATE
	(State (3,3,0), True), -- not reachable
	(State (3,2,1), True), -- not reachable
	(State (3,2,0), True),
	(State (3,1,1), True),
	(State (3,1,0), True),
	(State (3,0,1), True),
	(State (3,0,0), True),
	(State (2,3,1), False),
	(State (2,3,0), False),
	(State (2,2,1), True),
	(State (2,2,0), True),
	(State (2,1,1), False),
	(State (2,1,0), False),
	(State (2,0,1), False),
	(State (2,0,0), False),
	(State (1,3,1), False),
	(State (1,3,0), False),
	(State (1,2,1), False),
	(State (1,2,0), False),
	(State (1,1,1), True),
	(State (1,1,0), True),
	(State (1,0,1), False),
	(State (1,0,0), False),
	(State (0,3,1), True),
	(State (0,3,0), True),
	(State (0,2,1), True),
	(State (0,2,0), True),
	(State (0,1,1), True),
	(State (0,1,0), True),
	(State (0,0,1), True), -- not reachable
	(State (0,0,0), True)  -- SOLUTION
				]

test_1 :: [(State, Bool)]
test_1 = filter test states_test
		 where test (s,b) = not (checkState s == b)

sfs :: [[(Action, State)]]
just_states = [s | (s,_) <- states_test]
sfs = map successorFunction just_states

check_sfs = filter check_state (concat sfs)
			where check_state (a,s) = not (checkState s)

states = [State (x,y,z) | x <- [-5..5], y <- [-5..5], z <- [-5..5]]
check_states :: [(State, Bool)]
check_states = filter onlyTrue [(s, checkState s) | s <- states]
			   where onlyTrue (s,b) = b


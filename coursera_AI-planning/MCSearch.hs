module MCSearch where

-- A state of the missionaries/cannibals problem might be represented by an integer triple (x, y, z),
-- where x and y are the number o missionaries and cannibals, respectively, at LEFT bank of the river
-- (the right bank configuration is complementary, so it can be hidden). $x, y \in [0,3]$. z represents
-- the boat: 1 if it is at left bank; 0 otherwise...
data State = State (Int, Int, Int) deriving Show

-- How to compare two states
instance Eq State where
	State (x1,y1,z1) == State (x2,y2,z2) =  x1 == x2 &&
											y1 == y2 &&
											z1 == z2

-- ... In this model, an action might be represented by a pair (dx, dy): dx and dy are the change in
-- the number of missionaries and cannibals, respectively (at the left bank). $dx, dy \in [0,2]$.
-- LR stands for "Left to right (bank) trip" [in this case, dz = -1 (implicit)]; RL is the opposite (dz = +1).
data Action = LR (Int, Int) | RL (Int, Int) deriving Show

-- How to compare two actions
instance Eq Action where
	LR (dx1, dy1) == LR (dx2, dy2) = (dx1 == dx2 && dy1 == dy2)
	RL (dx1, dy1) == RL (dx2, dy2) = (dx1 == dx2 && dy1 == dy2)
	_             == _             = False

-- A node of the tree search			
data Node = Node {
				  state  :: State,        -- Current state
				  action :: Maybe Action -- The action that takes previous state to current state
				  --cost   :: Int,          -- The total cost of applying actions from initial state up to current state
				  --depth  :: Int           -- Depth in the search tree
				 }
			deriving Show
	
-- How to compare two nodes
instance Eq Node where
	n1 == n2 = state n1  == state n2 &&
			   action n1 == action n2

-- Checks whether a given state is valid
checkState :: State -> Bool
checkState (State (x, y, z))
	= if (x >= 0 && x <= 3) && (z == 0 || z == 1)
	  then if (x == 0) || (x == 3)
	  	   then (y >= 0) && (y <= 3)
		   else (x == y)
	  else False
							   
-- The goal state is just "State (0,0,0)" (nobody at the left bank of the river, including boat)
checkGoalState :: State -> Bool
checkGoalState (State (0,0,0)) = True
checkGoalState _               = False

-- Inverts a given action
inverse :: Action -> Action
inverse (LR pair) = RL pair
inverse (RL pair) = LR pair

-- Checks whether a given action is valid (for a given state)
checkAction :: State -> Action -> Bool
checkAction state action = case next_state of
						   Just ns -> checkState ns
						   Nothing -> False
						   where next_state = Just state `apply` action

-- For any given state, the MAXIMUM set of possible actions is this one. Some actions lead to invalid states.
actions :: [Action]
actions = [LR(2,0), LR(1,0), LR(0,2), LR(0,1), LR(1,1), RL(2,0), RL(1,0), RL(0,2), RL(0,1), RL(1,1)]
		   
-- Evolves a given state by a given action, resulting in a (possibly) new state, if action is applicable.
apply :: Maybe State -> Action -> Maybe State
apply (Just state) action = case action of
							LR (dx, dy) -> state `add` (-dx, -dy, -1)
							RL (dx, dy) -> state `add` ( dx,  dy,  1)
							where add (State (x, y, z)) (ddx, ddy, ddz) = if checkState next_state
												  						  then Just next_state			
												  						  else Nothing		
												  						  where next_state = State (x + ddx, y + ddy, z + ddz)
apply Nothing _ = Nothing

-- A fancy syntax for "apply"
(>>>) :: Maybe State -> Action -> Maybe State
(>>>) = apply

-- Successor function: for a given state (represented as a node), generates a list of all reachable states
sf :: Node -> [Node]
sf n = map toNode (filter justStates [(action, Just (state n) `apply` action) | action <- actions])
	   where justStates (_, ms) = case ms of
			 			    	  Just s  -> checkState s
			 			    	  Nothing -> False

--checkNode :: Node -> Bool
--checkNode node = checkState (state node)

reachableStates :: State -> [State]
reachableStates state = map toState (filter justStates [Just state `apply` action | action <- actions])

justStates :: Maybe State -> Bool
justStates (Just s) = True
justStates Nothing  = False

toState :: Maybe State -> State
toState (Just s) = s
--toState Nothing  = ??? -- I know this won't happen, but... 


-- Auxiliar function. TODO: move it into successorFunction
getState :: (Action, Maybe State) -> (Action, State)
getState (a, Just s) = (a, s)
				


-- Query the Node data structure for the state and checks it is the goal (auxiliary function)
checkGoalNode :: Node -> Bool
checkGoalNode node = checkGoalState (state node)
	
-- The plan, as a tree search
data Tree a = Leaf a | Branch (a, [Tree a]) deriving Show

-- Auxiliar
toNode :: (Action, Maybe State) -> Node 
toNode (a, Just s) = Node {state = s, action = Just a}
--toNode (_, Nothing) = ???

-- Performs the search (depth-first or breadth first??)

search :: [Node] -> Tree Node -> Tree Node
search vns (Leaf node) = 
	if checkGoalNode node || length children == 0
	then Leaf node
	else search (vns ++ [node]) (Branch (node, children))
	where children = map Leaf (filter (removeVisitedNodes vns) (sf node))
search vns (Branch (node, nodes)) = Branch (node, map (search vns) nodes)

-- Auxiliar (used to remove from the successor function the action that goes back, ie, which takes the current state and leads to the previous one)
nowayback :: Maybe Action -> (Action, State) -> Bool
nowayback previous_action (action, _) = case previous_action of
										Just a  -> not (action == inverse a)
										Nothing -> True

removeVisitedNodes :: [Node] -> Node -> Bool
removeVisitedNodes nodes node = not (node `elem` nodes)

-- ---------------------------- TESTS -------------
-- Example of node
n0 = Node {
	state = State(3,3,1),
	--parent = Nothing,
	action = Nothing
	--cost = 0,
	--depth = 0
}

-- Manual evolution of the problem
s1 = Just (State(3, 3, 1))
a1 = LR(1, 1)
s2 = s1 `apply` a1           -- Just (2, 2, 0)
a2 = RL(1, 0)
s3 = s2 `apply` a2           -- Just (3, 2, 1)
a3 = LR(1, 1)
s4 = s3 `apply` a3           -- Nothing (invalid state)

result :: Maybe State
--result = s1 `apply` a1 `apply` a2 --`apply` a3
result = s1 >>> a1 >>> a2




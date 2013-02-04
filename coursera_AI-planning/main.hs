-- A state of the missionaries/cannibals problem might be represented by an integer triple (x, y, z),
-- where x and y are the number o missionaries and cannibals, respectively, at LEFT bank of the river
-- (the right bank configuration is complementary, so it can be hidden). $x, y \in [0,3]$. z represents
-- the boat: 1 if it is at left bank; 0 otherwise...
data State = State (Int, Int, Int) deriving Show

-- ... In this model, an action might be represented by a pair (dx, dy): dx and dy are the change in
-- the number of missionaries and cannibals, respectively (at the left bank). $dx, dy \in [0,2]$.
-- LR stands for "Left to right (bank) trip" [in this case, dz = -1 (implicit)]; RL is the opposite (dz = +1).
data Action = LR (Int, Int) | RL (Int, Int) deriving Show

{-
-- An alternative approach to Action
data Action2 = Action (Maybe State -> Maybe State)
LR :: (Int,Int) -> Maybe State -> Maybe State
LR (dx, dy) (Just (State (x, y, z))) = ...

actions2 :: [Action]
actions2 = [Action (LR(1,1)), ...]
-}

-- Checking if two actions are equal
instance Eq Action where
	LR (dx1, dy1) == LR (dx2, dy2) = (dx1 == dx2 && dy1 == dy2)
	RL (dx1, dy1) == RL (dx2, dy2) = (dx1 == dx2 && dy1 == dy2)
	_             == _             = False

-- Checks whether a given state is valid
checkState :: State -> Bool
checkState (State (x, y, z))
	= if x >= 0 && x <= 3
	  then if x == 0 || x == 3
	  	   then y >= 0 && y <= 3
		   else x == y
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

-- Successor function: for a given state, generates a list of all reachable states
successorFunction :: State -> [(Action, State)]
successorFunction state = map getState (filter checkActionState [(action, (Just state) `apply` action) | action <- actions])
						  where checkActionState (a, ms) = case ms of
									 					   Just s  -> checkState s
									 					   Nothing -> False

-- Auxiliar function. TODO: move it into successorFunction
getState :: (Action, Maybe State) -> (Action, State)
getState (a, Just s) = (a, s)
				
-- A node of the tree search			
data Node = Node {
				  state  :: State,        -- Current state
				  --parent :: Maybe Node,   -- Previous state (I think I don't need this because of the Tree structure, below)
				  action :: Maybe Action -- The action that takes previous state to current state
				  --cost   :: Int,          -- The total cost of applying actions from initial state up to current state
				  --depth  :: Int           -- Depth in the search tree
				 }
			deriving Show
	
-- Query the Node data structure for the state and checks it is the goal (auxiliary function)
checkGoalNode :: Node -> Bool
checkGoalNode node = checkGoalState (state node)
	
-- The plan, as a tree search
data Tree a = Leaf a | Branch (a, [Tree a]) deriving Show

-- Auxiliar
toTree :: [Node] -> [Tree Node]
toTree (x:xs) = Leaf (Node {state = state x, action = action x}) : toTree xs
toTree []     = []

toTree2 :: Node -> Tree Node
toTree2 node = Leaf node

-- Auxiliar
toNode :: (Action, State) -> Node 
toNode (a, s) = Node {state = s, action = Just a}

-- Performs the search (depth-first or breadth first??)
search :: Tree Node -> Tree Node
search (Leaf node) = 
	if checkGoalNode node || length children == 0
	then Leaf node
	--else Branch(node, map search (toTree (map toNode children)))
	else search (Branch (node, map (toTree2 . toNode) children))
	where children = filter (nowayback (action node)) (successorFunction (state node))
search (Branch (node, nodes)) = Branch (node, map search nodes)

-- Auxiliar (used to remove from the successor function the action that goes back, ie, which takes the current state and leads to the previous one)
nowayback :: Maybe Action -> (Action, State) -> Bool
nowayback previous_action (action, _) = case previous_action of
										Just a  -> not (action == inverse a)
										Nothing -> True


{- Manual simulation of the algorithm. It seems to work, but... it doesn't!
n0 <- Node {state = State(3,3,1), action = Nothing}
search (Leaf n0)
	checkGoalNode n0 = checkGoalState (state n0)
  					 = checkGoalState (State (3,3,1))
  					 = False
	children = filter (nowayback (action n0)) (successorFunction (state n0))
    		 = filter (nowayback Nothing) (successorFunction (State (3,3,1)))
  		   	 = filter (nowayback Nothing) [(LR(2,0), State(1,3,0)), (LR(1,0), State(2,3,0)), (LR(0,2), State(3,1,0)), (LR(0,1), State(3,2,0)), (LR(1,1), State(2,2,0))]
  		   	 = [(LR(0,2), State(3,1,0)), (LR(0,1), State(3,2,0)), (LR(1,1), State(2,2,0))]  	
	length children = 3
search (Branch (n0, map (toTree2 . toNode) [(LR(0,2), State(3,1,0)), (LR(0,1), State(3,2,0)), (LR(1,1), State(2,2,0))]
search (Branch (n0, map toTree2 [Node {state = State(3,1,0), action = Just LR(0,2)}, Node {state = State(3,2,0), action = Just LR(0,1)}, Node {state = State(2,2,0), action = Just LR(1,1)}]
search (Branch (n0, [Leaf (Node {state = State(3,1,0), action = Just LR(0,2)}),
					 Leaf (Node {state = State(3,2,0), action = Just LR(0,1)}),
					 Leaf (Node {state = State(2,2,0), action = Just LR(1,1)})]))
Branch (n0, map search [Leaf (Node {state = State(3,1,0), action = Just LR(0,2)}),
					    Leaf (Node {state = State(3,2,0), action = Just LR(0,1)}),
					    Leaf (Node {state = State(2,2,0), action = Just LR(1,1)})])
n310_LR02 <- Node {state = State(3,1,0), action = Just LR(0,2)}
n320_LR01 <- Node {state = State(3,2,0), action = Just LR(0,1)}
n220_LR11 <- Node {state = State(2,2,0), action = Just LR(1,1)}
Branch (n0, [search (Leaf n310_LR02),  <-- (1)
			 search (Leaf n320_LR01),  <-- (2)
			 search (Leaf n220_LR11)]) <-- (3)
(1)	search (Leaf n310_LR02)
		ceckGoalNode n310_LR02 = False
		children = filter (nowayback (action n310)) (successorFunction (state n310))
				 = filter (nowayback (Just LR(0,2)) [(RL(0,2), State(3,1,0)), (RL(0,1), State(3,2,1))]
				 = [(RL(0,1), State(3,2,1))]
		length children = 1
	search (Branch (n310_LR02, map (toTree2 . toNode) [(RL(0,1), State(3,2,1))]))
	search (Branch (n310_LR02, map toTree2 [toNode (RL(0,1), State(3,2,1))]))
	search (Branch (n310_LR02, map toTree2 [Node {state = State(3,2,1), action = Just RL(0,1)}]))
	search (Branch (n310_LR02, [toTree2 (Node {state = State(3,2,1), action = Just RL(0,1)})]))
	search (Branch (n310_LR02, [Leaf (Node {state = State(3,2,1), action = Just RL(0,1)})]))
	Branch (n310_LR02, map search [Leaf (Node {state = State(3,2,1), action = Just RL(0,1)})])
	Branch (n310_LR02, [search (Leaf (Node {state = State(3,2,1), action = Just RL(0,1)}))])
	n321_RL01 <- Node {state = State(3,2,1), action = Just RL(0,1)}
	Branch (n310_LR02, [search (Leaf n321_RL01)])
		search (Leaf n321_RL01)
			checkGoalNode n321_RL01 = False
			children = filter (nowayback (action n321_RL01)) (successorFunction (state n321_RL01))
			 		 = filter (nowayback (Just RL(0,1))) [(LR(1,0), State(2,2,0)), (LR(0,2), State(3,0,0)), (LR(0,1), State(3,1,0))]
			 		 = [(LR(1,0), State(2,2,0)), (LR(0,2), State(3,0,0))]
			length children = 2
		search (Branch (n321_RL01, map (toTree2 . toNode) [(LR(1,0), State(2,2,0)), (LR(0,2), State(3,0,0))]))
		search (Branch (n321_RL01, map toTree2 [toNode (LR(1,0), State(2,2,0)), toNode (LR(0,2), State(3,0,0))]))
		n220_LR10 <- Node {state = State(2,2,0), action = LR(1,0)}
		n300_LR02 <- Node {state = State(3,0,0), action = LR(0,2)}
		search (Branch (n321_RL01, map toTree2 [n220_LR10, n300_LR02]))
		search (Branch (n321_RL01, [Leaf n220_LR10, Leaf n300_LR02]))
		Branch (n321_RL01, map search [Leaf n220_LR10, Leaf n300_LR02])
		Branch (n321_RL01, [search (Leaf n220_LR10), search (Leaf n300_LR02)])

Branch(n0, [Branch(n310_LR02, [Branch(n321_RL01, [...])])])
-}

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




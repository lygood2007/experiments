-- A state of the missionaries/cannibals problem
data State = State (Int, Int, Int) deriving Show

-- Checks whether a given state is valid
checkState :: State -> Bool
checkState (State (x, y, z)) = if x >= 0 && x <= 3
							   then if x == 0 || x == 3
									then y >= 0 && y <= 3
									else x == y
							   else False
							   
-- The goal state is just "State (0,0,0)"
checkGoalState :: State -> Bool
checkGoalState (State (0,0,0)) = True
checkGoalState _               = False

-- An action could be a left-to-right trip or opposite
data Action = Left2Right (Int, Int)
			| Right2Left (Int, Int)
			  deriving Show

-- Checks whether a given action is valid (for a given state)
checkAction :: State -> Action -> Bool
checkAction state action = case next_state of
						   Just ns -> checkState ns
						   Nothing -> False
						   where next_state = Just state `apply` action

-- For any given state, the maximum set of possible actions is this one. Some actions lead to invalid states.
actions :: [Action]
actions = [Left2Right(2,0), Left2Right(1,0), Left2Right(0,2), Left2Right(0,1), Left2Right(1,1), Right2Left(2,0), Right2Left(1,0), Right2Left(0,2), Right2Left(0,1), Right2Left(1,1)]
		   
-- evolve state action apply action to state, resulting a new state, if action is applicable
apply :: Maybe State -> Action -> Maybe State
apply (Just state) action = case action of
	Left2Right (dx, dy) -> state `add` (-dx, -dy, -1)
	Right2Left (dx, dy) -> state `add` ( dx,  dy,  1)
	where add (State (x, y, z)) (ddx, ddy, ddz) = if checkState next_state
												  then Just next_state			
												  else Nothing		
												  where next_state = State (x + ddx, y + ddy, z + ddz)
apply Nothing _ = Nothing

-- A fancy syntax for "apply"
(>>>) :: Maybe State -> Action -> Maybe State
(>>>) = apply

-- Successor function: for a given state, generates a list of all reachable states
successorFunction :: State -> [(Action, Maybe State)]
successorFunction state = filter checkActionState [(action, (Just state) `apply` action) | action <- actions]
	where checkActionState (a, ms) = case ms of
									Just s  -> checkState s
									Nothing -> False

				
-- A node of the tree search			
data Node = Node {
				  state  :: State,        -- Current state
				  --parent :: Maybe Node,   -- Previous state [[ACHO QUE NÃO PRECISO DISSO POR CAUSA DO TREE, ABAIXO]]
				  action :: Maybe Action, -- The action that takes previous state to current state
				  cost   :: Int,          -- The total cost of applying actions from initial state up to current state
				  depth  :: Int           -- Depth in the search tree
				 }
			deriving Show
	
checkGoalNode :: Node -> Bool
checkGoalNode node = checkGoalState (state node)
	
-- Given the initial state, returns the goal node
--depthFirstSearch :: State -> Node
--depthFirstSearch initialState = ...?

data Tree a = Leaf a | Branch [Tree a]

n0 = Node {
	state = State(3,3,1),
	--parent = Nothing,
	action = Nothing,
	cost = 0,
	depth = 0
}

{-
se (successorFunction state) tem um ou mais elementos, cria um Branch com a lista do successor function como argumento;
                             tem zero elementos, cria um Leaf e verifica se é um goal node
-}


-- ---------------------------- TESTE -------------
s1 = Just (State(3, 3, 1))
a1 = Left2Right(1, 1)
s2 = s1 `apply` a1           -- Just (2, 2, 0)
a2 = Right2Left(1, 0)
s3 = s2 `apply` a2           -- Just (3, 2, 1)
a3 = Left2Right(1, 1)
s4 = s3 `apply` a3           -- Nothing

result :: Maybe State
--result = s1 `apply` a1 `apply` a2 --`apply` a3
result = s1 >>> a1 >>> a2




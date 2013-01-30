-- A state of the missionaries/cannibals problem
data State = State (Int, Int, Int) deriving Show

-- Checks whether a given state is valid
checkState :: State -> Bool
checkState (State (x, y, z)) = 
	if x >= 0 && x <= 3
	then
		if x == 0 || x == 3
		then y >= 0 && y <= 3
		else x == y
	else False

-- An action could be a left-to-right trip or opposite
data Action = Left2Right (Int, Int) | Right2Left (Int, Int) deriving Show

-- Checks whether a given action is valid (for a given state)
checkAction state action = case nextState of
								Just ns -> checkState ns
								Nothing -> False
						   where nextState = state `apply` action

-- For any given state, the maximum set of possible actions is this one. Some actions lead to invalid state.
actions = [Left2Right(2,0), Left2Right(1,0), Left2Right(0,2), Left2Right(0,1), Left2Right(1,1), Right2Left(2,0), Right2Left(1,0), Right2Left(0,2), Right2Left(0,1), Right2Left(1,1)]
		   
-- evolve state action apply action to state, resulting a new state, if action is applicable
apply :: Maybe State -> Action -> Maybe State
apply (Just state) action = case action of
	Left2Right (dx, dy) -> evolve state (-dx, -dy, -1)
	Right2Left (dx, dy) -> evolve state ( dx,  dy,  1)
apply Nothing _ = Nothing

(>>>) :: Maybe State -> Action -> Maybe State
(>>>) = apply

evolve :: State -> (Int, Int, Int) -> Maybe State
evolve (State (x, y, z)) (dx, dy, dz) =
	if checkState nextState
	then Just nextState			
	else Nothing		
	where nextState = State (x + dx, y + dy, z + dz)

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
result = s1 >>> a1 >>> a2 -- Será que daria para usar Monad aqui??



successorFunction :: State -> [(Action, Maybe State)]
successorFunction state = filter checkActionState [(action, (Just state) `apply` action) | action <- actions]
	where checkActionState (a, ms) = case ms of
									Just s  -> checkState s
									Nothing -> False
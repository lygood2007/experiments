-- A state of the missionaries/cannibals problem
data State = State (Int, Int, Int) deriving Show

-- Checks whether a given state is valid
checkState :: (Maybe State) -> Bool
checkState (Just (State (x, y, z))) = check_xy && (z == 0 || z == 1)
	where check_xy = case x of
		0 -> y >= 0 && y <= 3
		_ -> x >= 0 && x <= 3 && y >= 0 && y <= x
checkState Nothing = False

data Action = Left2Right (Int, Int)
			| Right2Left (Int, Int) deriving Show

			
actions = [Left2Right(2,0), Left2Right(1,0), Left2Right(0,2), Left2Right(0,1), Left2Right(1,1),
		   Right2Left(2,0), Right2Left(1,0), Right2Left(0,2), Right2Left(0,1), Right2Left(1,1)]
		   
-- evolve state action apply action to state, resulting a new state, if action is applicable
evolve :: Maybe State -> Action -> Maybe State
evolve (Just (State (x, y, z))) (Left2Right (dx, dy)) = common (x, y, z) (-dx, -dy, -1)
evolve (Just (State (x, y, z))) (Right2Left (dx, dy)) = common (x, y, z) (dx, dy, 1)
evolve Nothing _ = Nothing

common :: (Int, Int, Int) -> (Int, Int, Int) -> Maybe State
common (x, y, z) (dx, dy, dz) =
	if checkState (Just(nextState))
	then Just nextState			
	else Nothing		
	where nextState = State (x + dx, y + dy, z + dz)
	



-- ---------------------------- TESTE -------------
s1 = Just (State(3, 3, 1))
a1 = Left2Right(1, 1)
s2 = evolve s1 a1           -- Just (2, 2, 0)
a2 = Right2Left(1, 0)
s3 = evolve s2 a2           -- Just (3, 2, 1)
a3 = Left2Right(1, 1)
s4 = evolve s3 a3           -- Nothing

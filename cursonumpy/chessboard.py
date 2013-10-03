import numpy as np
from time import time

def chessboard_1(shape, size):

	f = np.zeros(shape, dtype='uint16')

	for y in range(0, shape[0], size):
		start = size if ((y / size) % 2 == 0) else 0
		for x in range(start, shape[1], 2*size):
			f[y:y+size,x:x+size] = 1

	return f

def chessboard_2(shape, size):

	# The first row of the chessboard
	r1 = np.zeros((size, shape[1]), dtype='uint16')
	for x in range(size, shape[1], 2 * size):
		r1[:,x:x+size] = 1

	# The second row of the chessboard
	r2 = np.roll(r1, size)

	# First and second rows together
	base_row = np.vstack((r1, r2))

	# Resize to desired dimensions
	return np.resize(base_row, shape)

def chessboard_3(shape, size):

	r,c = np.indices(shape, dtype='uint16')
	return (r/size + c/size) & 1 # &1 is equivalente to %2 (just for 2)


H,W = 2000,2000
S = 10
funlist = (chessboard_1, chessboard_1, chessboard_1)
fundesc = ("chessboard_1:", "chessboard_1:", "chessboard_1:")
i = 0
for fun in funlist:
	t1,f,t2 = time(), fun((H,W), S), time()
	print fundesc[i], "\t", t2-t1," s"
	i = i+1

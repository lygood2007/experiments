import numpy as np

def chessboard_1(shape, size):
	dimensions = tuple(x * size for x in shape)
	f = np.zeros(dimensions, dtype='uint16')

	for y in range(0, dimensions[0], size):
		start = 0 if ((y / size) % 2 == 0) else size
		for x in range(start, dimensions[1], 2*size):
			f[y:y+size,x:x+size] = 1

	return f

def chessboard_2(shape, size):

	# Dimensions of the chessboard image
	dimensions = tuple(i * size for i in shape)

	# The first row of the chessboard
	r1 = np.zeros((size, dimensions[1]), dtype='uint16')
	for x in range(0, dimensions[1], 2 * size):
		r1[:,x:x+size] = 1

	# The second row of the chessboard
	r2 = np.roll(r1, size)

	# First and second rows together
	base_row = np.vstack((r1, r2))

	# Resize to desired dimensions
	return np.resize(base_row, dimensions)

def chessboard_3(shape, size):

	dimensions = tuple(i * size for i in shape)
	r,c = np.indices(dimensions, dtype='uint16')

	return (r/size + c/size) & 1

print chessboard_1((8,8), 2)
print "----------------------------------"
print chessboard_2((8,8), 2)
print "----------------------------------"
print chessboard_3((8,8), 2)

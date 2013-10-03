import numpy as np

def cardioid(center, a, meshgrid):
    r,c = meshgrid
    r -= center[1]
    c -= center[0]
    theta = np.arctan2(r,c)
    rcard = 2*a*(1-np.cos(theta))
    R = np.sqrt(r**2 + c**2)
    cond = R < rcard
    #z = np.abs(R) * (1 if R < rcard else 0)
    z = np.select([R <= rcard], [np.abs(R)])
    return z

meshgrid = np.meshgrid(np.arange(0,100),np.arange(0,100))
f = cardioid((50,50), 10, meshgrid)

print f

# coding=UTF-8
import numpy as np

def kk_rank(f, opt):    
    #import numpy as np
    
    if   opt == "min": function = np.min
    elif opt == "max": function = np.max
    else:              function = np.median
    
    H,W = f.shape
    
    layers = np.empty((9,H+2,W+2), dtype="uint8")
    c = 0
    for i in range(0,3):
        for j in range(0,3):
            layers[c,i:H+i,j:W+j] = f
            c += 1
    
    ans = np.empty((H+2,W+2), dtype="uint8")
    ans[1,   1  ] = np.round(function(layers[[0,1,  3,4        ]], axis=0))[1,   1  ] # top-left-hand corner
    ans[2:H, 1  ] = np.round(function(layers[[0,1,  3,4,  6,7  ]], axis=0))[2:H, 1  ] # left line
    ans[  H, 1  ] = np.round(function(layers[[      3,4,  6,7  ]], axis=0))[  H, 1  ] # bottom-left-hand corner
    ans[  H, 2:W] = np.round(function(layers[[      3,4,5,6,7,8]], axis=0))[  H, 2:W] # bottom line
    ans[  H,   W] = np.round(function(layers[[        4,5,  7,8]], axis=0))[  H,   W] # bottom-right-hand corner
    ans[2:H,   W] = np.round(function(layers[[  1,2,  4,5,  7,8]], axis=0))[2:H,   W] # right line
    ans[1,     W] = np.round(function(layers[[  1,2,  4,5      ]], axis=0))[1,     W] # top-right-hand corner
    ans[1,   2:W] = np.round(function(layers[[0,1,2,3,4,5      ]], axis=0))[1,   2:W] # top line
    ans[2:H, 2:W] = np.round(function(layers[[0,1,2,3,4,5,6,7,8]], axis=0))[2:H, 2:W] # middle
        
    return ans[1:H+1,1:W+1]


f = np.random.rand(3,3)
out = kk_rank(f, "max")
print "Input: ", f
print "Output: ", out

# coding=UTF-8
import numpy as np

def kk_rank(f, opt):    
    #import numpy as np
    
    if   opt == "min": function = np.min
    elif opt == "max": function = np.max
    else:              function = np.median
    
    H,W = f.shape
    
    tmp = np.zeros((9,H+2,W+2))
    c = 0
    for i in range(-1,2):
        for j in range(-1,2):
            x = 1 + i
            y = 1 + j
            tmp[c,x:H+x,y:W+y] = f
            c += 1
    
    result = np.empty((9,H+2,W+2))
        
    result[0] = function(tmp[[0,1,  3,4        ]], axis=0)
    result[1] = function(tmp[[0,1,  3,4,  6,7  ]], axis=0)
    result[2] = function(tmp[[      3,4,  6,7  ]], axis=0)
    result[3] = function(tmp[[      3,4,5,6,7,8]], axis=0)
    result[4] = function(tmp[[        4,5,  7,8]], axis=0)
    result[5] = function(tmp[[  1,2,  4,5,  7,8]], axis=0)
    result[6] = function(tmp[[  1,2,  4,5      ]], axis=0)
    result[7] = function(tmp[[0,1,2,3,4,5      ]], axis=0)
    result[8] = function(tmp[[0,1,2,3,4,5,6,7,8]], axis=0)
        
    #print result
    ans = np.zeros((H+2,W+2))
    ans[1,1]     = result[0,1,1]
    ans[2:H,1]   = result[1,2:H,1]
    ans[H,1]     = result[2,H,1]
    ans[H,2:W]   = result[3,H,2:W]
    ans[H,W]     = result[4,H,W]
    ans[2:H,W]   = result[5,2:H,W]
    ans[1,W]     = result[6,1,W]
    ans[1,2:W]   = result[7,1,2:W]
    ans[2:H,2:W] = result[8,2:H,2:W]
        
    return ans[1:H+1,1:W+1]

f = np.random.rand(2,2)
out = kk_rank(f, "median")
print "Input: ", f
print "Output: ", out

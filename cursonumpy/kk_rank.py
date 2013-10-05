# coding=UTF-8
import numpy as np

def kk_rank(f, opt):    
    #import numpy as np
    
    H,W = f.shape
    
    if opt == "min":
        padding = np.max(f) + 1
    else:
        if opt == "max":
            padding = np.min(f) - 1
        else:
            padding = np.min(f) - 1
    
    tmp = np.empty((9,H+2,W+2))    
    
    c = 0    
    for i in range(0,3):
        for j in range(0,3):
            
            # Fill borders with appropriate value
            tmp[c,0:i,:]     = padding
            tmp[c,H+i:H+2,:] = padding
            tmp[c,:,0:j]     = padding
            tmp[c,:,W+j:W+2] = padding
            
            # Fill core with displaced f
            tmp[c,i:H+i,j:W+j] = f
            
            c += 1
            
    if (opt == "min"):
        ans = np.amin(tmp, axis=0)
    else:
        if (opt == "max"):
            ans = np.amax(tmp, axis=0)
        else:
            ans = np.median(tmp, axis=0)
            
    return ans[1:H+1,1:W+1]

f = np.random.rand(2,2)
print "Input: ", f
print "Output: ", kk_rank(f, "min")

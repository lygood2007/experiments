# http://adessowiki.fee.unicamp.br/adesso-1/wiki/cursopynumpy/activity_ivanpagnossin_1_qc/view/
import numpy as np

def qc(isImg):
	H,W = 300,600
	if not isImg:
		W /= 50
		H /= 50

	f = np.zeros((H,W))
	f[:,:W/2] = 64
	f[:,W/2:] = 192
	h = H/3
	w = W/6
	f[h:2*h,w:2*w] = 128
	f[h:2*h,4*w:5*w] = 128

	return f

print qc(False)

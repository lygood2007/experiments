# coding=UTF-8
import numpy as np

# A função nn_clip normaliza a imagem f (níveis de cinza) no intervalo
# to_range = [mínimo, máximo]. Na imagem original, os pixels com nível
# de cinza inferiores ao percentil associado a perc são mapeados para o
# valor mínimo desejado (na imagem final); pixels com nível de cinza
# superiores ao percentil associado a 100-perc são mapeados para o máximo.
def nn_clip(f, to_range, perc):
    #import numpy as np
    
    low,high = to_range
    
    p1 = np.round(np.percentile(f, perc))
    p2 = np.round(np.percentile(f, 100 - perc))    
    
    return np.select([f < p1, f > p2], [low, high], low + (high - low)/(p2 - p1) * (f - p1))

# Testando a função nn_clip
f = np.array([0, 2, 3, 3, 4, 4, 4, 5, 5, 7])
expected = "[0. 0. 85. 85. 170. 170. 170. 255. 255. 255.]"
print expected, " = ", nn_clip(f, (0,255), 10)
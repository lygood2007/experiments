v0 = [1/2 1/2 1/2 1/2]';
v1 = [1/2 1/2 -1/2 -1/2]';
v2 = [1/2 -1/2 1/2 -1/2]';
v3 = [1/2 -1/2 -1/2 1/2]'; % My guess

dot(v0,v1)
dot(v0,v2)
dot(v0,v3)
dot(v1,v2)
dot(v1,v3)
dot(v2,v3)

V = [v0 v1 v2 v3];

E = gs_orthonormalization(V);
N = 64;
n=[0:N-1];
y1 = 2 * cos(2*pi*n/16);
y2 = 1/2 * sin(2*pi*n/8);
y3 = ones(N)(1,:);
x  = y1 + y2 + y3;
%energy1 = dot(x, conj(x));
energy1 = norm(x)^2;

plot(n,y1,n,y2,n,y3);

Y1 = fft(y1);
Y2 = fft(y2);
Y3 = fft(y3);
X  = fft(x);
%energy2 = dot(X, conj(X)) / N^2;
energy2 = norm(X)^2/N;

k=n/N;
%plot(k,Y1,k,Y2,k,Y3);



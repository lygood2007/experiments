% Sampling on 'time' domain
N = 128;
t = (1:N)'; % obs.: this is unnecessary. Instead, we need only keep track of the indexes.

% ------------------------------
% Input signal ('time' domain)
x = [ones(1,64) zeros(1,64)]'; % ATENTION to length
%plot(t, x);

% ------------------------------
% Calculates the Discrete Fourier Transform (DFT) transformation matrix
a = 0:(N-1);
A = repmat(a',1,N);
B = repmat(a, N,1);

% DFT transformation matrix. Notice that it is independent of x
WN = exp(-i * (2*pi/N) * A .* B); 

% ------------------------------
% DFT of x ('frequency' domain)
k = t ./ N;
X = WN * x;
%plot(k, X,'rx-');

% ------------------------------
% Inverse DFT: back to 'time' domain
%y = 1/N * conj(WN) * X; % equals x

% ------------------------------
% Real part
magnitude = abs(X);
%plot(k, magnitude);

% ------------------------------
phase = angle(X);
%plot(k, phase);
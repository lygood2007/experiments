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
%k = t ./ N; % Normalized frequencies: (0,1]
%X = WN * x;
%plot(k, X,'rx-');

% ------------------------------
% Inverse DFT: back to 'time' domain
% The real() function is needed to avoid numerical
% errors that introduce imaginary components on the signal.
%y = real(1/N * conj(WN) * X); % equals x
%plot(t, y);

% ------------------------------
% Real part
%magnitude = abs(X);
%plot(k, magnitude);

% ------------------------------
% Phase computation is very sensitive if phase is close to pi:
% the graph of phase below shows spikes that doesn't exist; instead,
% the expected result is a increasing phase, from -pi/2 to +pi/2 for
% even samples and zero for odd samples.
%phase = angle(X);
%plot(k, phase); 

% ------------------------------
% Change the dimension d of the basis, ie, the amount of orthogonal
% sinusoidal waves used to decompose the input signal, in order to
% see how it approaches x as d approaches N.
d = 50; % n <= N
Xd = WN(1:d,:) * x;
yd = real(1/N * conj(WN(:,1:d)) * Xd);
plot(t, yd);
% ------------------------------
% Input signal ('time' domain) composed of three tones
N = 500;
x1 = sin(2*pi* 40*(1:N)/N); % tone 1
x2 = sin(2*pi* 80*(1:N)/N); % tone 2
x3 = sin(2*pi*160*(1:N)/N); % tone 3
x = [x1 zeros(1,N) x2 zeros(1,N) x3]';
%wavplay(x);
N = length(x);

% ------------------------------
% Calculates the Discrete Fourier Transform (DFT) transformation matrix
a = 0:(N-1);
A = repmat(a',1,N);
B = repmat(a, N,1);

% DFT transformation matrix. Notice that it is independent of x
WN = exp(-i * (2*pi/N) * A .* B); 

% ------------------------------
% x-axis: t (time domain) or k (frequency domain)
t = (1:N);
k = t ./ N; % Normalized frequencies: (0,1]

% ------------------------------
% DFT of x
%X = WN * x;
%plot(k, abs(X));

% ------------------------------
% DFT in a reduced base:
% WNreduced is a cut-off of the original DFT transformation matrix. That
% means it represents a low-dimension DFT. As a result, the three tones
% (peaks) where merged into two, a process known as "frequency mixing".
WNreduced = [WN(1:600,:); WN(N-600:N,:)];
inverseWNreduced = conj([WN(:,1:600) WN(:,N-600:N)]);
Xapprox = WNreduced * x;
xapprox = 1/N * real(inverseWNreduced * Xapprox);

X = WN * xapprox;
%plot(k, abs(X));
%wavplay(xapprox);


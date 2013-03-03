% Input signal ('time' domain)
x = [1 2 3 4]';
N = length(x);

% ------------------------------
% Calculates the Discrete Fourier Transform (DFT) transformation matrix
a = 0:(N-1);
A = repmat(a',1,N);
B = repmat(a, N,1);

% DFT transformation matrix. Notice that it is independent of x
WN = exp(-i * (2*pi/N) * A .* B); 

% ------------------------------
% DFT of x ('frequency' domain)
X = WN * x;

% ------------------------------
% Back to 'time' domain
y = 1/N * conj(WN) * X; % equals x


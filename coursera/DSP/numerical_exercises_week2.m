% ------------------------------
% Input signal ('time' domain) composed of three tones
x1 = sin(2*pi* 40*(1:4000)/1000); % tone 1
x2 = sin(2*pi* 50*(1:4000)/1000); % tone 2
x = x1 + x2;

N = length(x); % Full length of the signal
M = 50;        % Number of samples for resampling (change to 4000 and compare)

% ------------------------------
% Fast Fourier Transform (FFT)
% Notice: with M = 50 it isn't possible to see the two peaks (tones).
X = fft(x, M);         % FFT of the resampled signal: length M
%plot((1:M)/M, abs(X));

% ------------------------------
% Inverse FFT
% Notice: increase the signal length here has no effect on distinguish the two peaks.
% That is, padding the signal with zeros does not recover any information, as expected.
x = ifft(X);           % x now has length M
X = fft(x, N);         % Because N > M, pad M-lneght signal with zeros up to N samples
plot((1:N)/N, abs(X));
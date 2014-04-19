package chapter_3;

import org.opensourcephysics.numerics.ODE;

public class FallingParticleODE implements ODE {

	final static double g = 9.8; // m/s/s
	double[] state = new double[3];
	
	public FallingParticleODE (double y, double v) {
		// Initial condition
		state[0] = y;
		state[1] = v;
		state[2] = 0; // t = 0
	}

	@Override
	public void getRate(double[] state, double[] rate) {
		rate[0] = state[1]; // dy/dt = v
		rate[1] = -g; // dv/dt = -g
		rate[2] = 1; // dt/dt = 1
	}

	@Override
	public double[] getState() {
		return state;
	}
}

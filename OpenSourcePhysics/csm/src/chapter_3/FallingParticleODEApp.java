package chapter_3;

import org.opensourcephysics.controls.AbstractCalculation;
import org.opensourcephysics.controls.CalculationControl;
import org.opensourcephysics.numerics.Euler;
import org.opensourcephysics.numerics.EulerRichardson;
import org.opensourcephysics.numerics.ODESolver;

public class FallingParticleODEApp extends AbstractCalculation {

	@Override
	public void calculate() {
		// Gets initial condition
		double y0 = control.getDouble("Initial y");
		double v0 = control.getDouble("Initial v");
		
		// Creates ball with initial condition
		FallingParticleODE ball = new FallingParticleODE(y0, v0);
		
		// ODE solver algorithm
		//ODESolver solver = new Euler(ball);
		ODESolver solver = new EulerRichardson(ball);
		solver.setStepSize(control.getDouble("dt"));
		while(ball.state[0] > 0) {
			solver.step();
		}
		control.println("Final time = " + ball.state[2]);
		control.println(" y = " + ball.state[0] + " v = " + ball.state[1]);
	}
	
	public void reset () {
		control.setValue("Initial y", 10);
		control.setValue("Initial v", 0);
		control.setValue("dt", 0.01);
	}
	
	public static void main (String[] args) {
		CalculationControl.createApp(new FallingParticleODEApp());
	}
}
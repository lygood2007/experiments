package chapter_3;

import java.awt.Color;
import java.awt.Graphics;

import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.numerics.EulerRichardson;
import org.opensourcephysics.numerics.ODE;

public class Projectile implements Drawable, ODE {
	
	static final double g = 9.8; // m/s/s
	double[] state = new double[5]; // [x, vx, y, vy, t]
	int pixRadius = 6;
	EulerRichardson odeSolver = new EulerRichardson(this);

	public void setStepSize(double dt) {
		odeSolver.setStepSize(dt);
	}
	
	public void step () {
		odeSolver.step();
	}
	
	public void setState (double x, double vx, double y, double vy) {
		state[0] = x;
		state[1] = vx;
		state[2] = y;
		state[3] = vy;
		state[4] = 0; // t = 0
	}
	
	@Override
	public void getRate(double[] state, double[] rate) {
		rate[0] = state[1]; // dx/dt = vx
		rate[1] = 0; // dvx/dt = 0
		rate[2] = state[3]; // dy/dt = vy
		rate[3] = -g; // dvy/dt = -g
		rate[4] = 1; // dt/dt = 1
	}

	@Override
	public double[] getState() {
		return state;
	}

	@Override
	public void draw(DrawingPanel drawingPanel, Graphics g) {
		
		int xpix = drawingPanel.xToPix(state[0]);
		int ypix = drawingPanel.yToPix(state[2]);
		g.setColor(Color.red);
		g.fillOval(xpix-pixRadius, ypix-pixRadius, 2*pixRadius, 2*pixRadius);
		g.setColor(Color.green);
		
		int xmin = drawingPanel.xToPix(-100);
		int xmax = drawingPanel.xToPix(100);
		int y0 = drawingPanel.yToPix(0);		
		g.drawLine(xmin, y0, xmax, y0);

	}
}

package chapter_3;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;

public class ProjectileApp extends AbstractSimulation {

	PlotFrame plotFrame = new PlotFrame("Time", "x,y", "Position vs. time");
	Projectile projectile = new Projectile();
	PlotFrame animationFrame = new PlotFrame("x", "y", "Trajectory");
	
	public ProjectileApp () {
		animationFrame.addDrawable(projectile);
		plotFrame.setXYColumnNames(0, "t", "x");
		plotFrame.setXYColumnNames(0, "t", "y");
	}
	
	public void initialize () {
		double dt = control.getDouble("dt");
		double x = control.getDouble("Initial x");
		double vx = control.getDouble("Initial vx");
		double y = control.getDouble("Initial y");
		double vy = control.getDouble("Initial vy");
		projectile.setState(x, vx, y,vy);
		projectile.setStepSize(dt);
		double size = (vx*vx+vy*vy)/10;
		animationFrame.setPreferredMinMax(-1, size, -1, size);
	}
	
	@Override
	protected void doStep() {
		plotFrame.append(0, projectile.state[4], projectile.state[0]);
		plotFrame.append(1, projectile.state[4], projectile.state[2]);
		animationFrame.append(0, projectile.state[0], projectile.state[2]);
		projectile.step();
	}
	
	public void reset () {
		control.setValue("Initial x", 0);
		control.setValue("Initial vx", 10);
		control.setValue("Initial y", 0);
		control.setValue("Initial vy", 10);
		control.setValue("dt", 0.01);
		enableStepsPerDisplay(true);
	}
	
	public static void main (String[] args) {
		SimulationControl.createApp(new ProjectileApp());
	}

}

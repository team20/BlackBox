package org.usfirst.frc.team20.robot;

import com.ctre.CANTalon;

public class Drivetrain implements Loggable{
	CANTalon right, left;

	public Drivetrain(){
		right = new CANTalon(1);
		left = new CANTalon(9);
	}

	@Override
	public String log() {
		return "\n\tRight: " + right.getOutputCurrent() + "\n\tLeft: " + left.getOutputCurrent();
	}
}

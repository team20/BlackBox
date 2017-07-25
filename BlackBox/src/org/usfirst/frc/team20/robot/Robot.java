package org.usfirst.frc.team20.robot;

import java.io.IOException;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Logger logger = new Logger();
	Drivetrain drive = new Drivetrain();
	boolean beenEnabled = false, socket = false;

	@Override
	public void robotInit() {
		logger.register(drive);
		logger.startSocket(); socket = true;
	}

	public void disabledInit(){
		if(beenEnabled){
			try {
				logger.closeSocket();
				socket = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void autonomousInit() {
		beenEnabled = true;
		if(!socket){
			logger.startSocket(); socket = true;
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		logger.log();
	}

	/**
	 * This function is called periodically during operator control
	 */

	@Override
	public void teleopInit() {
		beenEnabled = true;
		if(!socket){
			logger.startSocket(); socket = true;
		}
	}

	@Override
	public void teleopPeriodic() {
		logger.log();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}
}

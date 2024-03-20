// please excuse my variables ;^)
package frc.robot.subsystems;

import com.ctre.phoenix.hardware.CANcoder;
import com.ctre.phoenix.sensors.CANCoder;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.RelativeEncoder;
//import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.kinematics.SwerveModuleState;

@SuppressWarnings("")
public class PhoenixModule {
		
	// DRIVING elements of the swerve module
	private CANSparkMax m_driveMotor;
	private RelativeEncoder m_driveIntegratedEncoder;

	// STEERING elements of the swerve module
	private CANSparkMax m_steeringMotor;
	private CANCoder m_steeringCANCoder;
	private RelativeEncoder m_steeringIntegratedEncoder;


	/**
     * Method to instantiate a swerve module with motors and encoders.
     *
     * @param driveMotorID The ID found for each drive motor for the CANSparkMax.
     * @param steeringMotorID The ID found for each steering motor for the CANSparkMax.
	 * 
     * @param steeringCANcoderID The ID found for each angled absolute encoder for the swerve module.
     */
	public PhoenixModule(
		int driveMotorID,
		int steeringMotorID,

		int steeringCANcoderID
	){
			// one thing, kind of confused on the integratedEncoders for the driveMotors and
			// steering motors. I'm assuming they're important so I'll provide code here for them.
		//CANcoder
		m_steeringCANcoder = new CANcoder(steeringCANcoderID);

		// DRIVEMOTORS AND ENCODER
		m_driveMotor = new CANSparkMax(driveMotorID, MotorType.kBrushless);
		m_driveIntegratedEncoder = m_driveMotor.getEncoder();

		// STEERING AND INTEGRATED ENCODER
		m_steeringMotor = new CANSparkMax(steeringMotorID, MotorType.kBrushless);
		m_steeringIntegratedEncoder = m_steeringMotor.getEncoder();
		
	}


	public void setDesiredState(SwerveModuleState desiredState){

	}
}
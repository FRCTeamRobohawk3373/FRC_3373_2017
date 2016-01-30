package org.usfirst.frc.team3373.robot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
* @author Drew Marino  @author Joey Dyer
*/
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import java.io.IOException;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.*;
import edu.wpi.first.wpilibj.CANTalon;
import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.image.*;

public class VisionSystem {	
	
	AxisCamera visionCamera = new AxisCamera("10.33.73.85");
	Image testImage = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
	Image binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	Image binaryFrame2 = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	//creates unfiltered images
	
	public void getVisionImage(){	
		try {
        	HSLImage image = visionCamera.getImage();
        	System.out.println("got Image");
        	image.write("/home/lvuser/image.png");
        	}catch (Exception e){
        		System.out.println("exception occured:" + e);
        	}
	}
	
	public int filterVisionImage(){
		NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
		NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
		NIVision.Range GOAL_HUE = new NIVision.Range(0,255);
		NIVision.Range GOAL_SAT = new NIVision.Range(0,255);
		NIVision.Range GOAL_VAL = new NIVision.Range(0,255);
		//establishes the criteria for filtering
		NIVision.imaqColorThreshold(binaryFrame2, testImage, 255, NIVision.ColorMode.HSV, GOAL_HUE, GOAL_SAT, GOAL_VAL);
		int numThingsDetected = NIVision.imaqCountParticles(binaryFrame, 1);
		return numThingsDetected;
		//make the codes work drew.
		//okay
	}	
	/*
	//declaring variables
	String cameraIP;
	int session;
	Image testImage;
	Image binaryFrame;
	Image binaryFrame2;
	double AREA_MINIMUM = 0.5;
	
	
	 
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
	
	public void Camera(String cameraIP){
		//creating blank images to overwrite with our data
	testImage = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
	binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	binaryFrame2 = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	//criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
	this.cameraIP = cameraIP; 
	
	//connecting to camera, I think.
	session = NIVision.IMAQdxOpenCamera(cameraIP,IMAQdxCameraControlMode.CameraControlModeController);
	NIVision.IMAQdxConfigureGrab(session);
	}
	public int Filtering(String cameraIP){
		//this method actually starts some filtration, I hope
		NIVision.IMAQdxStartAcquisition(session);
		NIVision.IMAQdxGrab(session, testImage, 1);
		NIVision.Range GOAL_HUE = new NIVision.Range(0,255);
		NIVision.Range GOAL_SAT = new NIVision.Range(0,255);
		NIVision.Range GOAL_VAL = new NIVision.Range(0,255);
		NIVision.imaqColorThreshold(binaryFrame2, testImage, 255, NIVision.ColorMode.HSV, GOAL_HUE, GOAL_SAT, GOAL_VAL);
		//detecting number of green things
		int numThingsDetected = NIVision.imaqCountParticles(binaryFrame, 1);
		CameraServer.getInstance().setImage(binaryFrame2);
		SmartDashboard.putNumber("VisionCodeVarParticles: ", numThingsDetected);
		return numThingsDetected;
		*/
	}

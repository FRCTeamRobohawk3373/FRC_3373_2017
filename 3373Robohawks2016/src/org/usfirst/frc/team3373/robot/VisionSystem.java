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
import edu.wpi.first.wpilibj.image.*;

public class VisionSystem {	


	//Images
	Image frame;
	Image binaryFrame;
	int imaqError;

	//Constants
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(100, 155);	//Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(0, 255);	//Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(0, 255);	//Default value range for yellow tote
	double AREA_MINIMUM = 0.15; //Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
	double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
	double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
	double VIEW_ANGLE = 49.4; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	AxisCamera visionCamera = new AxisCamera("10.33.73.30");
	
	
	
	//creates unfiltered images
	
	
<<<<<<< HEAD
	public int filterVisionImage(){
		NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
=======
	public void getVisionImage(){  //this method calls one image and then continually replaces it
		HSLImage image;
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
		try {
        	image = visionCamera.getImage();
        	System.out.println("got Image");
        	image.write("/home/lvuser/image.jpg");
        	}catch (Exception e){
        		System.out.println("exception occured: " + e);
        	}
		NIVision.imaqReadFile(frame, "/home/lvuser/image.jpg");

		//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
		TOTE_HUE_RANGE.minValue = 95;
		TOTE_HUE_RANGE.maxValue = 161;
		TOTE_SAT_RANGE.minValue = 215;
		TOTE_SAT_RANGE.maxValue = 255;
		TOTE_VAL_RANGE.minValue = 215;
		TOTE_VAL_RANGE.maxValue = 255;

		//Threshold the image looking for yellow (tote color)
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		System.out.println("Masked particles" + numParticles);

		//Send masked image to dashboard to assist in tweaking mask.
		//CameraServer.getInstance().setImage(binaryFrame);

		//filter out small particles
		NIVision.imaqWriteJPEGFile(binaryFrame, "/home/lvuser/filterImage.jpg", 255, null);
		float areaMin = (float) 0.15;
		criteria[0].lower = areaMin;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		System.out.println(numParticles + "Particles!!!");

		
	}


		
	//public int filterVisionImage(){
		/*NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
>>>>>>> branch 'master' of https://github.com/FRCTeamRobohawk3373/FRC_3373_2016
		NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);

		//establishes the criteria for filtering
		NIVision.imaqColorThreshold(binaryFrame2, testImage, 255, NIVision.ColorMode.HSV, GOAL_HUE, GOAL_SAT, GOAL_VAL);
<<<<<<< HEAD
		int numThingsDetected = NIVision.imaqCountParticles(binaryFrame, 1);
		return numThingsDetected;
=======
		int numParticlesDetected = NIVision.imaqCountParticles(binaryFrame, 1);
>>>>>>> branch 'master' of https://github.com/FRCTeamRobohawk3373/FRC_3373_2016
		//make the codes work drew.
<<<<<<< HEAD
		//okay
	}	
	/*
=======
		System.out.println(numParticlesDetected);
		return numParticlesDetected;
	*/
	
>>>>>>> branch 'master' of https://github.com/FRCTeamRobohawk3373/FRC_3373_2016
	//declaring variables
	/*
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
	}
	*/}

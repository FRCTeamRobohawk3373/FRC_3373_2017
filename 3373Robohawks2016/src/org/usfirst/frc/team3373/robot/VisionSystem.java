package org.usfirst.frc.team3373.robot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.*;

/*
* @author Drew Marino
* @author Joey Dyer
*/
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.*;

public class VisionSystem {
	
	//declaring variables
	static String cameraIP;
	static int session;
	static Image testImage;
	static Image binaryFrame;
	static Image binaryFrame2;
	public static void Camera(String cameraIP){
		//creating blank images to overwrite with our data
	testImage = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
	binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	binaryFrame2 = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	//this.cameraIP = cameraIP;     //no goddamn clue what this does
	
	//connecting to camera, I think.
	session = NIVision.IMAQdxOpenCamera(cameraIP,IMAQdxCameraControlMode.CameraControlModeController);
	NIVision.IMAQdxConfigureGrab(session);
	
	}
	public static void Filtering(String cameraIP){
		//this method actually starts some filtration, I hope
		NIVision.IMAQdxStartAcquisition(session);
		NIVision.IMAQdxGrab(session, testImage, 1);
		NIVision.Range GOAL_HUE = new NIVision.Range(110,150);
		NIVision.Range GOAL_SAT = new NIVision.Range(0,255);
		NIVision.Range GOAL_VAL = new NIVision.Range(0,255);
		NIVision.imaqColorThreshold(binaryFrame2, testImage, 255, NIVision.ColorMode.HSV, GOAL_HUE, GOAL_SAT, GOAL_VAL);
		//detecting number of green things
		int numThingsDetected = NIVision.imaqCountParticles(testImage, 1);
		
		
	}
}

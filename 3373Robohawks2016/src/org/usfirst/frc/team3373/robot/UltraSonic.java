package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;

public class UltraSonic {
	LookupTable lookUp;
	AnalogInput analogSensor;
	AnalogInput analogSensor2;
	AnalogInput analogSensor3;
	DigitalOutput digitalTrigger;
	boolean isSingleSensor = false;
	double distance = 0;
	double[] calibrationDistances;
	double[] calibrationVoltages;
	

	public UltraSonic(int analogPort1, int analogPort2, int digitalPort) {
		analogSensor = new AnalogInput(analogPort1);
		analogSensor2 = new AnalogInput(analogPort2);
		//analogSensor3 = new AnalogInput(analogPort3);
		digitalTrigger = new DigitalOutput(digitalPort);
		calibrationDistances = new double[]{12,18,24,30,36,42};
		calibrationVoltages = new double[]{0.319,0.513, 0.618, 0.739, 0.889, 1.021};
		lookUp = new LookupTable();
	}

	public UltraSonic(int analogPort) {
		isSingleSensor = true;
		analogSensor = new AnalogInput(analogPort);
		lookUp = new LookupTable();
/*		calibrationDistances = new double[]{12,18,24,30,36,42};
		calibrationVoltages = new double[]{0.319,0.513, 0.618, 0.739, 0.889, 1.021};*/
		calibrationDistances = new double[]{12,17,22,34.5,46,51, 71, 101};
		calibrationVoltages = new double[]{0.28,0.40, 0.56, 0.835, 1.12, 1.24, 1.71, 2.45};
	}

	public double getDistance() {
		if(isSingleSensor)
		return lookUp.lookUpValue(analogSensor.getAverageVoltage(), calibrationVoltages, calibrationDistances);
		else{
			digitalTrigger.pulse(.01);
			System.out.println("Sensor 1: " + lookUp.lookUpValue(analogSensor.getAverageVoltage(), calibrationVoltages, calibrationDistances) );
			System.out.println("Sensor 2: " + lookUp.lookUpValue(analogSensor2.getAverageVoltage(), calibrationVoltages, calibrationDistances) );
		//	System.out.println("Sensor 3: " +  lookUp.lookUpValue(analogSensor3.getAverageVoltage(), calibrationVoltages, calibrationDistances));
			double averageVoltage = (analogSensor.getAverageVoltage()  + analogSensor2.getAverageVoltage()) /2;
			if( lookUp.lookUpValue(averageVoltage, calibrationVoltages, calibrationDistances) == calibrationDistances[calibrationDistances.length -1])
				return calibrationDistances[0];
			else
				return lookUp.lookUpValue(averageVoltage, calibrationVoltages, calibrationDistances);
		}
		/*double distance2;
		if (isSingleSensor) {
			distance2 = (43.8703 * analogSensor.getVoltage() - 2.907);
			if (distance2 < 200)
				// checks to see if data is valid (205 inches is the max the
				// sensor can read)
				distance = distance2;
		} else {
			digitalTrigger.pulse(.01);
			double averageVoltage = (analogSensor.getVoltage() + analogSensor2.getVoltage()) / 2;
			distance2 = 43.8073 * averageVoltage - 2.907;
			if(distance2 < 200)
				distance = distance2;
		}
		return distance;
		*/
	}
	public void calibrate(){
		if(isSingleSensor)
			System.out.println("UltraSonic Sensor" + analogSensor.getAverageVoltage());
		else{
			System.out.println("UltraSonic Sensor 1: " + analogSensor.getAverageVoltage());
			System.out.println("UltraSonic Sensor 2: " + analogSensor2.getAverageVoltage());
			System.out.println("UltraSonic Sensor 3: " + analogSensor3.getAverageVoltage());

		}
	}
}

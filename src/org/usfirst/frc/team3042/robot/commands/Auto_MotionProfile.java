package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.CANTalon;

public class Auto_MotionProfile {
	
	//Current point
	int pointNumber = 0;

	//Time between each point in ms
	double itp;
		
	//Time for each filter in ms
	double time1, time2;
		
	//Maximum speed in RPM
	double maxVelocity;
		
	//Distance traveled in rotations
	double distance;
		
	//Length of each filter
	double filterLength1, filterLength2;
		
	//Sum of each filter
	double filterSum1 = 0, filterSum2 = 0, filterTotalSum = 0;
		
	//Velocity and position at current point
	double currentVelocity = 0, currentPosition = 0;
		
	//Time to decceleration in ms
	double timeToDeccel;
		
	//Total time in ms
	double totalTime;
		
	//Total number of points
	double totalPoints;
		
	//Array with all values of filterSum1
	double[] filterSums1;
	
	public Auto_MotionProfile(double itp, double time1, double time2, 
			double maxVelocity, double distance) {
		this.itp = itp;
		this.time1 = time1;
		this.time2 = time2;
		this.maxVelocity = maxVelocity;
		this.distance = distance;
		
		//Calculating values from these values
		filterLength1 = Math.ceil(time1 / itp);
		filterLength2 = Math.ceil(time2 / itp);
		timeToDeccel = distance / maxVelocity * 60 * 1000;
		totalTime = timeToDeccel + time1 + time2;
		totalPoints = Math.ceil(totalTime / itp);
		filterSums1 = new double[(int) totalPoints];
	}
	
	private void runFilters() {
		//Running through first filter
    	if (pointNumber * itp < time1 + time2) {
    		//Accelerating filter 1
    		filterSum1 = pointNumber / filterLength1;
    	}
    	else if (pointNumber * itp >= timeToDeccel) {
    		//Deccelerating filter 1
    		filterSum1 = (pointNumber - timeToDeccel / itp)
    				/ filterLength1;
    	}
    	else {
    		filterSum1 = 1;
    	}
    	
    	//Creating filterSum2 from the sum of the last filterLength2 values of filterSum1
    	filterSums1[pointNumber] = filterSum1;
    	int filter2Start = (int) ((pointNumber > filterLength2) ? pointNumber - filterLength2 : 0);
    	filterSum2 = 0;
    	for(int i = filter2Start; i <= pointNumber; i++) {
    		filterSum2 += filterSums1[i];
    	}
	}
	
	private void calculatePosition() {
		currentVelocity = (filterSum1 + filterSum2) / (filterLength2 + 1) * maxVelocity;
	}
	
	private void calculateVelocity() {
		
	}
	
	public CANTalon.TrajectoryPoint[] calculateProfile() {
		
		return null;
		
	}

}

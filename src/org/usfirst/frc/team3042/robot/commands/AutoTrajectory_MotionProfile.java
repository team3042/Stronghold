package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.CANTalon;

public class AutoTrajectory_MotionProfile {
	
	//Current point
	int currentPoint = 0;

	//Time between each point in ms
	int itp;
		
	//Time for each filter in ms
	double time1, time2;
		
	//Maximum speed in rotations / min
	double maxVelocity;
		
	//Distance traveled in rotations
	double distance;
		
	//Length of each filter
	double filterLength1, filterLength2;
		
	//Sum of each filter
	double filterSum1 = 0, filterSum2 = 0, filterTotalSum = 0;
				
	//Velocity and position at current point
	double currentVelocity = 0, currentPosition = 0, oldVelocity = 0;
				
	//Time to decceleration in ms
	double timeToDeccel;
		
	//Total time in ms
	double totalTime;
				
	//Total number of points
	int totalPoints;
				
	//Array with all values of filterSum1
	double[] filterSums1;
	
	public AutoTrajectory_MotionProfile(int itp, double time1, double time2, 
			double maxVelocity, double distance) {
		this.itp = itp;
		this.time1 = time1;
		this.time2 = time2;
		this.maxVelocity = maxVelocity;
		this.distance = distance;
		
		//Calculating values from these values
		filterLength1 = Math.ceil(time1 / itp);
		filterLength2 = Math.ceil(time2 / itp);
		timeToDeccel = distance / maxVelocity * 1000;
		totalTime = timeToDeccel + time1 + time2;
		totalPoints = (int) Math.ceil(totalTime / itp);
		filterSums1 = new double[totalPoints + 1];
	}
	
	private void runFilters() {
		//Running through first filter
    	if (currentPoint * itp < time1) {
    		//Accelerating filter 1
    		filterSum1 = currentPoint / filterLength1;
    	}
    	else if (currentPoint >= totalPoints - filterLength2) {
    		filterSum1 = 0;
    	}
    	else if (currentPoint * itp >= timeToDeccel) {
    		//Deccelerating filter 1
    		filterSum1 = (totalPoints - filterLength2 - currentPoint) / filterLength1;
    	}
    	else {
    		filterSum1 = 1;
    	}
    	
    	//Creating filterSum2 from the sum of the last filterLength2 values of filterSum1
    	filterSums1[currentPoint] = filterSum1;
    	int filter2Start = (int) ((currentPoint > filterLength2) ? currentPoint - filterLength2 + 1 : 0);
    	filterSum2 = 0;
    	for(int i = filter2Start; i <= currentPoint; i++) {
    		filterSum2 += filterSums1[i];
    	}
    	
	}
	
	private void calculatePosition() {
		currentPosition += (oldVelocity + currentVelocity) / 2 * itp / 1000;
	}
	
	private void calculateVelocity() {
		oldVelocity = currentVelocity;
		currentVelocity = (filterSum1 + filterSum2) / (filterLength2 + 1) * maxVelocity;
	}
	
	//Generating every point on the profile and putting them into an array
	public CANTalon.TrajectoryPoint[] calculateProfile() {
		
		CANTalon.TrajectoryPoint[] trajectory = new CANTalon.TrajectoryPoint[totalPoints + 1];
		
		for(int i = 0; i <= totalPoints; i++) {
			runFilters();
			calculateVelocity();
			calculatePosition();
			
			trajectory[i] = new CANTalon.TrajectoryPoint();
			trajectory[i].timeDurMs = itp;
			trajectory[i].position = currentPosition;
			trajectory[i].velocity = currentVelocity * 60;
			trajectory[i].profileSlotSelect = 0;
			trajectory[i].velocityOnly = false;
			trajectory[i].zeroPos = (currentPoint == 0);
			trajectory[i].isLastPoint = (currentPoint == totalPoints);
					
			currentPoint++;
		}
				
		return trajectory;
	}

}

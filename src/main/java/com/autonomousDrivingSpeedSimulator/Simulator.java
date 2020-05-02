package com.autonomousDrivingSpeedSimulator;

import java.util.Scanner;

public class Simulator {

	public static void main(String args[]) {

		SimulationHandler simulationHandler = new SimulationHandlerImpl();

		Scanner sc = new Scanner(System.in);
		String modeString = sc.nextLine();

		String selectedMode = simulationHandler.getSelectedMode(modeString);
		int currentSpeed = simulationHandler.getInititalSpeed();

		if (!"".equals(selectedMode)) {

			System.out.println("Welcome. Driving mode is " + selectedMode);
			System.out.println(currentSpeed);

			int updatedSpeed = currentSpeed;

			while (true) {
				System.out.print("Enter Event:");
				int event = sc.nextInt();
				if (simulationHandler.checkValidEvent(event)) {

					int speed = simulationHandler.process(event, updatedSpeed, selectedMode);
					System.out.println(speed);
					updatedSpeed = speed;
				} else {

					System.out.println("Invalid");
				}
			}

		} else {
			System.out.println("Invalid Mode!");
		}
	}
}

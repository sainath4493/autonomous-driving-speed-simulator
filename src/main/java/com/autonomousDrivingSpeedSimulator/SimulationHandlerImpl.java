package com.autonomousDrivingSpeedSimulator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SimulationHandlerImpl implements SimulationHandler {

	private List<String> modeList;
	private List<Integer> eventsFlagList;
	private int initialSpeed;

	public SimulationHandlerImpl() {

		modeList = Arrays.asList("NORMAL", "SPORT", "SAFE");
		initialSpeed = 20;
		eventsFlagList = new ArrayList<>();
	}

	/*
	 * To get Mode from entered input string
	 */
	public String getSelectedMode(String modeString) {

		String modeSelectd = "";

		for (String mode : modeList) {
			if (modeString.contains(mode)) {
				modeSelectd = mode;
			}
		}
		return modeSelectd;
	}

	/*
	 * To get initial speed
	 **/
	public int getInititalSpeed() {
		return initialSpeed;
	}

	/*
	 * To find speed based on event and mode from json
	 **/
	public String findSpeedBasedOnEventAndMode(int event, String selectedMode) {

		String speed = "";

		JSONObject jsonObject = getModeJSON(selectedMode);
		String value = (String) jsonObject.get(String.valueOf(event));
		speed = value.replace("kph", "").trim();
		return speed;
	}

	/*
	 * To get Json Object for selected mode
	 **/
	public JSONObject getModeJSON(String selectedMode) {

		JSONObject modeJson = new JSONObject();
		JSONParser parser = new JSONParser();
		String filePath = "";
		try {
			if ("NORMAL".equals(selectedMode)) {
				filePath = "normal.json";
			}
			if ("SPORT".equals(selectedMode)) {
				filePath = "sport.json";
			}
			if ("SAFE".equals(selectedMode)) {
				filePath = "safe.json";
			}
			ClassLoader classLoader = new SimulationHandlerImpl().getClass().getClassLoader();
			File file = new File(classLoader.getResource(filePath).getFile());
			FileReader reader = new FileReader(file);

			Object obj = parser.parse(reader);
			modeJson = (JSONObject) obj;

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return modeJson;
	}

	/*
	 * To check valid event entered or not check less than 100
	 **/
	public boolean checkValidEvent(int event) {

		boolean flag = false;
		if (event < 100) {
			return true;
		}
		return flag;
	}

	/*
	 * to check speed and maintain 10kph
	 **/
	public int check10kph(int currentSpeed) {

		int speed = 10;
		if (currentSpeed > 10) {
			speed = currentSpeed;
		}
		return speed;
	}

	/*
	 * To get Speed list Sign speed for selected mode
	 **/
	public int getSpeedLimitSignSpeed(int event, String selectedMode, int x) {

		int speed = 0;

		String value = findSpeedBasedOnEventAndMode(event, selectedMode);
		value = value.replace("kph", "").trim();
		if (value.contains("+")) {
			String nums[] = value.split("[+]");
			speed = x + Integer.parseInt(nums[1]);

		} else if (value.contains("-")) {
			String nums[] = value.split("[-]");
			speed = x - Integer.parseInt(nums[1]);
		} else {
			speed = Integer.parseInt(value.replace("X", String.valueOf(x)));
		}
		return speed;
	}

	/*
	 * To call condition check for each input
	 * 
	 **/
	public int process(int event, int currentSpeed, String selectedMode) {

		int speed = conditionChecks(event, currentSpeed, selectedMode);
		return speed;
	}

	/*
	 * Check the condition according to sensor event and calculate speed
	 **/
	public int conditionChecks(int event, int currentSpeed, String selectedMode) {

		String speedString = String.valueOf(currentSpeed);

		switch (event) {
		case 1:
			if (!eventsFlagList.contains(1)) {
				speedString = speedString + findSpeedBasedOnEventAndMode(event, selectedMode);
				eventsFlagList.add(event);
			}
			break;

		case 2:
			if (eventsFlagList.contains(1)) {
				speedString = speedString + findSpeedBasedOnEventAndMode(event, selectedMode);
				eventsFlagList.remove(new Integer(1));
			}

			break;

		case 3:
			if (!eventsFlagList.contains(3)) {
				speedString = speedString + findSpeedBasedOnEventAndMode(event, selectedMode);
				eventsFlagList.add(event);
			}

			break;

		case 4:
			if (eventsFlagList.contains(3)) {
				speedString = speedString + findSpeedBasedOnEventAndMode(event, selectedMode);
				eventsFlagList.remove(new Integer(3));
			}

			break;

		case 5:
			if (!eventsFlagList.contains(5)) {
				speedString = speedString + findSpeedBasedOnEventAndMode(event, selectedMode);
				eventsFlagList.add(event);
			}

			break;

		case 6:
			if (eventsFlagList.contains(5)) {
				speedString = speedString + findSpeedBasedOnEventAndMode(event, selectedMode);
				eventsFlagList.remove(new Integer(5));
			}

			break;

		case 7:
			if (!eventsFlagList.contains(7)) {
				if (!eventsFlagList.contains(5)) {
					speedString = speedString + findSpeedBasedOnEventAndMode(event, selectedMode);
					eventsFlagList.add(event);
				}
			}

			break;

		case 8:
			break;
		case 9:
			break;

		default:

			speedString = String.valueOf(getSpeedLimitSignSpeed(10, selectedMode, event));
			if (eventsFlagList.contains(7)) {
				eventsFlagList.remove(new Integer(7));
			}
			break;
		}
		int speed = check10kph(calculate(speedString));
		return speed;
	}

	/*
	 * Calculate speed from string
	 **/
	public int calculate(String operation) {
		int speed = 0;
		if (operation.contains("+")) {
			String nums[] = operation.split("[+]");
			speed = Integer.parseInt(nums[0]) + Integer.parseInt(nums[1]);
		} else if (operation.contains("-")) {
			String nums[] = operation.split("[-]");
			speed = Integer.parseInt(nums[0]) - Integer.parseInt(nums[1]);
		} else {
			speed = Integer.parseInt(operation);
		}
		return speed;
	}
}

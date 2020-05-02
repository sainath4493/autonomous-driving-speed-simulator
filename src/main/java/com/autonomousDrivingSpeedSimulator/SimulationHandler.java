package com.autonomousDrivingSpeedSimulator;

import org.json.simple.JSONObject;

public interface SimulationHandler {

	public String getSelectedMode(String modeString);

	public int getInititalSpeed();

	public String findSpeedBasedOnEventAndMode(int event, String selectedMode);

	public JSONObject getModeJSON(String selectedMode);

	public boolean checkValidEvent(int event);

	public int check10kph(int currentSpeed);

	public int getSpeedLimitSignSpeed(int event, String selectedMode, int x);

	public int process(int event, int currentSpeed, String selectedMode);

	public int conditionChecks(int event, int currentSpeed, String selectedMode);

	public int calculate(String operation);

}

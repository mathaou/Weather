package environment;

import weatherCalculations.RealTimeWeather;

public class DataMinipulator {
	public RealTimeWeather weather;
	public int numIterations;
	public final int TIME_INTERVAL = 1;
	public float[] day;
	public DataMinipulator(){
		weather = new RealTimeWeather("Winona", "US");
		numIterations = (weather.getMidday1439()-weather.getSunRise1439())/TIME_INTERVAL;
	}
	
	public RealTimeWeather getWeather(){
		return weather;
	}
	
	public int getNumIterations(){
		return numIterations;
	}
	
}

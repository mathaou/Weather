package weatherCalculations;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import il.ac.hit.finalproject.classes.IWeatherDataService;
import il.ac.hit.finalproject.classes.Location;
import il.ac.hit.finalproject.classes.WeatherData;
import il.ac.hit.finalproject.classes.WeatherDataServiceFactory;
import il.ac.hit.finalproject.classes.WeatherDataServiceFactory.service;
import il.ac.hit.finalproject.exceptions.WeatherDataServiceException;

public class RealTimeWeather {
	private IWeatherDataService weather;
	private WeatherData weatherData;
	public String city, country, timeAsString;
	public int sunRiseHours, sunRiseMinutes, sunSetHours, sunSetMinutes, currentTimeHours, currentTimeMinutes;
	public int sunRise1439, sunSet1439, currentTime1439, midday1439;
	//public boolean thunder;//Needs implementation
	public final int TIMEZONE_OFFSET = 5;
	public DateFormat dateFormat;
	public Calendar cal;
	
	public RealTimeWeather(String city, String country){
		this.city = city;
		this.country = country;
		generateWeatherScope(city, country);
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cal = Calendar.getInstance();
		generateSunRise();
		generateSunSet();
		generateMidday();
		/*System.out.println("Midday in minutes: "+midday1439);
		System.out.println("Sunrise in minutes: "+sunRise1439);
		System.out.println("Sunset in minutes: "+sunSet1439);*/
	}
	
	public void generateMidday(){
		
		midday1439 = (sunRise1439+sunSet1439)/2;
	}
	
	public void generateSunRise(){
		cal = Calendar.getInstance();
		timeAsString = dateFormat.format(cal.getTime());
		timeAsString = weatherData.getSun().getRise();
		timeAsString = timeAsString.substring(11, timeAsString.length());
		sunRiseMinutes = Integer.parseInt(timeAsString.substring(6, timeAsString.length()));
		sunRiseHours = Integer.parseInt(timeAsString.substring(0,2))-TIMEZONE_OFFSET;
		sunRise1439 = (sunRiseHours*60)+sunRiseMinutes;
	}
	
	public void generateSunSet(){
		cal = Calendar.getInstance();
		timeAsString = dateFormat.format(cal.getTime());
		timeAsString = weatherData.getSun().getSet();
		timeAsString = timeAsString.substring(11, timeAsString.length());
		sunSetMinutes = Integer.parseInt(timeAsString.substring(6, timeAsString.length()));
		sunSetHours = Integer.parseInt(timeAsString.substring(0,2))-TIMEZONE_OFFSET;
		sunSet1439 = (sunSetHours*60)+sunSetMinutes;
	}
	
	public void generateWeatherScope(String city, String country){
		weather = WeatherDataServiceFactory.getWeatherDataService(service.OPEN_WEATHER_MAP);
		try {
			weatherData = weather.getWeatherData(new Location(city, country));
		} catch (WeatherDataServiceException e) {
			e.printStackTrace();
		}
	}
	
	//GETTERS AND SETTERS
	//Here's the link for more information = https://openweathermap.org/weather-conditions
	/*
	 * Azimuths are the celestial unit of rotation in degrees around the earth when talking about the rotation or direction of a celestial body.
	 * N = 0 degrees, S = 180 degrees
	 */
	public int getWindDirectionInAzimuth(){
		return Integer.parseInt(weatherData.getWind().getDirection().getValue());
	}
	/*
	 * Returns a simplified version of azimuths with general N, S, E, W, SW, NW, etc...
	 */
	public String getWindDirection(){
		return weatherData.getWind().getDirection().getDirectionCode();
	}
	/*
	 * Returns an general statement based on visibility
	 */
	public String getVisibility(){
		return weatherData.getVisibility().getValue();
	}
	/*
	 * Gets the current temperature in f
	 */
	public float getCurrentTemp(){
		return Float.parseFloat(weatherData.getTemperature().getValue())*(9/5)+32;
	}
	/*
	 * Returns a descriptor describing the wind speed.
	 */
	public String getWindSpeedName(){
		return weatherData.getWind().getSpeed().getName();
	}
	/*
	 * Returns the windspeed in m/s 
	 */
	public float getWindSpeed(){
		return Float.parseFloat(weatherData.getWind().getSpeed().getValue().substring(0, 4));
	}
	/*
	 * Returns a descriptive string about the clouds:
	 * clear, few clouds, scattered, broken, overcast.
	 */
	public String getCloudData(){
		return weatherData.getClouds().getValue();
	}
	/*
	 * Returns how humid it is.
	 */
	public String getHumidityData(){
		return weatherData.getHumidity().getValue();
	}
	/*
	 * Returns the amount of rain or snowfall in mm.
	 */
	public float getRainfallInMM(){
		return Float.parseFloat(weatherData.getPrecipitation().getValue());
	}
	/*
	 * Simply returns if its raining or snowing or not at all.
	 */
	public String getRainfallMode(){
		return weatherData.getPrecipitation().getMode();
	}
	
	public int getSunRise1439(){
		return sunRise1439;
	}
	
	public int getSunSet1439(){
		return sunSet1439;
	}
	
	public void printTime(){
		cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
	}
	
	public int getCurrentTime1439(){
		cal = Calendar.getInstance();
		timeAsString = dateFormat.format(cal.getTime());
		timeAsString = timeAsString.substring(11, timeAsString.length()-3);
		currentTimeMinutes = Integer.parseInt(timeAsString.substring(3, timeAsString.length()));
		currentTimeHours = Integer.parseInt(timeAsString.substring(0,2));
		currentTime1439 = (currentTimeHours*60)+currentTimeMinutes;
		return currentTime1439;
	}
	
	public int getMidday1439(){
		return midday1439;
	}

	/*
	 * Boring getters and setters that don't require explaining.
	 */
	
	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public int getSunRiseHours() {
		return sunRiseHours;
	}

	public void setSunRiseHours(int sunRiseHours) {
		this.sunRiseHours = sunRiseHours;
	}

	public int getSunRiseMinutes() {
		return sunRiseMinutes;
	}

	public void setSunRiseMinutes(int sunRiseMinutes) {
		this.sunRiseMinutes = sunRiseMinutes;
	}

	public int getSunSetHours() {
		return sunSetHours;
	}

	public void setSunSetHours(int sunSetHours) {
		this.sunSetHours = sunSetHours;
	}

	public int getSunSetMinutes() {
		return sunSetMinutes;
	}

	public void setSunSetMinutes(int sunSetMinutes) {
		this.sunSetMinutes = sunSetMinutes;
	}

	public int getCurrentTimeHours() {
		return currentTimeHours;
	}

	public void setCurrentTimeHours(int currentTimeHours) {
		this.currentTimeHours = currentTimeHours;
	}

	public int getCurrentTimeMinutes() {
		return currentTimeMinutes;
	}

	public void setCurrentTimeMinutes(int currentTimeMinutes) {
		this.currentTimeMinutes = currentTimeMinutes;
	}

	public void setCurrentTime1439(int currentTime1439) {
		this.currentTime1439 = currentTime1439;
	}

	public WeatherData getWeatherData() {
		return weatherData;
	}

	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Calendar getCal() {
		return cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	
}

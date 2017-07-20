package environment;

import java.util.Calendar;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

public class Environment extends BasicGame{
	
	//OBJECTS
	
	public Color[] phaseColors, gradientColors, effectColors;
	public Color grass, mountainColor;
	public Circle reticle, overlay;
	public Random rand;
	
	public Rectangle c;
	
	public DataMinipulator data;
	
	public GradientFill fill;
	
	public Image b;
	
	public Polygon hill, lightBeams, mountain, lightningBolt;
	
	public CloudGeneration cg;
	
	//PRIMATIVE TYPES
	
	public float[] hillPointModX, hillPointModY, lightBeamsMod, mountainPoints;
	
	public int time, currentTime, x,  num, lightAlpha, effectAlpha;
	
	public final int RAIN = 0, THUNDER = 1;
	
	public double xCoord, yCoord;
	
	public Environment(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		//cg = new CloudGeneration(gc,100);
		
		rand = new Random();
		
		lightningBolt = new Polygon();
		lightningBolt.addPoint(gc.getWidth()/2, 0);
		initializeHill(gc);
		initializeLightBeams(gc);
		initializeMountain(gc);
		
		c = new Rectangle(0, 0, gc.getWidth(), gc.getHeight()-300);
		
		grass = new Color(21,45,0);
		
		mountainColor = new Color(19,66,63);
		
		data = new DataMinipulator();
		
		System.out.println("Sunrise: "+data.getWeather().getSunRise1439()+" | Midday: "+data.getWeather().getMidday1439()+" | Sunset: "+data.getWeather().getSunSet1439());
		
		currentTime = data.getWeather().getCurrentTime1439();
		reticle = new Circle(0,0,1);
		
		xCoord = 0;
		yCoord = 0;
		
		effectAlpha = 20;
		
		effectColors = new Color[]{
				new Color(196,196,196, effectAlpha),
				new Color(33,38,51, effectAlpha)};
		
		phaseColors = new Color[]{
				new Color(3,1,18),
				new Color(3,9,31),
				new Color(220,64,28),
				new Color(239,17,79), 
				new Color(155,59,233),
				new Color(253,212,34),
				new Color(48,31,247),
				new Color(4,108,255),
				new Color(48,31,247),
				new Color(240,171,25),
				new Color(251,103,2),
				new Color(249,22,48),
				new Color(3,1,18),
				new Color(3,1,18)};
		
		overlay = new Circle(gc.getWidth()/2, gc.getHeight()/2,2000);
		
		gradientColors = new Color[]{new Color(0,0,0),new Color(0,0,0)};
		
		b = new Image("C:/Users/fx6134ud/workspace/Weather/res/windows.png");
		
		fill = new GradientFill(gc.getWidth()/2,gc.getHeight(),phaseColors[0],gc.getWidth()/2,0,phaseColors[0]);
		x = 0;
		currentTime = 400;
		//currentTime = data.getWeather().getCurrentTime1439();
		lightAlpha = 20;
		
	}

	@Override
	public void update(GameContainer gc, int d) throws SlickException {
		
		xCoord = Mouse.getX();
		yCoord = gc.getHeight()-Mouse.getY();
		reticle.setCenterX((float) xCoord);
		reticle.setCenterY((float) yCoord);
		time+=d;
		if(time >= 100){
			time=0;
			currentTime++;
			if(currentTime == 0){
				data.getWeather().generateSunRise();
				data.getWeather().generateSunSet();
				data.getWeather().generateMidday();
			}
			System.out.println(currentTime);
			phases();
		}
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//sky
		g.fill(c, fill);
		//light beams
		g.setColor(new Color(255, 255, 255, lightAlpha));
		g.fill(lightBeams);
		//mountain
		g.setColor(mountainColor);
		g.fill(mountain);
		//effects
		g.setColor(new Color(196,196,196,effectAlpha));
		g.fill(overlay);
		//hill
		g.setColor(grass);
		g.fill(hill);
		//string
		g.setColor(Color.white);
		g.drawString(data.getWeather().getDateFormat().format(Calendar.getInstance().getTime())+" | "+"X: "
				+ reticle.getCenterX()+", Y: "+reticle.getCenterY(), 10, gc.getHeight()-40);
		//b.draw(0, 0, gc.getWidth(), gc.getHeight());
		/*g.drawString(currentTime/60+" | "+"X: "
				+ reticle.getCenterX()+", Y: "+reticle.getCenterY(), 10, gc.getHeight()-40);*/
	}
	
	//OTHER METHODS
	
	public void phases(){
		if((currentTime > 0 && currentTime < data.getWeather().getSunRise1439()-15)||(currentTime > data.getWeather().getSunSet1439()+15&&currentTime < 1439)){
			totalDarkness();
		}
		if(currentTime > data.getWeather().getSunRise1439()-15 && currentTime < data.getWeather().getSunRise1439()-15+6){
			phaseA();
		}
		if(currentTime > data.getWeather().getSunRise1439()-15+6 && currentTime < data.getWeather().getSunRise1439()-15+12){
			phaseB();
		}
		if(currentTime > data.getWeather().getSunRise1439()-15+12 && currentTime < data.getWeather().getSunRise1439()-15+18){
			phaseC();
		}
		if(currentTime > data.getWeather().getSunRise1439()-15+18 && currentTime < data.getWeather().getSunRise1439()-15+24){
			phaseD();
		}
		if(currentTime > data.getWeather().getSunRise1439()-15+24 && currentTime < data.getWeather().getSunRise1439()-15+30){
			phaseE();
		}
		if(currentTime > data.getWeather().getSunRise1439()+15 && currentTime < data.getWeather().getSunRise1439()+30){
			phaseF();
		}
		if(currentTime > data.getWeather().getSunRise1439()+30&&currentTime < data.getWeather().getSunSet1439()-15){
			phaseG();
		}
		if(currentTime > data.getWeather().getSunSet1439()-15 && currentTime < data.getWeather().getSunSet1439()-15+6){
			phaseH();
		}
		if(currentTime > data.getWeather().getSunSet1439()-15+6 && currentTime < data.getWeather().getSunSet1439()-15+12){
			phaseI();
		}
		if(currentTime > data.getWeather().getSunSet1439()-15+12 && currentTime < data.getWeather().getSunSet1439()-15+18){
			phaseJ();
		}
		if(currentTime > data.getWeather().getSunSet1439()-15+18 && currentTime < data.getWeather().getSunSet1439()-15+24){
			phaseK();
		}
		if(currentTime > data.getWeather().getSunSet1439()-15+24 && currentTime < data.getWeather().getSunSet1439()-15+30){
			phaseL();
		}
	}
	
	public void adjustColor(Color a, Color b, float timePeriod){
		if(a.r < b.r){
			if(a.r+b.r/data.getNumIterations()<1)
				a.r+=(1/timePeriod);
		}
		if(a.r > b.r){
			if(a.r-b.r/data.getNumIterations()>0)
				a.r-=(1/timePeriod);
		}
		if(a.g < b.g){
			if(a.g+b.g/data.getNumIterations()<1)
				a.g+=(1/timePeriod);
		}
		if(a.g > b.g){
			if(a.g-b.g/data.getNumIterations()>0)
				a.g-=(1/timePeriod);
		}
		if(a.b < b.b){
			if(a.b+b.b/data.getNumIterations()<1)
				a.b+=(1/timePeriod);
		}
		if(a.b > b.b){
			if(a.b-b.b/data.getNumIterations()>0)
				a.b-=(1/timePeriod);
		}
	}
	
	public void totalDarkness(){
		adjustColor(gradientColors[0],phaseColors[0],50);
		fill.setStartColor(gradientColors[0]);
		adjustColor(gradientColors[1],phaseColors[1],50);
		fill.setEndColor(gradientColors[1]);
	}
	
	public void phaseA(){
		phaseChain(50, 1);
	}

	public void phaseB(){
		phaseChain(50, 2);
	}
	
	public void phaseC(){
		phaseChain(50, 3);
	}
	
	public void phaseD(){
		phaseChain(50, 4);
	}
	
	public void phaseE(){
		phaseChain(50, 5);
	}
	
	public void phaseF(){
		phaseChain(50, 6);
	}
	
	public void phaseG(){
		phaseChain(50, 7);
	}
	
	public void phaseH(){
		phaseChain(50, 8);
	}
	
	public void phaseI(){
		phaseChain(50, 9);
	}
	
	public void phaseJ(){
		phaseChain(50, 10);
	}
	
	public void phaseK(){
		phaseChain(50, 11);
	}
	
	public void phaseL(){
		phaseChain(50, 12);
	}
	
	public void phaseChain(float timePeriod, int a){
		adjustColor(gradientColors[0],phaseColors[a],timePeriod);
		fill.setStartColor(gradientColors[0]);
		adjustColor(gradientColors[1],phaseColors[a+1],timePeriod);
		fill.setEndColor(gradientColors[1]);
	}
	
	public void initializeHill(GameContainer gc){
		hill = new Polygon();
		
		hillPointModX = new float[]{0f,.0984f,.3182f,.7421f,1};
		hillPointModY = new float[]{.658333f,.5925f,.5574f,.6620f,.7490f};
		
		for(int i = 0; i < hillPointModX.length; i++){
			hill.addPoint(hillPointModX[i]*gc.getWidth(), hillPointModY[i]*gc.getHeight());
		}
		hill.addPoint(gc.getWidth(), gc.getHeight());
		hill.addPoint(0, gc.getHeight());
		hill.setClosed(true);
	}
	
	public void initializeLightBeams(GameContainer gc){
		lightBeams = new Polygon();
		
		lightBeamsMod = new float[]{
				gc.getWidth(),897,0,425,0,262,gc.getWidth(),796,93,0,450,0,gc.getWidth(),717,1121,0,1308,0,gc.getWidth(),740,gc.getWidth(),897};
		
		for(int i = 0; i < lightBeamsMod.length; i+=2){
			lightBeamsMod[i] = lightBeamsMod[i]/gc.getWidth();
		}
		for(int i = 1; i < lightBeamsMod.length; i+=2){
			lightBeamsMod[i] = lightBeamsMod[i]/gc.getHeight();
			lightBeams.addPoint(lightBeamsMod[i-1]*gc.getWidth(), lightBeamsMod[i]*gc.getHeight());
		}
		hill.setClosed(true);
	}
	
	public void initializeMountain(GameContainer gc){
		mountain = new Polygon();
		
		mountainPoints = new float[]{
			1546,742,1745,686,gc.getWidth(),722,gc.getWidth(),869, gc.getWidth(), gc.getHeight()	
		};
		for(int i = 0; i < mountainPoints.length; i+=2){
			mountainPoints[i] = mountainPoints[i]/gc.getWidth();
		}
		for(int i = 1; i < mountainPoints.length; i+=2){
			mountainPoints[i] = mountainPoints[i]/gc.getHeight();
			mountain.addPoint(mountainPoints[i-1]*gc.getWidth(), mountainPoints[i]*gc.getHeight());
		}
		mountain.setClosed(true);
	}
	
	//doesnt work
	public Polygon generateLightning(GameContainer gc, int intensity){
		int a = rand.nextInt(1);
		for(int i = 0; i < intensity; i+=rand.nextInt(100)){
			if(a == 1){
				a = -1;
			}else{
				a = 1;
			}
			lightningBolt.addPoint(gc.getWidth()/2+(rand.nextInt(100)*a), i);
		}
		return lightningBolt;
	}

}

package environment;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CloudGeneration {
	public ArrayList<Cloud> cloudList;
	public Random randySavage;
	
	public CloudGeneration(GameContainer gc, int freq){
		randySavage = new Random();
		cloudList = new ArrayList<Cloud>();
		for(int i = 0; i < freq; i++){
			Cloud c = new Cloud(randySavage.nextInt(gc.getWidth()),randySavage.nextInt(gc.getHeight()/2),randySavage.nextInt(200));
			cloudList.add(c);
		}
		for(int i = 0; i < cloudList.size()-1; i++){
			cloudList.get(i).getCloud().union(cloudList.get(i+1).getCloud());
		}
	}
	
	public void drawClouds(Graphics g) throws SlickException{
		//Graphics g = cloudPNG.getGraphics();
		for(Cloud c: cloudList){
			g.fill(c.getCloud());
		}
	}
	
	public void moveClouds(){
		for(Cloud c: cloudList){
			c.getCloudPoint().set(c.getCloudPoint().getX()+1, c.getCloudPoint().getY());
		}
	}
}

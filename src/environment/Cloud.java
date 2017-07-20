package environment;

import java.util.Random;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

public class Cloud {
	public Vector2f cloudPoint;
	public float scalar;
	public Polygon cloud;
	public float[] quadPoints;
	public Random randySavage;
	
	public Cloud(int x, int y, float scalar){
		randySavage = new Random();
		cloudPoint = new Vector2f(x,y);
		this.scalar = scalar;
		quadPoints = new float[8];
		generateQuadrilateral(scalar);
		cloud = new Polygon(quadPoints);
		cloud.closed();
	}
	
	public void generateQuadrilateral(float scalar){
		int i = 0;
		while(i < 4){
			if(i % 2 == 0){
				quadPoints[i] = cloudPoint.getX()+randySavage.nextInt((int)scalar);
				i++;
			}
			else{
				quadPoints[i] = cloudPoint.getY()+randySavage.nextInt((int)scalar);
				i++;
			}
		}
		i = 0;
		while(i < 2){
			quadPoints[i+4] = quadPoints[i]+scalar;
			quadPoints[i+5] = quadPoints[i+1]+scalar;
			i++;
		}
	}

	public Vector2f getCloudPoint() {
		return cloudPoint;
	}

	public void setCloudPoint(Vector2f cloudPoint) {
		this.cloudPoint = cloudPoint;
	}

	public float getScalar() {
		return scalar;
	}

	public void setScalar(float scalar) {
		this.scalar = scalar;
	}

	public Polygon getCloud() {
		return cloud;
	}

	public void setCloud(Polygon cloud) {
		this.cloud = cloud;
	}
	
}

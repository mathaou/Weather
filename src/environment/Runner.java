package environment;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class Runner {
	/*  
	    0. 800 x 600 x 32 @60Hz
		1. 1920 x 1080 x 32 @60Hz
		2. 1366 x 768 x 32 @60Hz
		3. 1280 x 720 x 32 @60Hz
		4. 1600 x 900 x 32 @60Hz
		5. 640 x 480 x 32 @60Hz
		6. 1024 x 768 x 32 @60Hz
		7. 1280 x 1024 x 32 @60Hz
	 */
	public static void main(String[]args) throws SlickException, LWJGLException{
		DisplayMode[] modes = Display.getAvailableDisplayModes();
		AppGameContainer game = new AppGameContainer(new Environment(""));
		game.setShowFPS(false);
		//game.setMouseGrabbed(true);
		int i = 1;
		game.setDisplayMode(modes[i].getWidth(), modes[i].getHeight(), false);
		game.setMaximumLogicUpdateInterval(60);
		game.setAlwaysRender(true);
		
		game.start();
	}
}

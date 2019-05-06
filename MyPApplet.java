package module2;

import processing.core.*;

public class MyPApplet extends PApplet {
	
	private String url = "https://cdn.eso.org/images/screen/eso1907a.jpg";
	private PImage background;
	
	public void setup() {
		size(200, 200);
		background = loadImage(url, "jpg");
		
	}
	
	public void draw() {
		
		background.resize(0,  height);
		image(background, 0, 0);
		int h = hour();
		if (h >= 0 && h < 6) {
			fill(0, 0, 0);
		}
		if (h >= 6 && h < 21) {
			fill(255, 209, 0);
		}
		if (h >= 21 && h <= 23) {
			fill(128, 128, 128);
		}
		ellipse(width/4, height/5, width/5, height/5);
		
	}

}

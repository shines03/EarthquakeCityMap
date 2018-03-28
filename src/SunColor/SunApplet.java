package SunColor;

import processing.core.PApplet;
import processing.core.PImage;

public class SunApplet extends PApplet{
	PImage background; // Processing Image variable
	public void setup() //Configuration of PApplet runs only once 
	{
		background = loadImage("C:/Users/Shines03/Desktop/a.jpg"); //loaded the image to the PImage variable
		size(500,500); // size of the PApplet window
		background.resize(0, height); // Resizing the PImage 
	}
	public void draw() // Drawing the canvas called again and again
	{
		image(background,0,0); // Displays the image
		int[] rgb = setColors(second()); //second returns the exact seconds from machine in float type
		stroke(100); //Outline for a canvas with GrayScale measurement here
		fill(rgb[0], rgb[1], rgb[2]); // fills the canvas with given r, g, b value here
		ellipse(width/5, height/5, width/5, height/5); // creates an ellipse at x,y coordinates with width and height respectively
		fill(rgb[0], rgb[1], 75);
		ellipse(width/5, 10*height/11, width/5, height/9);
	}
	public int[] setColors(float seconds)
	{
		seconds = Math.abs(30 - seconds);
		float ratio = (seconds/30);
		int[] rgb = new  int [3];
		rgb[0]=((int)(255*ratio));
		rgb[1]=((int)(255*ratio));
		rgb[2]=0;
		return rgb;
	}

}

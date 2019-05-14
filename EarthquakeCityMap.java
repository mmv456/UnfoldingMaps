package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PShape;
//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = true;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(1500, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 400, 50, 1100, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 400, 50, 1100, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    for (PointFeature quake: earthquakes) {
	    	markers.add(createMarker(quake));
	    }
	    
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		//System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation(), feature.getProperties());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		//int mag = (int) Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
		
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    
	    int gray = color(125, 125, 125);
	    int black = color(0, 0, 0);
	    int red = color(255, 0, 0);
	    
//	    switch(mag) {
//	    
//	    case 0:
//	    	marker.setColor(red);
//	    
//	    case 3:
//	    	marker.setColor(yellow);
//	    
//	    case 2:
//	    	marker.setColor(gray);
//	    
//	    case 1:
//	    	marker.setColor(black);
//	    break;
//	    }
	    
	    if (mag >= 4.0) {
	    	marker.setColor(red);
	    }
	    
	    else if (mag < 4.0 && mag >= 3.0) {
	    	marker.setColor(yellow);
	    }
	    
	    else if (mag < 3.0 && mag >= 2.0) {
	    	marker.setColor(gray);
	    }
	    
	    else if (mag < 2.0) {
	    	marker.setColor(black);
	    }
	    
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	    
	    
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		
		// White space for key
		fill(color(0, 150, 255)); // color for gray
		PShape s;
		s = createShape(RECT, 10, 50, 400, 400);
		shape(s, 0, 0);
		fill(255, 255, 255); // color for white
		
		
		// Text for Key Title
		textSize(32);
		text("Key", 30, 30);
		
		
		// Text for Map Title
		textSize(32);
		text("Map", 500, 30);
		//fill(255, 255, 255);
		
		// Text for key
		
		String t = "Magnitude > 4.0";
		String u = "Magnitude between 3.0 and 4.0";
		String v = "Magnitude between 2.0 and 3.0";
		String w = "Magnitude < 2.0";
		textSize(20);
		text(t, 50, 100);
		text(u, 50, 150);
		text(v, 50, 200);
		text(w, 50, 250);
		//fill(50);
		
		// Shapes of circles
		
		// color for red
		fill(color(255, 0, 0));
		PShape one;
		one = createShape(ELLIPSE, 30, 85, 15, 15);
		shape(one, 0, 0);
		
		// color for yellow
		fill(color(255, 255, 0));
		PShape two;
		two = createShape(ELLIPSE, 30, 135, 15, 15);
		shape(two, 0, 0);
		
		// color for gray
		fill(color(125, 125, 125));
		PShape three;
		three = createShape(ELLIPSE, 30, 185, 15, 15);
		shape(three, 0, 0);
		
		// color for black
		fill(color(0, 0, 0));
		PShape four;
		four = createShape(ELLIPSE, 30, 235, 15, 15);
		shape(four, 0, 0);
		
		
//		textSize(10);
//		text("Magnitude between 3.0 and 4.0", 30, 150);
//		//fill(50);
//		
//		textSize(10);
//		text("Magnitude between 2.0 and 3.0", 30, 200);
//		//fill(50);
//		
//		textSize(10);
//		text("Magnitude < 2.0", 30, 250);
//		//fill(50);
		
	}
}

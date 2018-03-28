package selfPractice;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

public class EarthquakeCity extends PApplet{
	private UnfoldingMap map;
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	private static String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	private static final boolean offline =	false;
	public void setup()
	{
		size(800,500,P2D);
		AbstractMapProvider provider = new Google.GoogleMapProvider();
		int zoomLevel = 10;
		if (offline) {  
			provider = new MBTilesMapProvider(mbTilesString);
			zoomLevel = 3;
			earthquakesURL = "2.5_week.atom";
		}
		map = new UnfoldingMap(this, 210, 20, 560, 460, provider);
		MapUtils.createDefaultEventDispatcher(this, map);
		/* Location chile = new Location(-28.9f, -78.9f);
		Location japan = new Location(31.678f, 120.27f);
		Location alaska = new Location(60.128f, -176.475f);
		Location sumatra = new Location(-0.142f, 96.144f);
		Location kamchatka = new Location(56.132f, 159.522f);
		PointFeature chileEq = new PointFeature(chile);
		chileEq.addProperty("title", "Chile");
		chileEq.addProperty("magnitude", "9.5");
		chileEq.addProperty("date", "May 22 1960");
		chileEq.addProperty("year", "1960");
		PointFeature japanEq = new PointFeature(japan);
		japanEq.addProperty("title", "japan");
		japanEq.addProperty("magnitude", "9.0");
		japanEq.addProperty("date", "May 21 2001");
		japanEq.addProperty("year", "2001");
		PointFeature alaskaEq = new PointFeature(alaska);
		alaskaEq.addProperty("title", "alaska");
		alaskaEq.addProperty("magnitude", "9.2");
		alaskaEq.addProperty("date", "June 1 2011");
		alaskaEq.addProperty("year", "2011");
		PointFeature sumatraEq = new PointFeature(sumatra);
		sumatraEq.addProperty("title", "Sumatra");
		sumatraEq.addProperty("magnitude", "9.4");
		sumatraEq.addProperty("date", "Dec 26 2004");
		sumatraEq.addProperty("year", "2004");
		PointFeature kamchatkaEq = new PointFeature(kamchatka);
		kamchatkaEq.addProperty("title", "kamchatka");
		kamchatkaEq.addProperty("magnitude", "8.5");
		kamchatkaEq.addProperty("date", "May 11 1912");
		kamchatkaEq.addProperty("year", "1912"); **/
		List<PointFeature> eqFeatureList = ParseFeed.parseEarthquake(this, earthquakesURL);
		/* eqFeatureList.add(chileEq);
		eqFeatureList.add(japanEq);
		eqFeatureList.add(alaskaEq);
		eqFeatureList.add(sumatraEq);
		eqFeatureList.add(kamchatkaEq); */
		List<Marker> markList = new ArrayList<Marker>();
		for(PointFeature eq: eqFeatureList)
		{
			Marker mark = new SimplePointMarker(eq.getLocation(), eq.getProperties());
			/*Object magObj = eq.getProperty("magnitude");
			float mag = Float.parseFloat(magObj.toString());
			if(mag>4.5)
				mark.setColor(color(255,0,0));
			else
				mark.setColor(color(0,255,0));*/
			markList.add(mark);
		}
		for(Marker mark: markList)
		{
			if (Float.parseFloat((mark.getProperty("magnitude").toString())) >= 4)
				mark.setColor(color(255, 0, 0));
			else
				mark.setColor(color(0, 255, 0));
			map.addMarker(mark);
		}
	}
		
		public void draw()
		{
			background(100);
			map.draw();
		}
}

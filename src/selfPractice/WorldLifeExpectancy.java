package selfPractice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class WorldLifeExpectancy extends PApplet{
	
	private UnfoldingMap map;
	private Map<String, Float> LifeExpByCountry; // A Map where key is the country code and value is Average Life Expectancy
	List<Feature> countries;
	List<Marker> countryMarkers;
	public void setup()
	{
		size(1000,650, OPENGL);
		map = new UnfoldingMap(this, 20, 20, 960 , 610, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		LifeExpByCountry = loadLifeExpByCountry("F:/object-oriented-java/extras/UCSDUnfoldingMaps/data/LifeExpectancyWorldBankModule3.csv"); //The Map will be created by function called.
		countries = GeoJSONReader.loadData(this,"F:/object-oriented-java/extras/UCSDUnfoldingMaps/data/countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	
	private void shadeCountries() {
		for(Marker mark: countryMarkers)
		{
			String countryId = mark.getId();
			if(LifeExpByCountry.containsKey(countryId))
			{
				float value = LifeExpByCountry.get(countryId);
				int colorLevel = (int) map(value, 45, 90, 10, 255);
				mark.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else
			{
				mark.setColor(color(100, 100, 100));
			}
		}
		
	}

	private Map<String, Float> loadLifeExpByCountry(String fileName) {
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		String[] rows = loadStrings(fileName); //Read file row by row
		for(int i=1; i<rows.length;i++)
		{
			String[] column = rows[i].split(","); //Split the whole row into column
			if(!(column[5].toString()).equals(".."))
			{
				float value = Float.parseFloat(column[5]);
				//System.out.println(column[5]+"\n");
				lifeExpMap.put(column[4], value);
			}
		}
		return lifeExpMap;
	}

	public void draw()
	{
		background(150);
		map.draw();
	}

}

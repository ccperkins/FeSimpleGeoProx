## README ##


FeProxiMap is a lightweight collection of user-supplied geographical points which supports fast proximity search by search within a radius or by rectangle.  

By "lightweight" and "fast" here, I mean that it's midway between linear search (lightweight but slow: for a reasonable search, this is between 100 and 1000 times faster) and GeoRedis (which is blazingly fast but heavier weight).  Also, the documentation on GeoRedis says that its answers are approximate, while these are precisely as exact as LatLng will give.

Makes use of [SimpleLatLng by Tyler Coles](https://github.com/JavadocMD/simplelatlng) (also published under the Apache license), so latitude and longitude are represented in that coordinate system.


* Allows query within circle (radial distance from start point), and rectangle.
* Lightweight. The two jars (this and SimpleLatLng) together total less than 100K.
* Handles the poles properly.

* LIMITATION: Changes to caller's view of the location of objects will not be reflected in search results.  At this point, object locations must be held constant (or destroy the proximap and recreate with the new locations)
* LIMITATION: Persistence is the responsibility of caller.
* LIMITATION: Shares the limitations of LatLng: specifically it ignores elevation in calculating distance.
* LIMITATION: Slower than GeoRedis


## Performance ##
Being faster than brute force was pretty much the driving force behind this, so understanding the performance profile is essential: if you don't know its sweet spot, you don't know whether using it is a good idea.

From a sample database with a bit over a million points, the data from a series of requests (see the microbenchmarks in the tests folder for details) show a clear pattern:  if the search is "large" enough that it's going to return roughly half the points in the world or more, brute force will be faster.   But if the search is of a "reasonable" size (less than half), FeSimpleGeoProx outperforms brute force by a convincing margin.

![PerformanceProfile.png](https://bitbucket.org/repo/yaG8K9/images/152946229-PerformanceProfile.png)

## USAGE ##

Construct an instance of this and give it a collection of GeoObject instances (See GeoObject).  
A GeoObject is a set of coordinates (LatLng) and a user-given object.

### Example ###
1. Obviously, as a prospective user of this, you already have a source (database, file, collection) of geographical objects, in some class you've written, and obviously they all have geographical coordinates.
2. If needed, create a method to get the location in LatLng for each point
3. Create a world instance holding those points;
    
```
#!java
		List<MapObjectHolder<YourGeographicalPointClass>> mapObjects = new ArrayList<>();
		for (YourGeographicalPointClass point: yourGeographicalObjects) {
			LatLng loc = getLatLngForYourGeographicalPointClass (point);
			mapObjects.add(new MapObjectHolder<YourGeographicalPointClass> (loc, point));
		}
		FeProxiMap<YourGeographicalPointClass> world = new FeProxiMap<YourGeographicalPointClass>(mapObjects);
```
		    
4. You're now ready to search.  Given:
    	 a starting point (given in LatLng)
    	 a search radius (given as a double)
    	 the units of the radius (example: LengthUnit.MILE)
    	 

```
#!java

      Collection<MapObjectHolder<YourGeographicalPointClass>> pointsInRadius = world.find (start, radius, units);

```
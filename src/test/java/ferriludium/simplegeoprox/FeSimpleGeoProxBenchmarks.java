package ferriludium.simplegeoprox;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import ferriludium.simplegeoprox.FeSimpleGeoProxTest.TestClientMapObject;
import java.util.Collection;
import org.ferriludium.simplegeoprox.FeSimpleGeoProx;
import org.ferriludium.simplegeoprox.MapObjectHolder;
import org.junit.Test;

/*
 * Copyright [2016] [Cornelius Perkins]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *    Cornelius Perkins - initial API and implementation and/or initial documentation
 *
 * Author Cornelius Perkins (ccperkins at both github and bitbucket)
 ******************************************************************************
 */

public class FeSimpleGeoProxBenchmarks {
  String newline = System.lineSeparator();
  String separator = "\t";

  @Test
  public void doTimetests() {
    int times = 10;  // each test will be run this many times.
    Given given = givenAWorld_with_1126656_points_nearAntiMeridian();
    System.out.print(header());
    FeSimpleGeoProx<TestClientMapObject> world = given.world;
    warmupJit(world, times);
    timetest(given, 10.0, new LatLng(41.0, -179.0), times);
    timetest(given, 10.0, new LatLng(41, 180.0), times);
    timetest(given, 10.0, new LatLng(141.0, -179.0), times); // should find zero
    timetest(given, 1.0, new LatLng(41.0, -179.0), times);
    timetest(given, 1.0, new LatLng(41, 180.0), times);
    timetest(given, 1.0, new LatLng(141.0, -179.0), times); // should find zero
    timetest(given, 1000.0, new LatLng(41, 180.0), times); // should find all
    timetest(given, 400.0, new LatLng(41, 180.0), times);
    timetest(given, 400.0, new LatLng(141.0, -179.0), times); // should find zero

    for (long rr = 400; rr > 0; rr -= 20) {
      double radius = rr;
      timetest(given, radius, new LatLng(41, 180.0), times);
    }
  }

  void timetest(Given given, double radius, LatLng start, int times) {
    //System.out.println("Beginning test with given " + given + " (" + given.numPoints + " points, " + radius + " mile search");
    Results accum = iterate(given.world, start, radius, times);
    System.out.print(toRow(given, start, accum, times));
    // System.out.println("  Searching within " + radius + " of " + start + ", results:");
    // System.out.println("    Found:      " + accum.numFound + " points ");
    // System.out.println("    Bruteforce: " + accum.timeBruteForce);
    // System.out.println("    ProxMap:    " + accum.timeFeSimpleGeoProx);

  }

  void warmupJit(FeSimpleGeoProx<TestClientMapObject> world, int times) {
    iterate(world, new LatLng(0.0, 0.0), 1.0, times);
  }

  Results iterate(FeSimpleGeoProx<TestClientMapObject> world, LatLng start, double radius, int times) {
    Results accum = new Results(radius);
    Results bruteFirst = timetest_bruteFirst(world, start, radius, times);
    Results worldFirst = timetest_worldFirst(world, start, radius, times);
    accum.numFound = worldFirst.numFound;
    accum.timeBruteForce += bruteFirst.timeBruteForce;
    accum.timeFeSimpleGeoProx += bruteFirst.timeFeSimpleGeoProx;
    accum.timeBruteForce += worldFirst.timeBruteForce;
    accum.timeFeSimpleGeoProx += worldFirst.timeFeSimpleGeoProx;
    return accum;
  }

  Results timetest_bruteFirst(FeSimpleGeoProx<TestClientMapObject> world, LatLng start, double radius, int times) {
    long timeBruteForce = 0;
    long timeFeSimpleGeoProx = 0;
    long numFound = 0;
    for (int ii = 0; ii < times; ii++) {
      long t0 = System.currentTimeMillis();
      Collection<MapObjectHolder<TestClientMapObject>> pointsByBruteForce = FeSimpleGeoProxTest.searchByBruteForce(world, start, radius, LengthUnit.MILE);
      long t1 = System.currentTimeMillis();
      Collection<MapObjectHolder<TestClientMapObject>> pointsByWorldSearch = world.find(start, radius, LengthUnit.MILE);
      long t2 = System.currentTimeMillis();
      FeSimpleGeoProxTest.assertPointCollectionsEqual(pointsByBruteForce, pointsByWorldSearch);
      //System.out.println("    bruteFirst  returning " + new TimePair ((t2-t1), (t1-t0)));
      //System.out.println("      bruteFirst  found " + pointsByWorldSearch.size() + " points, returning " + new TimePair ((t2-t1), (t1-t0)));
      timeFeSimpleGeoProx += (t2 - t1);
      timeBruteForce += (t1 - t0);
      numFound = pointsByBruteForce.size();
    }
    return new Results(radius, numFound, timeFeSimpleGeoProx, timeBruteForce);
  }

  Results timetest_worldFirst(FeSimpleGeoProx<TestClientMapObject> world, LatLng start, double radius, int times) {
    long timeBruteForce = 0;
    long timeFeSimpleGeoProx = 0;
    long numFound = 0;
    for (int ii = 0; ii < times; ii++) {
      long t0 = System.currentTimeMillis();
      Collection<MapObjectHolder<TestClientMapObject>> pointsByWorldSearch = world.find(start, radius, LengthUnit.MILE);
      long t1 = System.currentTimeMillis();
      Collection<MapObjectHolder<TestClientMapObject>> pointsByBruteForce = FeSimpleGeoProxTest.searchByBruteForce(world, start, radius, LengthUnit.MILE);
      long t2 = System.currentTimeMillis();
      FeSimpleGeoProxTest.assertPointCollectionsEqual(pointsByBruteForce, pointsByWorldSearch);
      //System.out.println("    proxMap     returning " + new TimePair ((t1-t0), (t2-t1)));
      timeFeSimpleGeoProx += (t1 - t0);
      timeBruteForce += (t2 - t1);
      numFound = pointsByBruteForce.size();
    }
    return new Results(radius, numFound, timeFeSimpleGeoProx, timeBruteForce);
  }

  public Given givenAWorld_with_1126656_points_nearAntiMeridian() {
    long start = System.currentTimeMillis();
    LatLng eastCenter = new LatLng(41.0, -175.0);
    LatLng westCenter = new LatLng(41.0, 175.0);
    LatLng northCenter = new LatLng(46.0, 180.0);
    LatLng southCenter = new LatLng(36.0, 180.0);

    //		System.out.println("Making points in a box "
    //				+ LatLngTool.distance(eastCenter, westCenter, LengthUnit.MILE)
    //				+ " miles east-to-west, and " + LatLngTool.distance(northCenter, southCenter, LengthUnit.MILE)
    //				+ " miles north-to-south");
    Collection<MapObjectHolder<TestClientMapObject>> mapObjects = FeSimpleGeoProxTest.make_many_points(southCenter.getLatitude(), northCenter.getLatitude()
        , -180.0, eastCenter.getLongitude());
    mapObjects.addAll(FeSimpleGeoProxTest.make_many_points(southCenter.getLatitude(), northCenter.getLatitude()
        , 177.0, westCenter.getLongitude()));

    long timeToCreatePoints = System.currentTimeMillis() - start;
    System.out.println("Created test world with " + mapObjects.size() + " points in an area "
        + LatLngTool.distance(eastCenter, westCenter, LengthUnit.MILE) + " miles east-to-west"
        + " (" + eastCenter.getLongitude() + " to " + westCenter.getLongitude() + ")"
        + ", and "
        + LatLngTool.distance(northCenter, southCenter, LengthUnit.MILE) + " miles north-to-south"
        + " (" + northCenter.getLatitude() + " to " + southCenter.getLatitude() + ")"
        + "; which took " + timeToCreatePoints + " ms."
    );
    FeSimpleGeoProx<TestClientMapObject> world = new FeSimpleGeoProx<TestClientMapObject>(mapObjects);
    Given given = new Given(eastCenter, westCenter, northCenter, southCenter, world, mapObjects.size());

    return given;
  }

  String toRow(Given given, LatLng loc, Results results, int times) {
    StringBuilder sb = new StringBuilder();
    sb.append(given.numPoints).append(separator)
        .append(given.eastCenter.getLongitude()).append(separator)
        .append(given.westCenter.getLongitude()).append(separator)
        .append(given.northCenter.getLatitude()).append(separator)
        .append(given.southCenter.getLatitude()).append(separator)
        .append(loc).append(separator)
        .append(results.radius).append(separator)
        .append(results.numFound).append(separator)
        .append(results.timeBruteForce).append(separator)
        .append(results.timeFeSimpleGeoProx).append(separator)
        .append(times).append(separator)
        .append(newline);
    return sb.toString();

  }

  String header() {
    StringBuilder sb = new StringBuilder();
    sb.append("PointsInWorld").append(separator)
        .append("eastLongitude").append(separator)
        .append("westLongitude").append(separator)
        .append("northLatitude").append(separator)
        .append("southLatitude").append(separator)
        .append("startPoint").append(separator)
        .append("searchRadius").append(separator)
        .append("numFound").append(separator)
        .append("timeBruteForce").append(separator)
        .append("timeFeSimpleGeoProx").append(separator)
        .append("numRepetitions").append(separator)
        .append(newline);
    return sb.toString();
  }

  private class Results {
    public double radius;
    public long numFound;
    public long timeFeSimpleGeoProx;
    public long timeBruteForce;

    public Results(double radius) {
      this(radius, 0, 0, 0);
    }

    public Results(double radius, long numFound, long timeFeSimpleGeoProx, long timeBruteForce) {
      super();
      this.radius = radius;
      this.numFound = numFound;
      this.timeFeSimpleGeoProx = timeFeSimpleGeoProx;
      this.timeBruteForce = timeBruteForce;
    }

    @Override
    public String toString() {
      return "Results [radius=" + radius + ", numFound=" + numFound + ", timeFeSimpleGeoProx=" + timeFeSimpleGeoProx + ", timeBruteForce="
          + timeBruteForce + "]";
    }

  }

  private class Given {
    public LatLng eastCenter;
    public LatLng westCenter;
    public LatLng northCenter;
    public LatLng southCenter;
    public FeSimpleGeoProx<TestClientMapObject> world;
    public long numPoints;

    public Given(LatLng eastCenter, LatLng westCenter, LatLng northCenter, LatLng southCenter,
                 FeSimpleGeoProx<TestClientMapObject> world, long numPoints) {
      super();
      this.eastCenter = eastCenter;
      this.westCenter = westCenter;
      this.northCenter = northCenter;
      this.southCenter = southCenter;
      this.world = world;
      this.numPoints = numPoints;
    }

    @Override
    public String toString() {
      return "Given [numPoints=" + numPoints + ", eastCenter=" + eastCenter + ", westCenter=" + westCenter
          + ", northCenter=" + northCenter + ", southCenter=" + southCenter + "]";
    }

  }


}


package com.bpodgursky.uncharted.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bpodgursky.uncharted.datasets.Coordinate;
import com.bpodgursky.uncharted.datasets.StarRecord;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class CoordinateSpace {

  //  god forgive me
  private final Map<Integer, Map<Integer, Map<Integer, List<StarRecord>>>> buckets = Maps.newHashMap();

  public void add(StarRecord record) {
    Coordinate coordinates = record.getCartesianCoordsInLys();


    int xBucket = (int)coordinates.getX();
    int yBucket = (int)coordinates.getY();
    int zBucket = (int)coordinates.getZ();

    if (!buckets.containsKey(xBucket)) {
      buckets.put(xBucket, new HashMap<>());
    }

    Map<Integer, Map<Integer, List<StarRecord>>> xb = buckets.get(xBucket);

    if (!xb.containsKey(yBucket)) {
      xb.put(yBucket, new HashMap<>());
    }

    Map<Integer, List<StarRecord>> yb = xb.get(yBucket);

    if (!yb.containsKey(zBucket)) {
      yb.put(zBucket, Lists.newArrayList());
    }

    yb.get(zBucket).add(record);

  }


  public Set<Integer> getNeighborIDs(StarRecord record, double distance) {

    Coordinate coordinates = record.getCartesianCoordsInLys();

    int xBucket = (int)coordinates.getX();
    int yBucket = (int)coordinates.getY();
    int zBucket = (int)coordinates.getZ();

    List<StarRecord> potentialNeighbors = Lists.newArrayList();

    int maxLysOut = (int)Math.ceil(distance);

    Set<Integer> range = Sets.newHashSet();
    for (int i = 0; i <= maxLysOut; i++) {
      range.add(i);
      range.add(-i);
    }

    System.out.println("RANGE: " + range);

    for (Integer xDiff : range) {
      for (Integer yDiff : range) {
        for (Integer zDiff : range) {
          potentialNeighbors.addAll(collectFrom(xBucket + xDiff, yBucket + yDiff, zBucket + zDiff));
        }
      }
    }

    Set<Integer> withinRange = Sets.newHashSet();
    for (StarRecord potentialNeighbor : potentialNeighbors) {
      Coordinate neighCoords = potentialNeighbor.getCartesianCoordsInLys();
      if(distance(coordinates, neighCoords) < distance){
        withinRange.add(potentialNeighbor.getPrimaryId());
      }
    }

    return withinRange;
  }

  private double distance(Coordinate coord1, Coordinate coord2) {
    return Math.sqrt(
        Math.pow(coord1.getX() - coord2.getX(), 2) +
            Math.pow(coord1.getY() - coord2.getY(), 2) +
            Math.pow(coord1.getZ() - coord2.getZ(), 2)
    );

  }


  private List<StarRecord> collectFrom(int xCoord, int yCoord, int zCoord) {

    if (!buckets.containsKey(xCoord)) {
      return Lists.newArrayList();
    }

    Map<Integer, Map<Integer, List<StarRecord>>> xBucket = buckets.get(xCoord);

    if (!xBucket.containsKey(yCoord)) {
      return Lists.newArrayList();
    }

    Map<Integer, List<StarRecord>> yBucket = xBucket.get(yCoord);

    if (!yBucket.containsKey(zCoord)) {
      return Lists.newArrayList();
    }

    return yBucket.get(zCoord);

  }

}

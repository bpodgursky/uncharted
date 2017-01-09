package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.datasets.catalogs.ObjectValue;
import com.bpodgursky.uncharted.datasets.catalogs.Unit;
import com.bpodgursky.uncharted.datasets.catalogs.ValueSource;

public class PlanetData extends ObjectRecord{

  private final Integer planetId;
  private final int starId;
  private final PlanetName name;

  private final ObjectValue semiMajorAxisLys;
  private final ObjectValue semiMinorAxisLys;
  private final ObjectValue eccentricity;
  private final ObjectValue orbitalPeriodDays;
  private final ObjectValue inclination;
  private final ObjectValue massKg;
  private final ObjectValue radiusLys;
  private final ObjectValue densityGcc;
  private final ObjectValue longAscendingNode;
  private final ObjectValue argumentPerhelion;

  public static class PlanetName {

    private final String starName;
    private final String planetLetter;
    private final String commonName;

    public PlanetName(String starName, String planetLetter, String commonName) {
      this.starName = starName;
      this.planetLetter = planetLetter;
      this.commonName = commonName;
    }

    public String toString(){
      if(commonName != null){
        return commonName;
      }
      return starName+" "+planetLetter;
    }
  }

  public PlanetData(Integer planetId,
                    int starID,
                    PlanetName name,
                    ObjectValue semiMajorAxisLys,
                    ObjectValue eccentricity,
                    ObjectValue orbitalPeriodDays,
                    ObjectValue inclination,
                    ObjectValue massKg,
                    ObjectValue radiusLys,
                    ObjectValue densityGcc,
                    ObjectValue longAscendingNode,
                    ObjectValue argumentPerhelion){
    super(name.toString(), "PLANET");

    this.planetId = planetId;
    this.starId = starID;
    this.name = name;

    this.semiMajorAxisLys = semiMajorAxisLys;
    this.semiMinorAxisLys = computeSemiMinorAxis(semiMajorAxisLys, eccentricity);
    this.eccentricity = eccentricity;
    this.orbitalPeriodDays = orbitalPeriodDays;
    this.inclination = inclination;
    this.massKg = massKg;
    this.radiusLys = radiusLys;
    this.densityGcc = densityGcc;
    this.longAscendingNode = longAscendingNode;
    this.argumentPerhelion = argumentPerhelion;

    setRadius(radiusLys);
  }

  private ObjectValue computeSemiMinorAxis(ObjectValue major, ObjectValue eccentricity){
    return new ObjectValue(
        major.getValue().in(Unit.LY).getQuantity() * Math.sqrt(1 - Math.pow(eccentricity.getValue().getQuantity(), 2)),
        ValueSource.combine(major.getSource(), eccentricity.getSource()),
        Unit.LY
    );
  }

}

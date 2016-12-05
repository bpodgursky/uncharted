package com.bpodgursky.uncharted.datasets;

import java.util.Map;

import com.bpodgursky.uncharted.datasets.catalogs.PlanetValue;

public class PlanetData extends ObjectRecord{

  private final Integer planetId;
  private final int starId;
  private final PlanetName name;

  private final Map<PlanetProperties, PlanetValue> values;

  public static class PlanetName {

    private final String starName;
    private final String planetLetter;

    public PlanetName(String starName, String planetLetter) {
      this.starName = starName;
      this.planetLetter = planetLetter;
    }

    public String toString(){
      return starName+" "+planetLetter;
    }
  }

  public PlanetData(Integer planetId,
                    int starID,
                    PlanetName name,
                    Map<PlanetProperties, PlanetValue> values){
    super(name.toString(), "PLANET");

    this.planetId = planetId;
    this.starId = starID;
    this.name = name;
    this.values = values;

    setRadiusInLys(values.get(PlanetProperties.RADIUS_LYS).getValue());
  }

}

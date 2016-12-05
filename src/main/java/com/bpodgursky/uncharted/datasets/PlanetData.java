package com.bpodgursky.uncharted.datasets;

import java.util.Map;

import com.bpodgursky.uncharted.datasets.catalogs.PlanetValue;

public class PlanetData {

  private final Integer planetId;
  private final String starId;
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
                    String starID,
                    PlanetName name,
                    Map<PlanetProperties, PlanetValue> values){
    this.planetId = planetId;
    this.starId = starID;
    this.name = name;
    this.values = values;
  }

}

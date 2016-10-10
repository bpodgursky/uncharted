package com.bpodgursky.uncharted.datasets;

public class PlanetData {

  private final Integer planetId;
  private final String starId;
  private final PlanetName name;

  private final Double semiMajorAxisLys;
  private final Double eccentricity;
  private final Double orbitalPeriodDays;
  private final Double inclination;

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
                    Double semiMajorAxisLys,
                    Double eccentricity,
                    Double orbitalPeriodDays,
                    Double inclination){
    this.planetId = planetId;
    this.starId = starID;
    this.name = name;

    this.semiMajorAxisLys = semiMajorAxisLys;
    this.eccentricity = eccentricity;
    this.orbitalPeriodDays = orbitalPeriodDays;
    this.inclination = inclination;

  }

  @Override
  public String toString() {
    return "PlanetData{" +
        "planetId=" + planetId +
        ", starId='" + starId + '\'' +
        ", name=" + name +
        ", semiMajorAxisLys=" + semiMajorAxisLys +
        ", eccentricity=" + eccentricity +
        ", orbitalPeriodDays=" + orbitalPeriodDays +
        ", inclination=" + inclination +
        '}';
  }
}

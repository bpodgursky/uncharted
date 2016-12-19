package com.bpodgursky.uncharted.datasets;

import java.util.Set;

import com.bpodgursky.uncharted.datasets.catalogs.ObjectValue;
import com.bpodgursky.uncharted.datasets.catalogs.Unit;
import com.bpodgursky.uncharted.datasets.catalogs.ValueSource;

public class StarRecord extends ObjectRecord{

  private final StarIdentifiers identifiers;

  private final ObjectValue solDistance;
  private final Double rightAscensionRadians;
  private final Double declinationRadians;
  private final Double absoluteMagnitude;
  private final String rawStellarClassification;
  private final StellarClassification parsedStellarClassification;
  private final Coordinate cartesianCoordsInLys;

  private final ObjectValue temperatureEstimate;

  private Set<Integer> nearbyObjectIDs;

  public StarRecord(StarIdentifiers identifiers,
                    ExternalLinks links,
                    ObjectValue lightYearDistance,
                    Double rightAscensionRadians,
                    Double declinationRadians,
                    Double absoluteMagnitude,
                    String stellarClass,
                    Double colorIndex,
                    Double luminosity) {
    super(identifiers.getPrimaryName(), "STAR");
    this.identifiers = identifiers;
    this.solDistance = lightYearDistance;
    this.rightAscensionRadians = rightAscensionRadians;
    this.declinationRadians = declinationRadians;
    this.absoluteMagnitude = absoluteMagnitude;
    this.rawStellarClassification = stellarClass;
    this.parsedStellarClassification = StarClassHelper.parseClass(stellarClass);
    this.cartesianCoordsInLys = AstroConvert.equatorialToCartesian(rightAscensionRadians, declinationRadians, lightYearDistance.getValue().in(Unit.LY).getQuantity());

    if (colorIndex == null) {
      this.temperatureEstimate = new ObjectValue(StarClassHelper.getTemperatureEstimate(parsedStellarClassification), ValueSource.DEFAULT, Unit.K);
    } else {
      this.temperatureEstimate = new ObjectValue(AstroConvert.bvToTemperature(colorIndex), ValueSource.SUPPLIED, Unit.K);
    }

    setLinks(links);
    setRadius(new ObjectValue(AstroConvert.getRadiusLys(luminosity, temperatureEstimate.getValue()), ValueSource.INFERRED, Unit.LY));
  }

  public void setNearbyObjectIDs(Set<Integer> nearbyObjectIDs){
    this.nearbyObjectIDs = nearbyObjectIDs;
  }

  public StarIdentifiers getIdentifiers() {
    return identifiers;
  }

  public Double getSolDistance() {
    return solDistance.getValue().in(Unit.LY).getQuantity();
  }

  public Double getRightAscensionRadians() {
    return rightAscensionRadians;
  }

  public Double getDeclinationRadians() {
    return declinationRadians;
  }

  public Double getAbsoluteMagnitude() {
    return absoluteMagnitude;
  }

  public Coordinate getCartesianCoordsInLys() {
    return cartesianCoordsInLys;
  }

  public String getRawStellarClassification() {
    return rawStellarClassification;
  }

  public StellarClassification getParsedStellarClassification() {
    return parsedStellarClassification;
  }

}

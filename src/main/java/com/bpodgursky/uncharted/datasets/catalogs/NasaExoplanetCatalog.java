package com.bpodgursky.uncharted.datasets.catalogs;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import com.bpodgursky.uncharted.datasets.PlanetData;
import com.bpodgursky.uncharted.datasets.StarRecord;
import com.bpodgursky.uncharted.datasets.StellarLibrary;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NasaExoplanetCatalog implements ExoplanetCatalog {
  private static final Logger LOG = LoggerFactory.getLogger(NasaExoplanetCatalog.class);

  private final Multimap<Integer, PlanetData> allPlanetsByStarID = LinkedListMultimap.create();

  public NasaExoplanetCatalog(StellarLibrary library) throws IOException {

    Scanner nasaPlanets = new Scanner(new GZIPInputStream(HYGDatabase.class.getClassLoader()
        .getResourceAsStream("com/bpodgursky/uncharted/datasets/planets-nasa-10-9-16.csv.gz")));


    int planetCount = 0;


    String headerLine;
    do {
      headerLine = nasaPlanets.nextLine();
    } while (headerLine.charAt(0) == '#');

    nasaPlanets.nextLine();

    while (nasaPlanets.hasNext()) {
      String next = nasaPlanets.nextLine();


      String[] line = next.split(",");

      Integer planetID = Integer.parseInt(line[0]);
      String starName = line[1];
      String planetLetter = line[2];

      StarRecord starRecord = library.find(starName);

      //  ignore planets outside 75 lys for now
      if (!line[42].equals("") && Double.parseDouble(line[42]) > 75.0 / 0.306601) {
        continue;
      }

      String orbitalPeriodDaysRaw = line[5];
      String semiMajorAxisRaw = line[9];
      String eccentricityRaw = line[13];
      String inclinationRaw = line[17];

      String massRaw = line[21];
      String massCalc = line[25];
      String radiusRaw = line[26];
      String densityRaw = line[30];

      planetCount++;

      if (starRecord == null) {
        starRecord = library.find(line[166]);   //  HIPPARCOS
      }

      if (starRecord == null) {
        starRecord = library.find(line[165]);
      }

      if (starRecord != null) {
        allPlanetsByStarID.put(starRecord.getPrimaryId(), new PlanetData(
            planetID,
            starRecord.getPrimaryId(),
            new PlanetData.PlanetName(starName, planetLetter, null),
            ObjectValue.value(semiMajorAxisRaw, Unit.AU, Unit.LY, PlanetDefaults.DEFAULT_SEMI_MAJOR_AXIS),
            ObjectValue.value(eccentricityRaw, Unit.NONE, Unit.NONE, PlanetDefaults.DEFAULT_ECCENTRICITY),
            ObjectValue.value(orbitalPeriodDaysRaw, Unit.DAY, Unit.DAY, PlanetDefaults.DEFAULT_ORBITAL_PERIOD),
            ObjectValue.value(inclinationRaw, Unit.DEGREE_GEOM, Unit.DEGREE_GEOM, PlanetDefaults.DEFAULT_INCLINATION),
            ObjectValue.value(massRaw, Unit.MASS_JUP, Unit.KG, PlanetDefaults.DEFAULT_MASS, massCalc),
            ObjectValue.value(radiusRaw, Unit.RADIUS_JUP, Unit.LY, PlanetDefaults.DEFAULT_RADIUS),
            ObjectValue.value(densityRaw, Unit.G_PER_CC, Unit.G_PER_CC, PlanetDefaults.DENSITY),
            ObjectValue.value("", Unit.DEGREE_GEOM, Unit.DEGREE_GEOM, PlanetDefaults.DEFAULT_LONG_ASCENDING),
            ObjectValue.value("", Unit.DEGREE_GEOM, Unit.DEGREE_GEOM, PlanetDefaults.DEFAULT_ARGUMENT_PERHELION),
            ObjectValue.value("", Unit.DEGREE_GEOM, Unit.DEGREE_GEOM, PlanetDefaults.DEFAULT_AXIAL_TILT)
        ));
      } else {
        LOG.warn("Could not find star for name: " + starName);
      }

    }

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Earth"),
        new ObjectValue(1.5812e-5, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.0167086, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(365.256363, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(0.0, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(5.97237e24, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(6.7341e-10, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(5.514, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(0.0, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(0.0, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(23.4392811, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Mercury"),
        new ObjectValue(6.120989e-6, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.205630, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(87.969, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(7.005, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(3.3011e23, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(2.5787e-10, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(5.427, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(48.331, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(29.124, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(0.034, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Venus"),
        new ObjectValue(1.14376e-5, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.006772, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(224.701, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(3.39458, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(4.8675e24, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(6.39675765e-10, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(5.243, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(76.680, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(54.884, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(2.64, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Mars"),
        new ObjectValue(2.4093185478e-5, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.0934, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(686.971, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(1.850, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(6.4171e23, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(3.582704327e-10, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(3.9335, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(49.558, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(286.502, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(25.19, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Jupiter"),
        new ObjectValue(8.22661511e-5, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.048498, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(4332.59, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(1.303, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(1.8986e27, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(7.3895985e-9, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(1.326, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(100.464, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(273.867, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(3.13, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Saturn"),
        new ObjectValue(0.00015108706936, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.05555, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(10759.22, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(2.485240, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(5.6836e26, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(6.1551273e-9, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.687, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(113.665, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(339.392, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(26.73, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Uranus"),
        new ObjectValue(0.0003038910924, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.046381, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(30688.5, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(0.773, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(8.6810e25, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(2.6807655e-9, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(1.27, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(74.006, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(96.998857, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(97.77, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    allPlanetsByStarID.put(1, new PlanetData(
        null,
        1,
        new PlanetData.PlanetName("Sol", null, "Neptune"),
        new ObjectValue(0.00047612071755, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(0.009456, ValueSource.SUPPLIED, Unit.NONE),
        new ObjectValue(60182, ValueSource.SUPPLIED, Unit.DAY),
        new ObjectValue(1.767975, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(1.0243e26, ValueSource.SUPPLIED, Unit.KG),
        new ObjectValue(2.6025475e-9, ValueSource.SUPPLIED, Unit.LY),
        new ObjectValue(1.638, ValueSource.SUPPLIED, Unit.G_PER_CC),
        new ObjectValue(131.784, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(276.336, ValueSource.SUPPLIED, Unit.DEGREE_GEOM),
        new ObjectValue(28.32, ValueSource.SUPPLIED, Unit.DEGREE_GEOM)
    ));

    LOG.info("Found stars for " + allPlanetsByStarID.size() + "/" + planetCount + " total records");

  }

  @Override
  public Multimap<Integer, PlanetData> getAllPlanetsByStarID(Collection<StarRecord> stars) {

    Multimap<Integer, PlanetData> forStars = HashMultimap.create();
    for (StarRecord star : stars) {
      Integer starID = star.getPrimaryId();
      forStars.putAll(starID, allPlanetsByStarID.get(starID));
    }

    return forStars;

  }
}
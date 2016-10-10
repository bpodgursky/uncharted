package com.bpodgursky.uncharted.datasets.catalogs;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import com.bpodgursky.uncharted.datasets.AstroConvert;
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

  private final Multimap<String, PlanetData> allPlanetsByStarID = LinkedListMultimap.create();

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

      String orbitalPeriodDays = line[5];
      String semiMajorAxis = line[9];
      String eccentricity = line[13];
      String inclination = line[17];

      //  TODO try to calculate from each other?
      //  TODO some we can fake / ignore (ex inclination)
      if(semiMajorAxis.equals("") || eccentricity.equals("") || orbitalPeriodDays.equals("") || inclination.equals("")){
        continue;
      }

      planetCount++;

      if (starRecord == null) {
        starRecord = library.find(line[166]);   //  HIPPARCOS
      }

      if (starRecord == null) {
        starRecord = library.find(line[165]);
      }

      if (starRecord != null) {
        allPlanetsByStarID.put(starRecord.getIdentifiers().getPrimaryId(), new PlanetData(
            planetID,
            starRecord.getIdentifiers().getPrimaryId(),
            new PlanetData.PlanetName(starName, planetLetter),
            AstroConvert.auToLightyears(Double.parseDouble(semiMajorAxis)),
            Double.parseDouble(eccentricity),
            Double.parseDouble(orbitalPeriodDays),
            Double.parseDouble(inclination)
        ));
      } else {
        LOG.warn("Could not find star for name: " + starName);
      }

    }

    LOG.info("Found stars for " + allPlanetsByStarID.size() + "/" + planetCount + " total records");

  }

  @Override
  public Multimap<String, PlanetData> getAllPlanetsByStarID(Collection<StarRecord> stars) {

    Multimap<String, PlanetData> forStars = HashMultimap.create();
    for (StarRecord star : stars) {
      String starID = star.getIdentifiers().getPrimaryId();
      forStars.putAll(starID, allPlanetsByStarID.get(starID));
    }

    return forStars;

  }
}
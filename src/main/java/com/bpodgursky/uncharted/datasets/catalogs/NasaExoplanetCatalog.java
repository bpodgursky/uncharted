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
            new PlanetData.PlanetName(starName, planetLetter),
            PlanetDefaults.getPlanetProperties(
                semiMajorAxisRaw,
                eccentricityRaw,
                orbitalPeriodDaysRaw,
                inclinationRaw,
                massRaw,
                radiusRaw,
                densityRaw
            )
        ));
      } else {
//        LOG.warn("Could not find star for name: " + starName);
      }

    }

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
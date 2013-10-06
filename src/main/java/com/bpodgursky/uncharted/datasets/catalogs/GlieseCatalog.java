package com.bpodgursky.uncharted.datasets.catalogs;

import com.bpodgursky.uncharted.datasets.AstroConvert;
import com.bpodgursky.uncharted.datasets.DatasetName;
import com.bpodgursky.uncharted.datasets.ExternalLinks;
import com.bpodgursky.uncharted.datasets.StarIdentifiers;
import com.bpodgursky.uncharted.datasets.StarRecord;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

/**
 * super hacky text dump of the Gliese 3 catalog
 * <p/>
 */
public class GlieseCatalog implements StarCatalog {

  private final Map<String, StarRecord> starsByName;

  public GlieseCatalog() throws IOException {

    starsByName = Maps.newHashMap();

    InputStream resourceAsStream = GlieseCatalog.class.getClassLoader().getResourceAsStream("com/bpodgursky/uncharted/datasets/gliese_3_catalog.txt.gz");
    GZIPInputStream gzis = new GZIPInputStream(resourceAsStream);
    Scanner scan = new Scanner(gzis);

    scan.nextLine();  //  source
    scan.nextLine();  //  blank
    scan.nextLine();  //  source
    scan.nextLine();  //  coordinate system
    scan.nextLine();  //  headers

    Map<DatasetName, String> sunIds = Maps.newHashMap();
    sunIds.put(DatasetName.GLIESE_ID, "Sun");
    sunIds.put(DatasetName.PROPER_NAME, "The Sun");

    StarIdentifiers sunIdentifiers = new StarIdentifiers("Sun");
    sunIdentifiers.setProperName("The Sun");
    sunIdentifiers.setGlieseId("Sun");

    starsByName.put("Sol",
        new StarRecord(
            sunIdentifiers,
            new ExternalLinks(),
            0.0,
            0.0,
            0.0,
            4.83,
            "G2V"
        ));

    while (scan.hasNext()) {
      String line = scan.nextLine();
      String[] split = line.split("\\|");

//      Map<DatasetName, String> identitiers = Maps.newHashMap();

      String glieseName = split[1].trim();

      StarIdentifiers identifiers = new StarIdentifiers(glieseName);
      identifiers.setGlieseId(glieseName);

      String otherName = safeTrim(split[41]);
      if (otherName != null) {
        identifiers.setProperName(otherName);
      }

      Double parallax = parseOrNull(split[6]);
      if (parallax != null) {
        StarRecord record = new StarRecord(
            identifiers,
            new ExternalLinks(),
            AstroConvert.parallaxToLightyears(parseOrNull(split[6])),
            AstroConvert.parseDegreeMinSec(split[2]),
            AstroConvert.parseDegreeMinSec(split[3]),
            parseOrNull(split[32]),
            safeTrim(split[5])
        );

        starsByName.put(glieseName, record);
      }
    }
  }

  public StarRecord getStar(String name) {
    return starsByName.get(name);
  }


  private static String safeTrim(String s) {
    if (s == null) {
      return null;
    }
    return s.trim();
  }

  private static Double parseOrNull(String s) {
    if (s == null) {
      return null;
    }

    String trimmed = s.trim();

    if (trimmed.isEmpty()) {
      return null;
    }

    return Double.parseDouble(s);
  }

  @Override
  public Collection<StarRecord> getAllStars(final double maxLyDistance) {
    return Collections2.filter(starsByName.values(), new Predicate<StarRecord>() {
      @Override
      public boolean apply(StarRecord input) {
        return input.getLightYearDistance() <= maxLyDistance;
      }
    });
  }
}

package com.bpodgursky.uncharted.datasets;

import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

/**
 * super hacky text dump of the Gliese 3 catalog
 *
 * TODO abstract?  I guess?
 *
 */
public class GlieseCatalog {

  private final Map<String, Star> starsByName;

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
    scan.nextLine();  //  sun

    while (scan.hasNext()) {
      String line = scan.nextLine();
      String[] split = line.split("\\|");

      Star star = new Star(safeTrim(split[1]),
          Angle.parse(split[2]),
          Angle.parse(split[3]),
          parseOrNull(split[4]),
          safeTrim(split[5]),
          parseOrNull(split[6]),
          parseOrNull(split[7]),
          safeTrim(split[8]),
          safeTrim(split[9]),
          parseOrNull(split[10]),
          parseOrNull(split[11]),
          parseOrNull(split[12]),
          safeTrim(split[13]),
          parseOrNull(split[14]),
          parseOrNull(split[15]),
          safeTrim(split[16]),
          safeTrim(split[17]),
          safeTrim(split[18]),
          safeTrim(split[19]),
          safeTrim(split[20]),
          safeTrim(split[21]),
          safeTrim(split[22]),
          safeTrim(split[23]),
          safeTrim(split[24]),
          safeTrim(split[25]),
          safeTrim(split[26]),
          safeTrim(split[27]),
          safeTrim(split[28]),
          parseOrNull(split[29]),
          parseOrNull(split[30]),
          safeTrim(split[31]),
          parseOrNull(split[32]),
          safeTrim(split[33]),
          parseOrNull(split[34]),
          parseOrNull(split[35]),
          parseOrNull(split[36]),
          safeTrim(split[37]),
          safeTrim(split[38]),
          safeTrim(split[39]),
          safeTrim(split[40]),
          safeTrim(split[41]),
          safeTrim(split[42]),
          safeTrim(split[43]));

      starsByName.put(star.getName(), star);

    }
  }

  public Collection<Star> getAllStars() {
    return starsByName.values();
  }

  public Star getStar(String name) {
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


}

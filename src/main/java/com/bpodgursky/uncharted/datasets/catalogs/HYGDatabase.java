package com.bpodgursky.uncharted.datasets.catalogs;

import com.bpodgursky.uncharted.datasets.AstroConvert;
import com.bpodgursky.uncharted.datasets.DatasetName;
import com.bpodgursky.uncharted.datasets.StarRecord;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class HYGDatabase implements StarCatalog {
  private static final Logger LOG = LoggerFactory.getLogger(HYGDatabase.class);

  private final Set<StarRecord> stars;

  public HYGDatabase() throws IOException {

    stars = Sets.newHashSet();

    InputStream resourceAsStream = GlieseCatalog.class.getClassLoader().getResourceAsStream("com/bpodgursky/uncharted/datasets/hygxyz.csv.gz");

    GZIPInputStream gzis = new GZIPInputStream(resourceAsStream);
    Scanner scan = new Scanner(gzis);

    //  header
    scan.nextLine();

    //  StarID,HIP,HD,HR,Gliese,BayerFlamsteed,ProperName,RA,Dec,Distance,PMRA,PMDec,RV,Mag,AbsMag,Spectrum,ColorIndex,X,Y,Z,VX,VY,VZ
    while(scan.hasNext()){
      String line = scan.nextLine();
      String[] split = line.split(",");

      Map<DatasetName, String> identifiers = Maps.newHashMap();

      String hipparcosID = split[1];
      if(!hipparcosID.equals("") && !hipparcosID.equals("0")){
        identifiers.put(DatasetName.HIPPARCOS_ID, hipparcosID);
      }

      String henryDraperID = split[2];
      if(!henryDraperID.equals("")){
        identifiers.put(DatasetName.HENRY_DRAPER_ID, henryDraperID);
      }

      String harvardRevisedId = split[3];
      if(!harvardRevisedId.equals("")){
        identifiers.put(DatasetName.HARVARD_REVISED_ID, harvardRevisedId);
      }

      String glieseId = split[4];
      if(!glieseId.equals("")){
        identifiers.put(DatasetName.GLIESE_ID, glieseId);
      }

      String bayerFlamsteed = split[5];
      if(!bayerFlamsteed.equals("")){
        identifiers.put(DatasetName.BAYER_FLAMSTEED, bayerFlamsteed);
      }

      String properName = split[6];
      if(!properName.equals("")){
        identifiers.put(DatasetName.PROPER_NAME, properName);
      }

      String rightAscension = split[7];
      String declination = split[8];
      String distanceParsecs = split[9];

      if(!rightAscension.equals("") && !declination.equals("")
          && !distanceParsecs.equals("") && !distanceParsecs.equals("10000000")){

        String absMag = split[14];

        StarRecord record = new StarRecord(
            split[0],
            identifiers,
            AstroConvert.parsecsToLightyears(Double.parseDouble(distanceParsecs)),
            AstroConvert.hoursToRadians(Double.parseDouble(rightAscension)),
            AstroConvert.degreesToRadians(Double.parseDouble(declination)),
            Double.parseDouble(absMag),
            split[15]
        );

        stars.add(record);
      }

    }

  }

  @Override
  public Collection<StarRecord> getAllStars(final double maxLyDistance) {
    LOG.info("Filtering "+stars.size()+" stars for distance "+maxLyDistance);
    Collection<StarRecord> filteredSet = Collections2.filter(stars, new Predicate<StarRecord>() {
      @Override
      public boolean apply(StarRecord input) {
        return input.getLightYearDistance() <= maxLyDistance;
      }
    });
    LOG.info("Found "+filteredSet.size()+" stars within "+maxLyDistance);

    return filteredSet;
  }
}

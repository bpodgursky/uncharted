package com.bpodgursky.uncharted.datasets.catalogs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import com.bpodgursky.uncharted.datasets.AstroConvert;
import com.bpodgursky.uncharted.datasets.ExternalLinks;
import com.bpodgursky.uncharted.datasets.StarIdentifiers;
import com.bpodgursky.uncharted.datasets.StarRecord;
import com.bpodgursky.uncharted.datasets.identifiers.BayerFlamsteed;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HYGDatabase implements StarCatalog {
  private static final Logger LOG = LoggerFactory.getLogger(HYGDatabase.class);

  private final Set<StarRecord> stars;

  public HYGDatabase() throws IOException {

    stars = Sets.newHashSet();

    Scanner wikiLinks = new Scanner(new GZIPInputStream(HYGDatabase.class.getClassLoader()
        .getResourceAsStream("com/bpodgursky/uncharted/datasets/hyg_wikipedia_links.txt.gz")));

    Map<String, String> idToWikiLink = Maps.newHashMap();

    while(wikiLinks.hasNext()){
      String line = wikiLinks.nextLine();
      String[] split = line.split("\t");
      idToWikiLink.put(split[0], split[1]);
    }

    InputStream resourceAsStream = HYGDatabase.class.getClassLoader().getResourceAsStream("com/bpodgursky/uncharted/datasets/hygdata_v3.csv.gz");

    GZIPInputStream gzis = new GZIPInputStream(resourceAsStream);
    Scanner scan = new Scanner(gzis);

    //  header
    scan.nextLine();

    //  StarID,HIP,HD,HR,Gliese,BayerFlamsteed,ProperName,RA,Dec,Distance,PMRA,PMDec,RV,Mag,AbsMag,Spectrum,ColorIndex,X,Y,Z,VX,VY,VZ
    while(scan.hasNext()){
      String line = scan.nextLine();
      String[] split = line.split(",");

      //  id,
      String mainId = split[0];

      StarIdentifiers identifiers = new StarIdentifiers(mainId);


      //  hip
      String hipparcosID = split[1];
      if(!hipparcosID.equals("") && !hipparcosID.equals("0")){
        identifiers.setHipparcosId(hipparcosID);
      }

      // hd
      String henryDraperID = split[2];
      if(!henryDraperID.equals("")){
        identifiers.setHenryDraperId(henryDraperID);
      }

      //        ,hr
      String harvardRevisedId = split[3];
      if(!harvardRevisedId.equals("")){
        identifiers.setHarvardRevisedId(harvardRevisedId);
      }

      // ,gl
      String glieseId = split[4];
      if(!glieseId.equals("")){
        identifiers.setGlieseId(glieseId);
      }

      //  ,bf,
      String bayerFlamsteed = split[5];
      if(!bayerFlamsteed.equals("")){
        System.out.println(line);
        identifiers.setBayerFlamsteed(BayerFlamsteed.parse(bayerFlamsteed));
      }

      // proper,
      String properName = split[6];
      if(!properName.equals("")){
        identifiers.setProperName(properName);
      }

      // ra,
      String rightAscension = split[7];
      // dec,
      String declination = split[8];
      // dist,
      String distanceParsecs = split[9];

      // pmra,pmdec,rv,mag,absmag,spect,ci,x,y,z,vx,vy,vz,rarad,decrad,pmrarad,pmdecrad,bayer,flam,con,comp,comp_primary,base,lum,var,var_min,var_max


      if(!rightAscension.equals("") && !declination.equals("")
          && !distanceParsecs.equals("") && !distanceParsecs.equals("10000000")){

        String absMag = split[14];

        ExternalLinks links = new ExternalLinks();

        if(idToWikiLink.containsKey(mainId)){
          links.setWikipedia(idToWikiLink.get(mainId));
        }

        Double luminosity = Double.parseDouble(split[33]);

        StarRecord record = new StarRecord(
            identifiers,
            links,
            AstroConvert.parsecsToLightyears(Double.parseDouble(distanceParsecs)),
            AstroConvert.hoursToRadians(Double.parseDouble(rightAscension)),
            AstroConvert.degreesToRadians(Double.parseDouble(declination)),
            Double.parseDouble(absMag),
            split[15],
            parseOrNull(split[16]),
            luminosity
        );

        stars.add(record);
      }

    }

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

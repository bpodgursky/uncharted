package com.bpodgursky.uncharted.datasets;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bpodgursky.uncharted.datasets.identifiers.BayerFlamsteed;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StellarLibrary {
  private static final Logger LOG = LoggerFactory.getLogger(StellarLibrary.class);

  private static final Pattern GL_PATTERN = Pattern.compile("(Gl|GJ|NN|Wo) ([0-9]+\\.?[0-9]?[A-Z]?)");
  private static final Pattern HIP_PATTERN = Pattern.compile("HIP ([0-9]+)");
  private static final Pattern HD_PATTERN = Pattern.compile("HD ([0-9]+)");

  private final Map<String, StarRecord> starsByGJId = Maps.newHashMap();
  private final Map<BayerFlamsteed, StarRecord> starsByBFLId = Maps.newHashMap();
  private final Map<String, StarRecord> starsByHDId = Maps.newHashMap();
  private final Map<String, StarRecord> starsByHipId = Maps.newHashMap();

  public StellarLibrary(Collection<StarRecord> records) {

    for (StarRecord record : records) {
      StarIdentifiers ids = record.getIdentifiers();

      String glieseName = ids.getGlieseId();
      if (glieseName != null) {
        starsByGJId.put(getGJ(glieseName, true), record);
      }

      BayerFlamsteed bayerFlamsteed = ids.getBayerFlamsteed();
      if(bayerFlamsteed != null) {
        starsByBFLId.put(bayerFlamsteed, record);
      }

      String hdId = ids.getHenryDraperId();
      if(hdId != null){
        starsByHDId.put(hdId, record);
      }

      String hipparcosId = ids.getHipparcosId();
      if(hipparcosId != null){
        starsByHipId.put(hipparcosId, record);
      }

    }

  }

  private String getGJ(String name, boolean verify) {
    return get(GL_PATTERN, name, verify, 2);
  }

  private String getHIP(String name, boolean verify){
    return get(HIP_PATTERN, name, verify, 1);
  }

  private String getHD(String name, boolean verify){
    return get(HD_PATTERN, name, verify, 1);
  }

  private String get(Pattern pattern, String name, boolean verify, int capGroup){

    Matcher matcher = pattern.matcher(name);
    boolean matches = matcher.matches();
    if(!matches && verify){
      throw new RuntimeException("Unable to parse ID: " + name);
    }

    if(matches){
      return matcher.group(capGroup);
    }

    return null;

  }

  public Map<String, StarRecord> getStarsByHDId() {
    return starsByHDId;
  }

  public StarRecord find(String name) throws IOException {

    String gj = getGJ(name, false);
    if (gj != null) {
      StarRecord starRecord = starsByGJId.get(gj);
      if(starRecord != null){
        return starRecord;
      }else{
        LOG.warn("Could not find star with GJ id: "+gj);
      }
    }

    BayerFlamsteed bayerFlamsteedID = BayerFlamsteed.parse(name);
    if(bayerFlamsteedID != null){
      StarRecord starRecord = starsByBFLId.get(bayerFlamsteedID);
      if(starRecord != null){
        return starRecord;
      }else{
        LOG.warn("Could not find star with BF id: "+bayerFlamsteedID);
      }
    }

    String hip = getHIP(name, false);
    if(hip != null){
      StarRecord record = starsByHipId.get(hip);
      if(record != null){
        return record;
      }else{
        LOG.warn("Could not find star with hipparcos ID: "+ hip);
      }
    }

    String hd = getHD(name, false);
    if(hd != null){
      StarRecord record = starsByHDId.get(hd);
      if(record != null){
        return record;
      }else{
        LOG.warn("Could not find star with HD ID: "+ hd);
      }
    }

    return null;

  }
}

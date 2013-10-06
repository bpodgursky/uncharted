package com.bpodgursky.uncharted.datasets;

import com.bpodgursky.uncharted.datasets.catalogs.GlieseCatalog;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class ConstellationCorpus {

  private final Map<String, Constellation> abbrToConstellation = Maps.newHashMap();

  public ConstellationCorpus() throws IOException {
    InputStream resourceAsStream = GlieseCatalog.class.getClassLoader().getResourceAsStream("com/bpodgursky/uncharted/datasets/constellations.txt.gz");
    GZIPInputStream gzis = new GZIPInputStream(resourceAsStream);
    Scanner scan = new Scanner(gzis);

    while(scan.hasNext()){
      String line = scan.nextLine();
      String[] split = line.split("\t");
      abbrToConstellation.put(split[0], new Constellation(split[1], split[2]));
    }
  }

  public Constellation get(String abbr){
    return abbrToConstellation.get(abbr);
  }

  private static ConstellationCorpus corpus;
  public static ConstellationCorpus get() throws IOException {
    if(corpus == null){
      corpus = new ConstellationCorpus();
    }
    return corpus;
  }
}

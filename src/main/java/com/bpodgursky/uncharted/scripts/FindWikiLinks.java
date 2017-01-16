package com.bpodgursky.uncharted.scripts;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.bpodgursky.uncharted.datasets.Constellation;
import com.bpodgursky.uncharted.datasets.ConstellationCorpus;
import com.bpodgursky.uncharted.datasets.StarRecord;
import com.bpodgursky.uncharted.datasets.catalogs.HYGDatabase;
import com.bpodgursky.uncharted.datasets.identifiers.BayerFlamsteed;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FindWikiLinks {

  public static void main(String[] args) throws IOException {

    HYGDatabase hygDatabase = new HYGDatabase();

    FileWriter matchFile = new FileWriter("hyg_wikipedia_links.txt");

    for (StarRecord record : hygDatabase.getAllStars(Integer.MAX_VALUE)) {

      String properName = record.getIdentifiers().getProperName();
      String foundUrl = fetch(properName);

      //  kinda ghetto but seems to work :/
      if(foundUrl == null && properName != null){
        String[] parts = properName.split(" ");
        Constellation cons = ConstellationCorpus.get().get(parts[parts.length - 1]);
        if(cons != null){
          parts[parts.length-1] = cons.getGenitive();
          foundUrl = fetch(StringUtils.join(parts, "_"));
        }
      }

      if(foundUrl == null){
        foundUrl = findLink(record.getIdentifiers().getBayerFlamsteed());
      }

      if(foundUrl != null){
        System.out.println("Found: "+ foundUrl+"\t"+record.getIdentifiers());
        matchFile.write(record.getPrimaryId() + "\t" + foundUrl + "\n");
      }

      //  if we had a reasonable chance...
      else if(properName != null || record.getIdentifiers().getBayerFlamsteed() != null){
        System.out.println("Missing: "+record.getIdentifiers());
      }
    }

    matchFile.close();

  }

  private static String findLink(BayerFlamsteed bf) throws UnsupportedEncodingException {
    if(bf != null){

      //  try the whole name
      String prettyDesig = fetch(bf.getPrettyName());
      if(prettyDesig != null){
        return prettyDesig;
      }

      //  some other combinations

      if(bf.getBayerDesignation() != null && bf.getNumber() != null && bf.getConstellation() != null){
        String shorter = fetch(bf.getBayerDesignation()+bf.getNumber() + "_" + bf.getConstellation().getGenitive());
        if(shorter != null){
          return shorter;
        }
      }

      if(bf.getBayerDesignation() != null && bf.getConstellation() != null){
        String shorter = fetch(bf.getBayerDesignation() + "_" + bf.getConstellation().getGenitive());
        if(shorter != null){
          return shorter;
        }
      }

      if(bf.getFlamsteedDesignation() != null && bf.getConstellation() != null){
        String shorter = fetch(bf.getFlamsteedDesignation() + "_" + bf.getConstellation().getGenitive());
        if(shorter != null){
          return shorter;
        }
      }
    }

    return null;
  }

  private static String fetch(String target) throws UnsupportedEncodingException {
    if(target == null){
      return null;
    }

    try{

      //  don't hose wikipedia
      Thread.sleep(1000);

      String url = "http://en.wikipedia.org/wiki/" + URLEncoder.encode(target.replaceAll(" ", "_"), "utf-8");
      Document doc = Jsoup.connect(url).get();

      Elements select = doc.select(".noarticletext");
      if(select.size() == 0){
        return url;
      }

    }catch(Exception e){
      //  nope
    }

    return null;
  }
}

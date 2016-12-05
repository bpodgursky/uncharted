package com.bpodgursky.uncharted.datasets.catalogs;

import java.util.Collection;

import com.bpodgursky.uncharted.datasets.PlanetData;
import com.bpodgursky.uncharted.datasets.StarRecord;
import com.google.common.collect.Multimap;

public interface ExoplanetCatalog {
  public Multimap<Integer, PlanetData> getAllPlanetsByStarID(Collection<StarRecord> stars);
}

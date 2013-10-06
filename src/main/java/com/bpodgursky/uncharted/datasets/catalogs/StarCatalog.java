package com.bpodgursky.uncharted.datasets.catalogs;

import com.bpodgursky.uncharted.datasets.StarRecord;

import java.util.Collection;

public interface StarCatalog {
  public Collection<StarRecord> getAllStars(final double maxLyDistance);
}

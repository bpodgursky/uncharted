package com.bpodgursky.uncharted.api;

import com.bpodgursky.uncharted.datasets.GlieseCatalog;
import com.bpodgursky.uncharted.datasets.Star;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllGliese extends HttpServlet {

  private final Gson gson = new Gson();
  private final GlieseCatalog catalog;

  public AllGliese(GlieseCatalog catalog) {
    this.catalog = catalog;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      JSONArray array = new JSONArray();
      for (Star star : catalog.getAllStars()) {
        array.put(new JSONObject(gson.toJson(star)));
      }
      resp.getWriter().write(array.toString());
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }
}

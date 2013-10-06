package com.bpodgursky.uncharted.api;

import com.bpodgursky.uncharted.datasets.StarRecord;
import com.bpodgursky.uncharted.datasets.catalogs.StarCatalog;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StarCatalogServlet extends HttpServlet {

  private final Gson gson = new Gson();
  private final StarCatalog catalog;

  public StarCatalogServlet(StarCatalog catalog) {
    this.catalog = catalog;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      JSONArray array = new JSONArray();
      for (StarRecord star : catalog.getAllStars(Double.parseDouble(req.getParameter("max_lys")))) {
        array.put(new JSONObject(gson.toJson(star)));
      }
      resp.getWriter().write(array.toString());
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }
}

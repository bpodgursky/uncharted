package com.bpodgursky.uncharted;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;
import java.util.concurrent.Semaphore;

import com.bpodgursky.uncharted.api.StarCatalogServlet;
import com.bpodgursky.uncharted.datasets.StellarLibrary;
import com.bpodgursky.uncharted.datasets.catalogs.HYGDatabase;
import com.bpodgursky.uncharted.datasets.catalogs.NasaExoplanetCatalog;
import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.GzipFilter;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebServer implements Runnable {
  public static final int DEFAULT_PORT = 42315;


  private final Semaphore shutdownLock = new Semaphore(0);

  public WebServer(){}

  public final void shutdown() {
    shutdownLock.release();
  }

  @Override
  public void run() {
    try {

      Server uiServer = new Server(DEFAULT_PORT);
      final URL warUrl = uiServer.getClass().getClassLoader().getResource("com/bpodgursky/uncharted/www");
      final String warUrlString = warUrl.toExternalForm();

      HYGDatabase hygDatabase = new HYGDatabase();
      NasaExoplanetCatalog planetCatalog = new NasaExoplanetCatalog(new StellarLibrary(hygDatabase.getAllStars(75.0)));




      WebAppContext context = new WebAppContext(warUrlString, "/");
      context.addServlet(new ServletHolder(new StarCatalogServlet(hygDatabase, planetCatalog)), "/star_catalog");
      context.addFilter(GzipFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

      uiServer.setHandler(context);

      uiServer.start();

      shutdownLock.acquire();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    DOMConfigurator.configure(WebServer.class.getResource("/com/bpodgursky/uncharted/log4j.xml"));

    WebServer server = new WebServer();
    Thread thread1 = new Thread(server);

    thread1.start();
    thread1.join();
  }
}

package com.bpodgursky.uncharted;

import com.bpodgursky.uncharted.api.AllGliese;
import com.bpodgursky.uncharted.datasets.GlieseCatalog;
import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.GzipFilter;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;
import java.util.concurrent.Semaphore;

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

      WebAppContext context = new WebAppContext(warUrlString, "/");
      context.addServlet(new ServletHolder(new AllGliese(new GlieseCatalog())), "/all_gliese");
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

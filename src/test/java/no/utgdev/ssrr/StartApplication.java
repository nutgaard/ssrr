package no.utgdev.ssrr;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartApplication {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        String rootPath = StartApplication.class.getClassLoader().getResource(".").toString();
        WebAppContext webapp = new WebAppContext(rootPath + "../../src/main/webapp", "");

        server.setHandler(webapp);

        server.start();
        server.join();
    }
}

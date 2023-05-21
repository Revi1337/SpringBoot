package org.example.container;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.example.servlet.HelloServlet;

public class AppInitV1Servlet implements AppInit{

    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV1Servlet.onStartup");
        ServletRegistration.Dynamic helloServlet =
                servletContext.addServlet("helloServlet", new HelloServlet());
        helloServlet.addMapping("/hello-servlet");
    }
}

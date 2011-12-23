package com.fb.restbucks.integration;

import com.fb.restbucks.servlet.AppInitializer;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.springframework.web.SpringServletContainerInitializer;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author dan
 */
public class Tomcat {
    
    public static final AtomicBoolean started = new AtomicBoolean(false);

    public void start(String contextPath, int port) throws LifecycleException {
        if(started.get()) {
            return;
        }

        org.apache.catalina.startup.Tomcat tomcat = new org.apache.catalina.startup.Tomcat();
        tomcat.setBaseDir(".");
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(".");

        // Add AprLifecycleListener
        StandardServer server = (StandardServer)tomcat.getServer();

        LifecycleListener listener = new AprLifecycleListener();
        server.addLifecycleListener(listener);

        File baseDir = new File("target/tomcat7");
        baseDir.mkdir();

        Context context = tomcat.addContext(contextPath, baseDir.getAbsolutePath());
        HashSet<Class<?>> initialisers = new HashSet<Class<?>>();
        initialisers.add(AppInitializer.class);
        context.addServletContainerInitializer(new SpringServletContainerInitializer(), initialisers);
        tomcat.start();
        started.set(true);
    }
}

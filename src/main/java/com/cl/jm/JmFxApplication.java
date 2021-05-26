package com.cl.jm;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cl.jm.fx.MySplashScreen;
import com.cl.jm.view.HomeViewFxml;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;

@SpringBootApplication
public class JmFxApplication extends AbstractJavaFxApplicationSupport {

	public static void main(String[] args) {
		launchApp(JmFxApplication.class, HomeViewFxml.class, new MySplashScreen(), args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		super.start(stage);
	}

}

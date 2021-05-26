package com.cl.jm.fx;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MySplashScreen extends SplashScreen {

	@Override
	public boolean visible() {
		return true;
	}

	@Override
	public Parent getParent() {
		ImageView imageView = new ImageView(getClass().getResource(getImagePath()).toExternalForm());
//		ProgressIndicator splashProgressBar = new ProgressIndicator ();
//        splashProgressBar.setPrefWidth(imageView.getImage().getWidth());
//        splashProgressBar.setLayoutY(200);
        
        AnchorPane aPane = new AnchorPane();
        aPane.getChildren().addAll(imageView);
        
        //vbox.getChildren().addAll(imageView, splashProgressBar);
        
        return aPane;
	}

	@Override
	public String getImagePath() {
		return "/images/load.gif";
	}
	
	
	
}

package com.cl.jm.jxcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@FXMLController
public class WebviewController implements Initializable {

	@FXML
	private WebView webView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		WebEngine webEngine = webView.getEngine();
		webEngine.load("http://120.78.134.126:9090/jm/#/");
	}

}

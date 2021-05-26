package com.cl.jm.view;

import java.util.ResourceBundle;

public enum FxmlView {
    MAIN {
        @Override
		public String title() {
            return getStringFromResourceBundle("app.title");
        }

        @Override
		public String fxml() {
            return "/template/Home.fxml";
        }

    };
	
    
    public abstract String title();
    public abstract String fxml();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}

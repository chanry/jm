package com.cl.jm.jxcontroller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.cl.jm.JmApplication;
import com.cl.jm.JmFxApplication;
import com.cl.jm.entity.JMInfo;
import com.cl.jm.exception.JmException;
import com.cl.jm.model.WebSite;
import com.cl.jm.service.JmServiceImpl;
import com.cl.jm.utils.StringUtil;
import com.cl.jm.view.WebViewFxml;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

@FXMLController
public class HomeController implements Initializable {

	@Autowired
	private JmServiceImpl jmservice;

	private JmApplication mainApp;

	@FXML
	private TextArea idsField;

	@FXML
	private ComboBox<WebSite> siteSelectField;

	@FXML
	private TableView<JMInfo> jmTable;
	@FXML
	private TableColumn<JMInfo, String> idColumn;
	@FXML
	private TableColumn<JMInfo, String> moneyColumn;
	@FXML
	private TableColumn<JMInfo, String> nameColumn;
	@FXML
	private TableColumn<JMInfo, String> urlColumn;
	@FXML
	private Label totalLabel;
	

	private int total;

	public HomeController() {

	}

	public HomeController(JmApplication mainApp) {
		this.mainApp = mainApp;
	}

	public JmApplication getMainApp() {
		return mainApp;
	}

	public void setMainApp(JmApplication mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL initUrl, ResourceBundle resources) {
		WebSite site = new WebSite("LL", "溜溜网");

		siteSelectField.getItems().addAll(
				site,
				new WebSite("JE", "建E网"),
				new WebSite("OM", "欧模网"),
				new WebSite("ZM", "知末网"));
		siteSelectField.converterProperty().set(new StringConverter<WebSite>() {
			@Override
			public String toString(WebSite object) {
				return object.getName();
			}

			@Override
			public WebSite fromString(String string) {
				return null;
			}
		});
		siteSelectField.setValue(site);

		idColumn.setCellValueFactory(new PropertyValueFactory<JMInfo, String>("id"));
		moneyColumn.setCellValueFactory(new PropertyValueFactory<JMInfo, String>("money"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<JMInfo, String>("name"));
		urlColumn.setCellValueFactory(new PropertyValueFactory<JMInfo, String>("url"));
		
		urlColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				System.out.println(11111);
				TableColumn<JMInfo, String> currentColumn = (TableColumn) event.getSource();
				System.out.println(currentColumn.getText());
			}
			
		});
		
		jmTable.getSelectionModel().selectedItemProperty().addListener(
        		 new ChangeListener<JMInfo>() {
					@Override
					public void changed(ObservableValue<? extends JMInfo> observable, JMInfo oldValue, JMInfo newValue) {
						try {
							Desktop.getDesktop().browse(new URI(newValue.getUrl()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
        		 });

	}

	/**
	 * 处理搜索
	 * 
	 * @throws JmException
	 */
	@FXML
	private void handleSearch() throws JmException {
		String ids = this.idsField.getText();
		WebSite site = this.siteSelectField.getValue();

		if (StringUtil.isNotEmpty(ids)) {
			String[] idArr = StringUtil.split(ids.trim(), "\n");

			List<JMInfo> jmInfos = new ArrayList<JMInfo>();

			if (site.getSite().equals("LL")) {
				jmInfos = jmservice.findLLJmInfoList(idArr);
			} else if (site.getSite().equals("JE")) {
				jmInfos = jmservice.findJEJmInfoList(idArr);
			} else if (site.getSite().equals("OM")) {
				jmInfos = jmservice.findOMJmInfoList(idArr);
			} else if (site.getSite().equals("ZM")) {
				jmInfos = jmservice.findZMJmInfoList(idArr);
			}

			ObservableList<JMInfo> moneys = FXCollections.observableList(jmInfos);

			moneys.forEach((money) -> {
				total += Integer.valueOf(money.getMoney()).intValue();
			});

			this.totalLabel.setText(String.valueOf(total));
			jmTable.setItems(moneys);
		}

	}

	/**
	 * 处理重置
	 */
	@FXML
	private void handleReset() {
		this.idsField.setText("");
		this.jmTable.getItems().clear();
	}

	/**
	 * 处理跳转到webview页面
	 */
	@FXML
	private void handleGotoWebView() {
		JmFxApplication.showView(WebViewFxml.class);

	}
	
	@FXML
	private void handleBrowse() {
		
	}
}

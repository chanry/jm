package com.cl.jm.fx;

import com.cl.jm.entity.JMInfo;

import javafx.application.HostServices;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HyperlinkCell implements Callback<TableColumn<JMInfo, String>, TableCell<JMInfo, String>> {
	private static HostServices hostServices;

	public static HostServices getHostServices() {
		return hostServices;
	}

	@Override
	public TableCell<JMInfo, String> call(TableColumn<JMInfo, String> arg0) {
		TableCell<JMInfo, String> cell = new TableCell<JMInfo, String>() {

			private final Hyperlink hyperlink = new Hyperlink();

			{
				hyperlink.setOnAction(event -> {
					String url = getItem();
					hostServices.showDocument(url);
				});
			}

			@Override
			protected void updateItem(String url, boolean empty) {
				super.updateItem(url, empty);
				if (empty) {
					setGraphic(null);
				} else {
					hyperlink.setText(url);
					setGraphic(hyperlink);
				}
			}
		};
		return cell;
	}
}
package com.mc_project;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout implements View {
	Panel mPanel, rPanel;
	final Panel mainMenu = new Panel(" Menu");

	public MainView() {
		setSizeFull();
		setMargin(true);

		HorizontalLayout hLayout;
		hLayout = new HorizontalLayout();
		hLayout.setSizeFull();

		mainMenu.setHeight("100%");
		mainMenu.setWidth(null);

		// menuContent holds content of mainMenu panel
		VerticalLayout menuContent = new VerticalLayout();

		// menu options
		menuContent.addComponent(new Button("Search Patients",
				new MenuListener("searchPatient")));

		menuContent.setWidth(null);
		menuContent.setMargin(true);
		mainMenu.setContent(menuContent);

		hLayout.addComponent(mainMenu);
		addComponent(hLayout);

		// A panel that contains a content area on right
		mPanel = new Panel(" Main Content");
		mPanel.setSizeFull();
		hLayout.addComponent(mPanel);
		hLayout.setExpandRatio(mPanel, 2.0f);

		// A panel that contains a content area on right
		rPanel = new Panel(" Widgets");
		rPanel.setSizeFull();
		hLayout.addComponent(rPanel);
		hLayout.setExpandRatio(rPanel, 1.0f);

		addComponent(hLayout);
		setExpandRatio(hLayout, 1.0f);

		// Allow going back to the start
		Button logout = new Button("Logout", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Mc_projectUI.navigator.navigateTo("");
			}
		});
		addComponent(logout);
	}

	// Dynamically loading data
	@Override
	public void enter(ViewChangeEvent event) {
		VerticalLayout panelContent = new VerticalLayout();
		panelContent.setSizeFull();
		mPanel.setContent(panelContent); // Also clears

		if (event.getParameters() == null || event.getParameters().isEmpty()) {

			Label topGap = new Label("");
			panelContent.addComponent(topGap);

			Image image = new Image("", new ThemeResource("img/DrSoftLogo_w.jpg"));
			panelContent.addComponent(image);
			panelContent.setSpacing(true);
			Label botomGap = new Label("");
			panelContent.addComponent(botomGap);

		}

		if (event.getParameters().contentEquals("searchPatient")) {
			PatientManager pm = new PatientManager();
			panelContent.addComponent(pm);
		}

	}

}

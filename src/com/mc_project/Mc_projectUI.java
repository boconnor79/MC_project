package com.mc_project;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class Mc_projectUI extends UI {

	static Navigator navigator;
	static String MAINVIEW = "main";

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("DrSoft Appointment Solutions by MourceCode");

		// Create a navigator to control the views
		navigator = new Navigator(this, this);

		// Create and register the views
		navigator.addView("", new StartView());
		//navigator.addView(MAINVIEW, new MainView());
		navigator.addView(MAINVIEW, new MainView());
	}

}
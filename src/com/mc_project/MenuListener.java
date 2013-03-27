package com.mc_project;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class MenuListener implements Button.ClickListener {

	String menuOption;

	public MenuListener(String menuOption) {
		this.menuOption = menuOption;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Mc_projectUI.navigator.navigateTo(Mc_projectUI.MAINVIEW + "/"
				+ menuOption);
	}
}

package com.mc_project;

import java.util.Date;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class DateSelectorWidget extends VerticalLayout {

	// Create Calendar
	final InlineDateField date = new InlineDateField();

	public DateSelectorWidget() {
		// Title
		addComponent(new Label("<b>Date Selector</b>", ContentMode.HTML));
		// Set date to today
		date.setValue(new java.util.Date());
		// Add to layout
		addComponent(date);
	}
	
	public Date getSelectedDate() {
		// Returns the value of selected date
		return date.getValue();
	}
}

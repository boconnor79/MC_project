package com.mc_project;

import java.sql.ResultSet;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class PatientManager extends VerticalLayout {
	TextField searchField = new TextField();
	Button searchButton = new Button("Submit");
	Label spacer = new Label(" ");
	Table patientList = new Table("Patient List");
	MyDatabaseHandler mydbh = new MyDatabaseHandler();
	HorizontalLayout hl = new HorizontalLayout();

	public PatientManager() {
		setSizeFull();
		searchPanel();
		addComponent(spacer);

		searchButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				hl.setVisible(false);
				try {
					ResultSet rs = mydbh
							.retQuery("Select * from patient where P_FName like '"
									+ searchField.getValue()
									+ "%' or P_SName like '"
									+ searchField.getValue() + "%'");
					while (rs.next()) {
						patientList.addItem(
								new Object[] { rs.getString(2),
										rs.getString(3), rs.getString(6) },
								rs.getInt(1));
					}
					rs.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		resultsPanel();
	}

	private void resultsPanel() {
		VerticalSplitPanel vsp = new VerticalSplitPanel();
		vsp.setHeight("100%");
		vsp.setSizeFull();
		VerticalLayout results = new VerticalLayout();
		VerticalLayout editDetails = new VerticalLayout();
		vsp.addComponent(results);
		results.setSizeFull();
		vsp.addComponent(editDetails);
		editDetails.setSizeFull();

		// results area
		patientList.addContainerProperty("Firstname", String.class, null);
		patientList.addContainerProperty("Surname", String.class, null);
		patientList.addContainerProperty("DOB", String.class, null);
		patientList.setSizeFull();
		patientList.setSelectable(true);
		patientList.setImmediate(true);
		patientList.setColumnReorderingAllowed(true);
		
		results.addComponent(patientList);

		// editDetails area
		editDetails.addComponent(new Label("Name"));
		addComponent(vsp);
		setExpandRatio(vsp, 1);
	}

	// adds search bar and button
	private void searchPanel() {
		searchField.setWidth("100%");
		searchField.setInputPrompt("Search by name portion or date of birth");

		hl.setMargin(true);
		hl.addComponent(searchField);
		hl.addComponent(searchButton);
		hl.setWidth("100%");
		hl.setExpandRatio(searchField, 1);

		addComponent(hl);
	}
}

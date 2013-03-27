package com.mc_project;

import java.sql.ResultSet;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
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
	private Button rmvPatientButton, updPatientButton;

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
		results.setMargin(true);
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
		FormLayout editContent = new FormLayout();
		final TextField fNameField = new TextField("First Name :");
		fNameField.setWidth("100%");
		editContent.addComponent(fNameField);

		final TextField sNameField = new TextField("Last Name :");
		sNameField.setWidth("100%");
		editContent.addComponent(sNameField);
		
		final TextField addressField = new TextField("Address :");
		addressField.setWidth("100%");
		editContent.addComponent(addressField);
	
		final TextField telNoField = new TextField("Tel No :");
		telNoField.setWidth("100%");
		editContent.addComponent(telNoField);
		
		final TextField dobField = new TextField("D.O.B :");
		dobField.setWidth("100%");
		editContent.addComponent(dobField);
		
		final TextField genderField = new TextField("Gender :");
		genderField.setWidth("100%");
		editContent.addComponent(genderField);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		//buttonLayout.setSizeFull();
		updPatientButton = new Button("Update Patient");
		rmvPatientButton = new Button("Remove Patient");
		buttonLayout.addComponent(updPatientButton);
		buttonLayout.addComponent(rmvPatientButton);
		editContent.addComponent(buttonLayout);
		editDetails.addComponent(editContent);
		editDetails.setMargin(true);
		editDetails.setVisible(true);
		addComponent(vsp);
		setExpandRatio(vsp, 1);

	}

	// adds search bar and button
	private void searchPanel() {
		searchField.setWidth("100%");
		searchField.setInputPrompt("Search by name");

		hl.setMargin(true);
		hl.addComponent(searchField);
		hl.addComponent(searchButton);
		hl.setWidth("100%");
		hl.setExpandRatio(searchField, 1);

		addComponent(hl);
	}
}

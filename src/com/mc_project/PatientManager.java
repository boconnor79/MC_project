package com.mc_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
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
	Label message = new Label();
	Table patientList = new Table("Patient List");
	MyDatabaseHandler mydbh = new MyDatabaseHandler();
	HorizontalLayout hl = new HorizontalLayout();
	private Button delPatientButton, updPatientButton;
	AddNewPatient anp = new AddNewPatient();
	Notification updNote = new Notification("Patient updated successfully");
	Notification delNote = new Notification("Patient deleted successfully");
	
	// Patient Details Fields
	final TextField fNameField = new TextField("First Name :");
	final TextField sNameField = new TextField("Last Name :");
	final TextField addressField = new TextField("Address :");
	final TextField telNoField = new TextField("Tel No :");
	final TextField dobField = new TextField("D.O.B :");
	final TextField genderField = new TextField("Gender :");

	public PatientManager() {
		setSizeFull();
		searchPanel();
		addComponent(spacer);

		searchButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				hl.setVisible(false);
				try {
					Connection conn = new MyDatabaseHandler().getConn();
					PreparedStatement stat = conn
							.prepareStatement("Select * from patient where P_FName like '"
									+ searchField.getValue()
									+ "%' or P_SName like '"
									+ searchField.getValue() + "%'");
					ResultSet rs = stat.executeQuery();
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
		patientList.addContainerProperty("First name", String.class, null);
		patientList.addContainerProperty("Surname", String.class, null);
		patientList.addContainerProperty("DOB", String.class, null);
		patientList.setSizeFull();
		patientList.setSelectable(true);
		patientList.setImmediate(true);
		patientList.setColumnReorderingAllowed(true);

		results.addComponent(patientList);

		// editDetails area
		final FormLayout form = new FormLayout();
		fNameField.setWidth("100%");
		form.addComponent(fNameField);
		sNameField.setWidth("100%");
		form.addComponent(sNameField);
		addressField.setWidth("100%");
		form.addComponent(addressField);
		telNoField.setWidth("100%");
		form.addComponent(telNoField);
		dobField.setWidth("100%");
		form.addComponent(dobField);
		genderField.setWidth("100%");
		form.addComponent(genderField);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		updPatientButton = new Button("Update Patient");
		delPatientButton = new Button("Delete Patient");
		buttonLayout.addComponent(updPatientButton);
		buttonLayout.addComponent(delPatientButton);
		buttonLayout.setSpacing(true);
		form.addComponent(buttonLayout);
		editDetails.addComponent(form);
		editDetails.setMargin(true);
		editDetails.setVisible(true);
		addComponent(vsp);
		setExpandRatio(vsp, 1);

		// gets the id of the selected patient 
		// and displays details in text fields
		patientList.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				SharedValues.PatientId = patientList.getValue().toString();
				displayDetails();
			}
		});

		updPatientButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				updNote.show(Page.getCurrent());
				updNote.setDelayMsec(500);
				updNote.setPosition(Position.MIDDLE_CENTER);
				updatePatient();
				anp.clearValues();
			}
		});

		delPatientButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				delNote.show(Page.getCurrent());
				delNote.setDelayMsec(500);
				delNote.setPosition(Position.MIDDLE_CENTER);
				deletePatient();
				anp.clearValues();

			}
		});

	}

	private void displayDetails() {
		try {
			Connection conn = new MyDatabaseHandler().getConn();
			PreparedStatement stat = conn
					.prepareStatement("Select * from patient where PatientID = "
							+ SharedValues.PatientId);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				fNameField.setValue(rs.getString(2));
				sNameField.setValue(rs.getString(3));
				addressField.setValue(rs.getString(4));
				telNoField.setValue(rs.getString(5));
				dobField.setValue(rs.getString(6));
				genderField.setValue(rs.getString(7));
			}
			rs.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updatePatient() {

		try {
			Connection conn = new MyDatabaseHandler().getConn();
			PreparedStatement stat = conn
					.prepareStatement("UPDATE `surgery_data`.`patient` SET `P_FName` = '"
							+ fNameField.getValue()
							+ "', `P_SName` = '"
							+ sNameField.getValue()
							+ "', `P_address` = '"
							+ addressField.getValue()
							+ "', `P_TelNo` = '"
							+ telNoField.getValue()
							+ "', `P_DOB` = '"
							+ dobField.getValue()
							+ "', `P_Gender` = '"
							+ genderField.getValue()
							+ "' WHERE `patient`.`PatientID` ="
							+ SharedValues.PatientId);
			stat.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deletePatient() {

		try {
			Connection conn = new MyDatabaseHandler().getConn();
			PreparedStatement stat = conn
					.prepareStatement("DELETE FROM `surgery_data`.`patient` WHERE `patient`.`PatientID` ="
							+ SharedValues.PatientId);
			stat.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

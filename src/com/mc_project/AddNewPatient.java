package com.mc_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class AddNewPatient extends VerticalLayout {
	final TextField fNameField = new TextField("First Name :");
	final TextField sNameField = new TextField("Last Name :");
	final TextField addressField = new TextField("Address :");
	final TextField telNoField = new TextField("Tel No :");
	final TextField dobField = new TextField("D.O.B :");
	final TextField genderField = new TextField("Gender :");
	Button submit = new Button("Add Patient");
	Button clear = new Button("Clear");
	Notification addNote = new Notification("Patient added successfully");
	@SuppressWarnings("deprecation")
	Notification addErrNote = new Notification("Fields are empty\n", "Please insert patient details", Notification.TYPE_ERROR_MESSAGE);
	

	public AddNewPatient() {
		setSizeFull();
		setMargin(true);
		final FormLayout form = new FormLayout();
		fNameField.setWidth("100%");
		fNameField.setRequired(true);
		fNameField.setRequiredError("Please Input patients first name");
		form.addComponent(fNameField);
		
		sNameField.setWidth("100%");
		sNameField.setRequired(true);
		sNameField.setRequiredError("Please Input patients surname name");
		form.addComponent(sNameField);
		
		addressField.setWidth("100%");
		addressField.setRequired(true);
		addressField.setRequiredError("Please Input patients address");
		form.addComponent(addressField);
		
		telNoField.setWidth("100%");
		telNoField.setRequired(true);
		telNoField.setRequiredError("Please Input patients contact number");
		form.addComponent(telNoField);
		
		dobField.setWidth("100%");
		dobField.setRequired(true);
		dobField.setRequiredError("Please Input patients date of birth");
		form.addComponent(dobField);
		
		genderField.setWidth("100%");
		genderField.setRequired(true);
		genderField.setRequiredError("Please Input patients gender");
		form.addComponent(genderField);

		HorizontalLayout bl = new HorizontalLayout();
		bl.setSpacing(true);
		bl.addComponent(submit);
		bl.addComponent(clear);

		form.addComponent(bl);
		addComponent(form);

		submit.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (fNameField.getValue() == "" 
					&& sNameField.getValue() == "" 
					&& addressField.getValue() == ""
					&& telNoField.getValue() == ""
					&& genderField.getValue() == "")
				{
					addErrNote.show(Page.getCurrent());
					addErrNote.setDelayMsec(500);
					addErrNote.setPosition(Position.MIDDLE_CENTER);
				}
				else
				{
				insertPatient();
				addNote.show(Page.getCurrent());
				addNote.setDelayMsec(500);
				addNote.setPosition(Position.MIDDLE_CENTER);
				clearValues();
				}
		
			}
		});

		clear.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				clearValues();
			}
		});
	}

	protected void clearValues() {
		fNameField.setValue("");
		sNameField.setValue("");
		addressField.setValue("");
		telNoField.setValue("");
		dobField.setValue("");
		genderField.setValue("");
	}

	// Adds new patient to database
	protected void insertPatient() {
		try {
			Connection conn = new MyDatabaseHandler().getConn();

			PreparedStatement stat = conn
					.prepareStatement("INSERT INTO `surgery_data`.`patient` (`P_FName`, `P_SName`, `P_Address`, `P_TelNo`, `P_DOB`, `P_Gender`) VALUES ('"
							+ fNameField.getValue()
							+ "', '"
							+ sNameField.getValue()
							+ "', '"
							+ addressField.getValue()
							+ "', '"
							+ telNoField.getValue()
							+ "', '"
							+ dobField.getValue()
							+ "', '"
							+ genderField.getValue() + "')");

			stat.executeUpdate();
			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error inserting");
		}
	}
}

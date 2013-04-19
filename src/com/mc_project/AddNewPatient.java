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

	// initial textfields
	final TextField fNameField = new TextField("First Name :");
	final TextField sNameField = new TextField("Last Name :");
	final TextField addressField = new TextField("Address :");
	final TextField telNoField = new TextField("Tel No :");
	final TextField dobField = new TextField("D.O.B :");
	final TextField genderField = new TextField("Gender :");

	// declare buttons
	Button submit = new Button("Add Patient");
	Button clear = new Button("Clear");

	// declare notification messages
	Notification addNote = new Notification("Patient added successfully");
	@SuppressWarnings("deprecation")
	Notification addErrNote = new Notification("Fields are empty\n",
			"Please insert patient details", Notification.TYPE_ERROR_MESSAGE);

	// add a new patient method
	public AddNewPatient() {
		setSizeFull();
		setMargin(true);

		// declare a form layout
		final FormLayout form = new FormLayout();
		
		// set firstname field attributes
		fNameField.setWidth("100%");
		fNameField.setRequired(true);
		fNameField.setRequiredError("Please Input patients first name");
		// add firstname field to the form layout
		form.addComponent(fNameField);

		// set surname field attributes
		sNameField.setWidth("100%");
		sNameField.setRequired(true);
		sNameField.setRequiredError("Please Input patients surname name");
		// add surname field to the form layout
		form.addComponent(sNameField);

		// set address field attributes
		addressField.setWidth("100%");
		addressField.setRequired(true);
		addressField.setRequiredError("Please Input patients address");
		// add address field to the form layout
		form.addComponent(addressField);

		// set telephone number field attributes
		telNoField.setWidth("100%");
		telNoField.setRequired(true);
		telNoField.setRequiredError("Please Input patients contact number");
		// add telephone number field to the form layout
		form.addComponent(telNoField);

		// set date of birth field attributes
		dobField.setWidth("100%");
		dobField.setRequired(true);
		dobField.setRequiredError("Please Input patients date of birth");
		// add date of birth field to the form layout
		form.addComponent(dobField);

		// set gender field attributes
		genderField.setWidth("100%");
		genderField.setRequired(true);
		genderField.setRequiredError("Please Input patients gender");
		// add gender field to the form layout
		form.addComponent(genderField);

		// create a horizontal layout with spacing
		HorizontalLayout bl = new HorizontalLayout();
		bl.setSpacing(true);
		// add buttons to the horizontal layout
		bl.addComponent(submit);
		bl.addComponent(clear);

		// add the horizontal layout to the form layout
		form.addComponent(bl);
		// add form to main vertical layout
		addComponent(form);

		// submit button listener - if all patient fields are blank display error message else display a success message
		submit.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (fNameField.getValue() == "" && sNameField.getValue() == ""
						&& addressField.getValue() == ""
						&& telNoField.getValue() == ""
						&& genderField.getValue() == "") {
					addErrNote.show(Page.getCurrent());
					addErrNote.setDelayMsec(500);
					addErrNote.setPosition(Position.MIDDLE_CENTER);
				} else {
					insertPatient();
					addNote.show(Page.getCurrent());
					addNote.setDelayMsec(500);
					addNote.setPosition(Position.MIDDLE_CENTER);
					clearValues();
				}

			}
		});

		// clear button listener - calls clear field values method
		clear.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				clearValues();
			}
		});
	}

	// method to clear any values from the fields
	protected void clearValues() {
		fNameField.setValue("");
		sNameField.setValue("");
		addressField.setValue("");
		telNoField.setValue("");
		dobField.setValue("");
		genderField.setValue("");
	}

	// method to add a new patient to database
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

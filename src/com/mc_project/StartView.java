/**
 * 
 */
package com.mc_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author Glen
 * 
 */
@SuppressWarnings("serial")
public class StartView extends VerticalLayout implements View {
	@SuppressWarnings("deprecation")
	Notification loginErrNote = new Notification("Incorrect login details\n",
			"Please enter a valid email and password",
			Notification.TYPE_ERROR_MESSAGE);
	Notification blankErrNote = new Notification("Fields Blank\n",
			"Please enter your email and password",
			Notification.TYPE_ERROR_MESSAGE);

	final TextField username = new TextField("Username :");
	final PasswordField password = new PasswordField("Password :");

	public StartView() {
		setSizeFull();
		setMargin(true);

		// Create a panel called login
		Panel panel = new Panel("Login");
		panel.setSizeUndefined(); // Shrink to fit content
		addComponent(panel);

		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		// Create the content
		FormLayout content = new FormLayout();

		username.setWidth("300px");
		username.setRequired(true);
		username.setInputPrompt("Your username(eg. name@email.com)");

		content.addComponent(username);

		password.setWidth("300px");
		password.setRequired(true);

		content.addComponent(password);

		HorizontalLayout buttonArea = new HorizontalLayout();
		buttonArea.setSizeFull();
		Button submit = new Button("Submit");
		Button clear = new Button("Clear");
		clear.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				username.setValue("");
				password.setValue("");

			}
		});

		submit.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				checkDatabase(username.getValue(), password.getValue());
			}
		});

		buttonArea.addComponent(clear);
		buttonArea.addComponent(submit);

		content.addComponent(buttonArea);
		content.setSizeFull();
		content.setMargin(true);

		panel.setContent(content);
	}

	protected void checkDatabase(String uname, String pword) {
		try {
			Connection conn = new MyDatabaseHandler().getConn();
			PreparedStatement stat = conn
					.prepareStatement("select * from staff where s_uname = '"
							+ uname + "' and s_pword = '" + pword + "'");
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				SharedValues.StaffID = rs.getString(1).toString();
				SharedValues.StaffName = rs.getString(2);
				SharedValues.StaffType = rs.getString(6);
				Mc_projectUI.navigator.navigateTo(Mc_projectUI.MAINVIEW);
			}
			else if (username.getValue() == "" && password.getValue() == "") {
				blankErrNote.show(Page.getCurrent());
				blankErrNote.setDelayMsec(500);
				blankErrNote.setPosition(Position.MIDDLE_CENTER);
			} else {
				loginErrNote.show(Page.getCurrent());
				loginErrNote.setDelayMsec(500);
				loginErrNote.setPosition(Position.MIDDLE_CENTER);
			}
		} catch (Exception e) {
			Logger log = new Logger();
			log.logCreator("Login Error");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		Notification notification = new Notification("\n"
				+ "Welcome to DrSoft Appointment Solutions" + "\n",
				"by MourceCode");

		notification.setDelayMsec(10000);
		notification.setPosition(Position.TOP_CENTER);
		notification.show(Page.getCurrent());
		notification.setIcon(new ThemeResource("img/DrSoftLogo1.png"));

	}

}

package org.gecko.playground.vaadin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view contains a button and a click listener.
 */
@Route("vaadin")
// Need to explicitly declare the Lumo until https://github.com/vaadin/flow/issues/4847 is fixed
@Theme(Lumo.class)
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = -1606204314748731359L;
	private volatile String name;

	public MainView() {
        Button button = new Button("Click test me, please",
                event -> {
                	String prefix = name == null || name.isBlank() || name.isEmpty() ? "Anonymus" : name;
                	Notification.show(prefix + " clicked the button!");
                });
        add(button);
        Text name = new Text("Your name: ");
        add(name);
        TextField nameField = new TextField(event -> this.name = event.getValue());
        nameField.setClearButtonVisible(true);
        add(nameField);
    }
}

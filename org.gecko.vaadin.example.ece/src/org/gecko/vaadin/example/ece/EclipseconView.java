/**
 * Copyright (c) 2012 - 2022 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.vaadin.example.ece;


import org.gecko.vaadin.example.views.main.MainView;
import org.gecko.vaadin.example.service.GreeterService;
import org.gecko.vaadin.whiteboard.annotations.VaadinComponent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

/**
 * This is one example view for your application.
 * With the {@link Route} annotation you specify the last segment of the path under which this view will be reachable. 
 * You can add other views in the createMenuItems method, that will be displayed in the Menu tabs.
 * 
 * @author Mark Hoffmann
 */
@Route(value = "eclipsecon", layout = MainView.class)
@RouteAlias(value = "ece", layout = MainView.class)
@PageTitle("Hello EclipseCon")
//@Component(service= {EclipseconView.class, com.vaadin.flow.component.Component.class}, property = {"main=test", "menu.name=Hello EclipseCon 2023"}, scope = ServiceScope.PROTOTYPE)
//@VaadinComponent()
public class EclipseconView extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private TextField name;
    private Button sayHello;
    @Reference
    private GreeterService service;

    @Activate
    public EclipseconView() {
        addClassName("ece-view");
        name = new TextField("Your EclipseCon Name");
        sayHello = new Button("Say 'Yeah'");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
        	String greet = service.greet(name.getValue());
            Notification.show(greet + " YEAH!!!");
        });
    }

}

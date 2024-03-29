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
package org.gecko.vaadin.example.views.helloworld;


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
 * @author ilenia
 * @since Feb 7, 2023
 */
@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Hello World")
@Component(service= HelloWorldView.class, scope = ServiceScope.PROTOTYPE, property = {"menu.name=Hello World"})
@VaadinComponent()
public class HelloWorldView extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private TextField name;
    private Button sayHello;
    @Reference
    private GreeterService service;

    @Activate
    public HelloWorldView() {
        addClassName("hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
        	String greet = service.greet(name.getValue());
            Notification.show(greet);
        });
    }

}

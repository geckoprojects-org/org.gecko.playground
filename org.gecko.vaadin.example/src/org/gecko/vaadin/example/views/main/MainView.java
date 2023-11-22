/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.vaadin.example.views.main;

import static java.util.Objects.nonNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.gecko.vaadin.example.views.helloworld.HelloWorldView;
import org.gecko.vaadin.whiteboard.annotations.VaadinComponent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;

/**
 * This is the MainView of your Vaadin application. 
 * You can add other views in the createMenuItems method, that will be displayed in the Menu tabs.
 * 
 * @author ilenia
 * @since Feb 7, 2023
 */
@Component(immediate=true, service=MainView.class, name="MainView")
@VaadinComponent()
@NpmPackage(value = "lumo-css-framework", version = "3.0.11")
public class MainView extends AppLayout {

	/** serialVersionUID */
	private static final long serialVersionUID = 2078941825127700729L;

	private final AtomicReference<Tabs> menu = new AtomicReference<>();
	private H1 viewTitle;

	private Map<String, Tab> tabList = new ConcurrentHashMap<>();
	private final UI ui = UI.getCurrent();

	@Activate
	public MainView() {
		setPrimarySection(Section.DRAWER);
		addToNavbar(true, createHeaderContent());
		tabList.put("Hello", createTab("Hello", HelloWorldView.class));
		//		tabList.put("EclipseCon", createTab("EclipseCon", EclipseconView.class));
		menu.set(createMenu());
		System.out.println("add to drawer");
		addToDrawer(createDrawerContent(menu.get()));
		System.out.println("added to drawer");
		//		PromiseFactory pf = new PromiseFactory(Executors.newCachedThreadPool());
		//		pf.resolved(createTab("EclipseCon", EclipseconView.class)).delay(15000).onSuccess(t->{
		//			tabList.put(t.getLabel(), t);
		////			Tabs newMenu = createMenu();
		//			Tabs menu = this.menu.get();
		////			VerticalLayout layout = (VerticalLayout)menu.getParent().get();
		//			UI.getCurrent().access(()->{
		//				menu.add(t);
		////				layout.replace(menu, newMenu);
		//			});
		////			this.menu.set(newMenu);
		//		});
	}

	private com.vaadin.flow.component.Component createHeaderContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setId("header");
		layout.getThemeList().set("dark", true);
		layout.setWidthFull();
		layout.setSpacing(false);
		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.add(new DrawerToggle());
		viewTitle = new H1();
		layout.add(viewTitle);
		layout.add(new Avatar());
		return layout;
	}

	private com.vaadin.flow.component.Component createDrawerContent(Tabs menu) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setPadding(false);
		layout.setSpacing(false);
		layout.getThemeList().set("spacing-s", true);
		layout.setAlignItems(FlexComponent.Alignment.STRETCH);
		VerticalLayout logoLayout = new VerticalLayout();
		logoLayout.setId("logo");
		logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
		logoLayout.add(new Image("images/CI_CIM.png", "DIMC logo"));
		logoLayout.add(new H1("Vaadin App"));
		layout.add(logoLayout, menu);
		return layout;
	}

	private Tabs createMenu() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
		tabs.setId("tabs");
		tabs.add(createMenuItems());
		return tabs;
	}

	private com.vaadin.flow.component.Component[] createMenuItems() {
		//		return new Tab[]{        		
		//				createTab("Hello", HelloWorldView.class),
		//				createTab("EclipseCon", EclipseconView.class)
		//		};
		System.out.println("create menu items");
		return tabList.values().toArray(new Tab[tabList.size()]);
	}

	private static Tab createTab(String text, Class<? extends com.vaadin.flow.component.Component> navigationTarget) {
		final Tab tab = new Tab();
		tab.add(new RouterLink(text, navigationTarget));
		ComponentUtil.setData(tab, Class.class, navigationTarget);
		return tab;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		Tabs menu = this.menu.get();
		getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
		viewTitle.setText(getCurrentPageTitle());
	}

	private Optional<Tab> getTabForComponent(com.vaadin.flow.component.Component component) {
		return this.menu.get().getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
				.findFirst().map(Tab.class::cast);
	}

	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, target = "(main=test)")
	public void addComponent(com.vaadin.flow.component.Component vc, Map<String, Object> properties) {
		if (vc == null) {
			return;
		}
		String name = (String) properties.getOrDefault("menu.name", vc.getClass().getSimpleName());
		System.out.println("INJECT " + vc + name);
		Tabs menu = this.menu.get();
		ui.access(()->{
			Tab tab = createTab(name, vc.getClass());
			tabList.put(name, tab);
			menu.add(tab);
			System.out.println("added ECE");
//			Tabs newMenu = createMenu();
//			VerticalLayout layout = (VerticalLayout)menu.getParent().get();
//			layout.replace(menu, newMenu);
//			this.menu.set(newMenu);
//			ui.getPage().reload();
		});
	}
	
	public void removeComponent(com.vaadin.flow.component.Component vc, Map<String, Object> properties) {
		String name = (String) properties.getOrDefault("menu.name", vc.getClass().getSimpleName());
		Tab toRemove = tabList.remove(name);
		if (nonNull(toRemove) && nonNull(menu.get())) {
			Tabs newMenu = createMenu();
			Tabs menu = this.menu.get();
			VerticalLayout layout = (VerticalLayout)menu.getParent().get();
			layout.replace(menu, newMenu);
			this.menu.set(newMenu);
		}
	}



}

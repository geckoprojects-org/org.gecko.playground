# Bnd Launch
This project is a simple Example that represents the default Vaadin Whiteboard with sample content.

The UI is based on Vaadin. Vaadin UI components can be regsitered as OSGi components (both are named components in different contexts: Vaadin - UI component, OSGi - Service component)

This application is similar to *org.gecko.playground.e4.rcp*, but with an alternative web frontend. the backend code is the same.

You will find the application under URL [http://localhost:8086/playground/](http://localhost:8086/playground/)

# The Application

## Precondition

You need a running mongo database as backing storage. 

In addition to that this application uses the following *playground* projects:

* org.gecko.playground.lucene - using OSGyfied Lucene 
* org.gecko.playground.model - an EMF example data model for our person
* org.gecko.playground.search - an search service for our persons
* org.gecko.playground.mongo - Persistence service using MongoDB backing including the configuration in */config/config.json*
* org.gecko.playground.vaadin - the Vaadin Java UI
* org.gecko.playground.vaadin.frontend - the Vaadin generated JavaSCript components for the web frontened

## Features

In the Webapplication, like in the RCP there are:

* Create Person form - Create a new person, save it into the database using the save icon
* Search Person Form - Look for persons using lucene search. To search for all, leave the text field empty and press search

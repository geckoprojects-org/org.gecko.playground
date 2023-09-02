# Bnd Launch and Export
This project is a simple Example that represents the default Eclipse E4 Application with sample content the PDE also provides.

If you want to modify it, only add -runrequire bundles in the **launch_base.bndrun** and resolve only the **launch_base.bndrun**. 

To start or export use the *launch_linux* or *launch_win32* because they contain the system specific fragments.

What OS Specific launcher to use, will be determined via the `-runsystemcapabilities'. If you use the `native_capability` Macro without any parameters, it will use the ones of your current system. 

You may realize that the splash screen appears only for a blink of an eye. This is because the application starts to quick :-)

# The Application

## Precondition

You need a running mongo database as backing storage. 

In addition to that this RCP uses the following *playground* projects:

* org.gecko.playground.lucene - using OSGyfied Lucene 
* org.gecko.playground.model - an EMF example data model for our person
* org.gecko.playground.search - an search service for our persons
* org.gecko.playground.mongo - Persistence service using MongoDB backing including the configuration in */config/config.json*
* org.gecko.playground.e4.rcp - the RCP application

## Features

In the RCP there are:

* Create Person form - Create a new person, save it into the database using the save icon
* Search Person Form - Look for persons using lucene search. To search for all, leave the text field empty and press search

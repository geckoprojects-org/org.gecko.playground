# `org.gecko.playground.config`

Most of the time in your application you have to deal with several configurations (db configs, whiteboard configs, etc).

It might be useful and cleaner to have a bundle which includes all these configurations files. 

This is what this configurator bundle does. It does not contain any source code, it just include the `config` folder within the generated jar and requires the OSGi configurator. 

In this way, when we include this bundle into our runtime, we would get automatically the needed dependencies for the configurator and the config admin, as well as all our configurations defined in the config folder.
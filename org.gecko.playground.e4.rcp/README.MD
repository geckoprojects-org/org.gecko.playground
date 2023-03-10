This project is a simple Example that represents the default Eclipse E4 Application with sample content the PDE also provides.

If you want to modify it, only add -runrequire bundles in the **launch_base.bndrun** and resolve only the **launch_base.bndrun**. 

To start or export use the *launch_linux* or *launch_win32* because they contain the system specific fragments.

What OS Specific launcher to use, will be determined via the `-runsystemcapabilities'. If you use the `native_capability` Macro without any parameters, it will use the ones of your current system. 

You may realize that the splash screen appears only for a blink of an eye. This is because the application starts to quick :-)
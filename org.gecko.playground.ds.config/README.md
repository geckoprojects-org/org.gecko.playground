# `org.gecko.playground.ds.config`

This is an example for configurable components.

This project is a multi jar project and produces:

* *org.gecko.playground.ds.config.configadmin* - components a programmatic way of configuration using the Configuration Admin
* *org.gecko.playground.ds.config.configurator* - declarative configured components using the Configurator

It shows how programmatically and declarative component instances can be created.

When running the application you will see this in the console:

```
Activate name-1 from 2001(album-10) from instance AlbumComponent@5398928f
Activate Funky from 1974(Funky, Funky) from instance AlbumComponent@165f68c5
Activate Happy from 2010(I wanna be happy) from instance AlbumComponent@1cea869f
Activate name-2 from 2002(album-20) from instance AlbumComponent@2e10d72b
Activate name-3 from 2003(album-30) from instance AlbumComponent@54da9176
Activate name-4 from 2004(album-40) from instance AlbumComponent@17d8d558
Activate name-5 from 2005(album-50) from instance AlbumComponent@5f5f0ed4
Modified name-1 from 2006(album-15) from instance AlbumComponent@5398928f
Modified name-2 from 2007(album-25) from instance AlbumComponent@2e10d72b
Modified name-3 from 2008(album-35) from instance AlbumComponent@54da9176
Modified name-4 from 2009(album-45) from instance AlbumComponent@17d8d558
Modified name-5 from 2010(album-55) from instance AlbumComponent@5f5f0ed4
De-Activate name-1 from 2006(album-15) from instance AlbumComponent@5398928f
De-Activate name-2 from 2007(album-25) from instance AlbumComponent@2e10d72b
De-Activate name-3 from 2008(album-35) from instance AlbumComponent@54da9176
De-Activate name-4 from 2009(album-45) from instance AlbumComponent@17d8d558
De-Activate name-5 from 2010(album-55) from instance AlbumComponent@5f5f0ed4
```

The albums *Funky* and *Happy* are created by configuration in *config/config.json*. This is published via the OSGi Configurator and the *org.gecko.playground.ds.config.configurator*. 

The *name-<something>* Albums are created, then modified and after that removed using the Configuration Admin in the *AlbumCreator* component

The *AlbumComponent* is the configuration factory. It creates a new instance for each new configuration. This can be recognized by the differnet instance id in the log entries.
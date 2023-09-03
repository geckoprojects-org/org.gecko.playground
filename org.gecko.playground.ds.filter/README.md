# `org.gecko.playground.ds.filter`

This tutorial shows how to work with OSGi *Declarative Service* references using filtering and selecting the right service.

The *GreeterComponent* referenced to a *GreeterService*. But we have two implementations of it and only want tol select that one with the service propert< *lang=DE*

The *ConfigurableGreeterComponent* uses the configuration in *config/config.json*. The reference for the *GreeterService* got a name *gs*.

The configuration contains the filter, by extending the key with `.target`:

```json
{
  ":configurator:resource-version": 1,
  "CGS": 
	{
		"gs.target": "(lang=EN)" 
	}
}
```

With this, it is possible to define filters in a declarative / configurable way. This can also be used when directly using the *ConfigurationAdmin*.

When launching the application, you will see the result in the console:

```
Say greeting depending on the configuration: Greetz, Emil
Say greeting in german: Grüße dich, Emil
```


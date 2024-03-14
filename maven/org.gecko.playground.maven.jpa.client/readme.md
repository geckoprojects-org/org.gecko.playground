# JPA Example

## Build project:

`mvn clean install`

## Run example

Runs the *launch.bndrun* in the *org.gecko.playground.maven.jpa.client* project.

```
cd org.gecko.playground.maven.jpa.client
mvn bnd-run:run
```

Should printout:

```
*** Account Report ***
Account: Account(2, Chan, Balance: $100.0)
```

## Bndrun - File explained

The comments in the bndrun should explain the most. Here the *-runrequire* section/instruction is explained:

```
# Required stuff to run the example:
-runrequires: \
	playground.jpa;filter:="(playground.jpa=accounts)",\
	bnd.identity;id='org.apache.felix.gogo.command',\
	bnd.identity;id='org.apache.felix.gogo.shell'
```

We use a requirement definition here, to tell the resolver what we need: `playground.jpa;filter:="(playground.jpa=accounts)"`

Therefor we need a corresponding capability. This is provided by using component type annotations at the `org.gecko.playground.TestComponent' class.

```
@Capability(namespace = "playground.jpa", name = "accounts")
```

The resolver matches this capability on this class. So it takes the corresponding bundle into account. As we see on the `org.gecko.playground.TestComponent' class, there is also some other `@Requirements` defined:

```
@Requirements({
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.gecko.persistence.derby)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=derby)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.eclipse.gemini.jpa)"),
	@Requirement(namespace = "osgi.identity", filter = "(osgi.identity=org.eclipse.persistence.jpa.jpql)")
})
```

They tell the resolver, that be need bundles with the given id's (bundle symbilic names) from the filter. So the resolver looks into its repository/available bundle pool an also takes these bundles into account.

In addition to that the ordinary package import-export matching runs, The result can be seen in the '-runbundles' section, that it filled by the resolver.

In the maven build the resolver run always when the *jpa.client* project is build. If something cannot be resolved anymore, the resolver will tell that.

Without capabilities you would need these requirements in your *launch.bndrun*:

```
# Required stuff to run the example:
-runrequires: \
	bnd.identity;id='org.apache.felix.gogo.command',\
	bnd.identity;id='org.apache.felix.gogo.shell'
	bnd.identity;id='org.gecko.playground.maven.jpa.client',\
	bnd.identity;id='org.eclipse.persistence.jpa.jpql',\
	bnd.identity;id='org.eclipse.gemini.jpa',\
	bnd.identity;id='org.gecko.persistence.derby',\
	bnd.identity;id='derby'
```

* You have to put the *jpa.client* bundle into the requirements
* And for sure the 4 bundles from above

What do the bundles mean:

* **jakarta.persistence-api** - The Java JPA API
* **org.eclipse.persistence.\*** - The Eclipselink JPA implementation
* **org.eclipse.gemini.jpa** - The OSGi JPA specification implementation
* **derby** - The Derby database
* **org.gecko.persistence.derby** - An implementation for the OSGi JDBC specifcation. This is needed from the OSGi JPA specification to provide a `DatasourceFactory` that is needed from JPA to instantiate a JDBC driver.



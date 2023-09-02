# `org.gecko.playground.logging`

This project is a multi bundle project in bndtools. This means on project can generate multiple bundles. This is a simple bridge to log various logging API's:

* slf4j
* Java Util Logging
* log4j
* OSGi Log

into one Log4j2 backend. 

The corresponding log4j2 configuration is located in *config/log4j2.xml* and you will find the resulting log file *gecko.log* in *cnf/logs/gecko.log*

``` 
2023.09.02 14:46:33,784 INFO  [Start Level: Equinox Container: b11aaa01-5f0e-4d8b-89f7-f771e0ba2703] org.gecko.playground.logging.test.LoggingComponent: Activate with Log4J
2023.09.02 14:46:33,785 INFO  [Start Level: Equinox Container: b11aaa01-5f0e-4d8b-89f7-f771e0ba2703] org.gecko.playground.logging.test.LoggingComponent: Activate with SLF4J
2023.09.02 14:46:33,785 INFO  [Start Level: Equinox Container: b11aaa01-5f0e-4d8b-89f7-f771e0ba2703] org.gecko.playground.logging.test.LoggingComponent: Activate with OSGi Logging
```

These log entries come out of *LoggingComponent*, that test logging with different log API's.
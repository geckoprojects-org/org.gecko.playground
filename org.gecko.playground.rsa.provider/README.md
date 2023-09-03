# `org.gecko.playground.rsa.provider`

This project is a provider example for our remote service.

It just contains the *ExampleHello* component, that implement the *HelloWorld* API. It marks the service as remote service using the component properties: `service.exported.interfaces=*`

The implementation tracks these services and creates a service description out of it. This is submitted over the service discovery mechanism


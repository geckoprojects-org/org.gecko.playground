# `org.gecko.playground.jaxb`

This project show an OSGi setup for JAXB. The important thing is to make JAXB find its implementation in an post Java 8 setup.

For this we use the HK2-OSGi-ResourceLocator, that is called as part of the JAXB API implementation finding search order.
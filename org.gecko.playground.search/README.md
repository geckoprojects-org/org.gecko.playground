# `org.gecko.playground.search`

This project shows how to use a Lucene based index service to index and search persons from an EMF model *org.gecko.playground.model*.

The console *help* shows the commands:

* `index:indexPerson <firstname> <lastname>` (returns a `personId`)
* `index:removePerson <personId>`
* `search:searchByFirstName <query> <Boolean:exactMatch>`
* `search:searchByLastName <query> <Boolean:exactMatch>`

Here is an example:

```
g! searchByLastName '' true
No Person object matching your search criteria has been found.
g! searchByLastName '' false
--------Matching Result------------
First Name: Mark
Last Name: Hoffmann
--------Matching Result------------
First Name: Peter
Last Name: Meier
```

This service is also used by the *playground.e4.rcp* example and the *playground.vaadin* applications as part of a more complex, re-usable part. 


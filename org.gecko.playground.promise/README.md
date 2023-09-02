# `org.gecko.playground.promise`

This project shows how to use asynchronous programming with OSGi Promises. The example shows how to use a `Deferred`, to hook in external frameworks and use their callbacks to trigger resolving of promises. This is a more advanced example, than the usual promises stuff. 

When starting the programm, you will see:

```
g! Scheduling database connection (main)
Finished activation (main)
```

After 15 seconds the database is connected and sends the resolved state:

```
Try connecting to database ... (pool-4-thread-1)
Database seems available: Doing sthg with the database ...
Database ID: CONID-b152b893-8961-4da7-8a80-11bf800a844a
```

The `list` command shows you two components:

```
g! list
org.gecko.playground.promise.DatabaseDriverComponent in bundle 3 (org.gecko.playground.promise:1.0.0.202309021036-SNAPSHOT) enabled, 1 instance.
    Id: 1, State:ACTIVE
org.gecko.playground.promise.DatabaseAvailableChecker in bundle 3 (org.gecko.playground.promise:1.0.0.202309021036-SNAPSHOT) enabled, 1 instance.
    Id: 0, State:ACTIVE
```



If you e.g. stop the *DatabaseDriverComponent* with:

```
g! disable org.gecko.playground.promise.DatabaseDriverComponent 
```

you will see, that the promise informs, that the database will be disconnected gracefully:

```
Disconnected Database with ID: b152b893-8961-4da7-8a80-11bf800a844a
true
Database has gone! Stop working with database!
```




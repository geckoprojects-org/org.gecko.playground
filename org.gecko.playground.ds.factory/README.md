# `org.gecko.playground.ds.factory`

This tutorial shows how to work with OSGi *Declarative Service* as a factory.

For this we have two different factories:

* *GardenDeviceFactory* - Creates smart device for your garden
* *HomeDeviceFactory* - Creates smart device for your home and rooms

Both are *Device* s

The *DeviceHandlerService* injects these instance as *ComponentFactory*. These can create *ComponentInstance* s. Depending on the given type and properties now configured instance can be created using the right factory.

All instances can be disposed, when they are not needed any more

To test this you can use the *DeviceCommands*:

* `device:createDevice <type> <name>` - creates a new device of type and name
* `device:deleteDevice <name>` - removes the device with the given name

This can be tested:

```
g! createDevice GARDEN Pool-Pump
Activate Instance GARDEN-DEVICE - Pool-Pump
Device created: GARDEN-DEVICE- Pool-Pump (org.gecko.playground.ds.factory.garden.GardenDeviceFactory@254b5fd4)
g! createDevice HOME Smart-TV
Activate Instance HOME-DEVICE - Smart-TV
Device created: HOME-DEVICE- Smart-TV (org.gecko.playground.ds.factory.home.HomeDeviceFactory@6147eaea)

g! deleteDevice HOME Smart-TV
Dispose device HOME - Smart-TV
Deactivate Instance HOME-DEVICE - Smart-TV
STOP HOME-DEVICE - Smart-TV
Device deleted: HOME- Smart-TV
g! deleteDevice GARDEN Pool-Pump
Dispose device GARDEN - Pool-Pump
Deactivate Instance GARDEN-DEVICE - Pool-Pump
STOP GARDEN-DEVICE - Pool-Pump
Device deleted: GARDEN- Pool-Pump
```

Now the instance are release using the corresponding factory.


# `org.gecko.playground.tasks.demo`

This demo will show you a simple example of startup task of an application. These task can be conditional, e.g. being dependent from a *workmode* of the application.

In this example a task is only executed, if the application is in workmode **ONLINE**. If the application becomes **OFFLINE** this task executes the deactivation callback respectively.

For this additional projects are involved:

* *org.gecko.playground.config* - Initial configuration for workmode in */config/config.json* 
* *org.gecko.playground.workmode* - Workmode commands and handling
* *org.gecko.playground.tasks* - Tasks services and framework
* *org.gecko.playground.tasks.demo*  - Example tasks

The implementation of this example, together with the workmode uses OSGi specifications

* *Promises* - Asynchronous activation and de-activation handling
* *Conditions* - Announcing the workmode state in the application
* *Configurator* - Using json configurations as setting for the configuration admin
* *Configuration Admin* - Programmatically create configurations during runtime



The console *help* shows the commands:

* `task:activate <taskid>` - Activate a task
* `task:deactivate <taskid>` - Deactivate a task
* `task:showState`- Show tasks states
* `workmode:getWorkmode` - Returns the current work mode
* `workmode:setWorkmode <workmode>` - Sets the workmode. Supported values are **ONLINE** or **OFFLINE**

When running the application, the default workmode from the configuration (from *org.gecko.playground.config*) is initialized via *WorkmodeInitializer* from the *org.gecko.playground.workmode* project. Depending on this condition that is raised, the *ExampleInitializationTask* ist started. It will take 8 seconds to finish this task (Simulated a long running task):

```
g! Started activation EXTERNAL 1 ... 
<wait 8 seconds>
Finished initialization EXTERNAL 1
```

The command *showState* will give you an overview and show the state is *ACTIVE*:

```
g! showState
Task with id 'externalTest' and name 'EXTERNAL 1 - INITIALIZATION TASK' has state 'ACTIVE'
```

You can deactivate this task with:

```
g! deactivate externalTest
De-activate task with id 'externalTest' and name 'EXTERNAL 1 - INITIALIZATION TASK' ...
Started deactivation EXTERNAL 1 ...
<wait 3 seconds>
Finished deactivation EXTERNAL 1
De-activated task id 'externalTest' and name 'externalTest' with result 'INACTIVE'
```

It take about 3 seconds until the task is deactivated / *INACTIVE*.

```
g! showState
Task with id 'externalTest' and name 'EXTERNAL 1 - INITIALIZATION TASK' has state 'INACTIVE'
```



Now you can change the workmode. First check the current work mode:

```
g! getworkmode
Current work mode is 'OFFLINE'
```



We want set the applications workmode to online, manually, now the *OnlineAuthenticatedExampleInitializationTask* should be activated:

```
g! setworkmode ONLINE
Workmode was updated from 'OFFLINE' to 'ONLINE'
g! Started initialization EXTERNAL 3 Workmode ONLINE ...
<wait 5 seconds>
Finished initialization EXTERNAL 3 Workmode ONLINE
```

Setting the workmode back to **OFFLINE**, deactivateds this task again (takes 5 seconds):

```
g! setworkmode OFFLINE
Workmode was updated from 'ONLINE' to 'OFFLINE'
g! Started deactivation EXTERNAL 3 Workmode ONLINE ...
<wait 5 seconds>
g! Finished deactivation EXTERNAL 3 Workmode ONLINE
showstate
Task with id 'externalTest' and name 'EXTERNAL 1 - INITIALIZATION TASK' has state 'INACTIVE'
Task with id 'externalOnline' and name 'EXTERNAL 3 - INITIALIZATION TASK (Workmode ONLINE)' has state 'REMOVED'
```

The  task has been removed. It is not *INACTIVE* like the other task. This is because *externalOnline* belongs to the workmode. If the right workmode is not set, this taks is not applicable, so it must be removed. To keep this information after cleanup, each task is an *TaskInfo* object, that just contains task meta-data, even if the real task is already cleaned up.

As you may see the console returns after the *setWorkmode* command with returning `g!`. This is because this activation is asynchronous. If you are fast enough you can also trigger the `showState` command in between this 5 activation or deactivation seconds:

```
g! setworkmode ONLINE
Workmode was updated from 'OFFLINE' to 'ONLINE'
g! Started initialization EXTERNAL 3 Workmode ONLINE ...
showstate
Task with id 'externalTest' and name 'EXTERNAL 1 - INITIALIZATION TASK' has state 'INACTIVE'
Task with id 'externalOnline' and name 'EXTERNAL 3 - INITIALIZATION TASK (Workmode ONLINE)' has state 'ACTIVATING'
g! Finished initialization EXTERNAL 3 Workmode ONLINE
showstate
Task with id 'externalTest' and name 'EXTERNAL 1 - INITIALIZATION TASK' has state 'INACTIVE'
Task with id 'externalOnline' and name 'EXTERNAL 3 - INITIALIZATION TASK (Workmode ONLINE)' has state 'ACTIVE'
```

So you can see that an activating task also announces a corresponding transition-status *ACTIVATING*.

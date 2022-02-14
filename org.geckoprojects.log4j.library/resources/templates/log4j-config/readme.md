# Using config path property

The default **log4j2.xml** refers to log files using the *${sys:logFolderPath}* variable.

This is a Java system property. So, if you want to run the application dont't forget to define that path. If you want to write your log file into "cnf/logs", you should define:

`-runvm: -DlogFolderPath=${build}/logs`
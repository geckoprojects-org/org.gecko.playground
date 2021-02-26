package org.gecko.playground.workmode;

public enum Workmode {
	
	// no server available
	OFFLINE,
	// server available
	ONLINE,
	// system replicates data with server, usually when coming from offline to online mose
	REPLICATING,
	// presentation mode, most services come from offline sources, some open server endpoints are available
	GUEST,

}

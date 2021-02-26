package org.gecko.playground.workmode.annotations;

import org.gecko.playground.workmode.WorkmodeConstants;
import org.osgi.annotation.bundle.Requirement;

@Requirement(namespace = WorkmodeConstants.CAPABILITY_WORKMODE, name = WorkmodeConstants.CAPABILITY_WORKMODE_NAME)
public @interface RequireWorkmode {

}

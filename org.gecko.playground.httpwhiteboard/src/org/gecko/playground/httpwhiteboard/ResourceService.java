package org.gecko.playground.httpwhiteboard;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardResource;

@Component(service = ResourceService.class)
@HttpWhiteboardResource(pattern = "/myapp/*", prefix = "/static")
public class ResourceService {}
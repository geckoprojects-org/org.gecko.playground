# Gecko Playground

OSGi examples built with bnd. The examples use Eclipse Fennec where a Fennec
counterpart exists and Gecko libraries otherwise.

## Disabled examples

### Vaadin (`org.gecko.playground.vaadin`, `org.gecko.playground.vaadin.frontend`)

The Vaadin example is currently disabled (`-nobundles: true`, `-generate` commented
out in the frontend's `bnd.bnd`):

- The `geckoVaadinNPM` frontend generator cannot read Java 21 class files
  (`Unsupported class file major version 65`). The workspace moved to Java 21 with the
  Fennec migration.
- The frontend's resolved `-runbundles` are stale, e.g. `org.eclipse.equinox.cm`
  1.5.100 is no longer available in any configured repository.

To re-enable it, remove `-nobundles` from both `bnd.bnd` files, uncomment `-generate`
in the frontend and re-resolve its run configuration.

/**
 * 
 */
package org.gecko.log4.extension.api;

import java.net.URI;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

/**
 * @author mark
 *
 */
public interface LogManagerProvider {
	
    /**
     * Detects if a Logger with the specified name exists. This is a convenience method for porting from version 1.
     *
     * @param name The Logger name to search for.
     * @return true if the Logger exists, false otherwise.
     * @see LoggerContext#hasLogger(String)
     */
    public boolean exists(final String name);
	
    /**
     * Returns the current LoggerContext.
     * <p>
     * WARNING - The LoggerContext returned by this method may not be the LoggerContext used to create a Logger for the
     * calling class.
     * </p>
     *
     * @return The current LoggerContext.
     */
    public LoggerContext getContext();
	
    /**
     * Returns a LoggerContext.
     *
     * @param currentContext if false the LoggerContext appropriate for the caller of this method is returned. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            returned and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be returned. If true then only a single LoggerContext will be returned.
     * @return a LoggerContext.
     */
    public LoggerContext getContext(final boolean currentContext);
	
    /**
     * Returns a LoggerContext.
     *
     * @param loader The ClassLoader for the context. If null the context will attempt to determine the appropriate
     *            ClassLoader.
     * @param currentContext if false the LoggerContext appropriate for the caller of this method is returned. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            returned and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be returned. If true then only a single LoggerContext will be returned.
     * @return a LoggerContext.
     */
	public LoggerContext getContext(final ClassLoader loader, final boolean currentContext);
	
    /**
     * Returns a LoggerContext.
     *
     * @param loader The ClassLoader for the context. If null the context will attempt to determine the appropriate
     *            ClassLoader.
     * @param currentContext if false the LoggerContext appropriate for the caller of this method is returned. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            returned and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be returned. If true then only a single LoggerContext will be returned.
     * @param externalContext An external context (such as a ServletContext) to be associated with the LoggerContext.
     * @return a LoggerContext.
     */
    public LoggerContext getContext(final ClassLoader loader, final boolean currentContext,
            final Object externalContext);
	
    /**
     * Returns a LoggerContext.
     *
     * @param loader The ClassLoader for the context. If null the context will attempt to determine the appropriate
     *            ClassLoader.
     * @param currentContext if false the LoggerContext appropriate for the caller of this method is returned. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            returned and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be returned. If true then only a single LoggerContext will be returned.
     * @param configLocation The URI for the configuration to use.
     * @return a LoggerContext.
     */
    public LoggerContext getContext(final ClassLoader loader, final boolean currentContext,
            final URI configLocation);
    
    /**
     * Returns a LoggerContext.
     *
     * @param loader The ClassLoader for the context. If null the context will attempt to determine the appropriate
     *            ClassLoader.
     * @param currentContext if false the LoggerContext appropriate for the caller of this method is returned. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            returned and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be returned. If true then only a single LoggerContext will be returned.
     * @param externalContext An external context (such as a ServletContext) to be associated with the LoggerContext.
     * @param configLocation The URI for the configuration to use.
     * @return a LoggerContext.
     */
    public LoggerContext getContext(final ClassLoader loader, final boolean currentContext,
            final Object externalContext, final URI configLocation);
    
    /**
     * Returns a LoggerContext.
     *
     * @param loader The ClassLoader for the context. If null the context will attempt to determine the appropriate
     *            ClassLoader.
     * @param currentContext if false the LoggerContext appropriate for the caller of this method is returned. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            returned and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be returned. If true then only a single LoggerContext will be returned.
     * @param externalContext An external context (such as a ServletContext) to be associated with the LoggerContext.
     * @param configLocation The URI for the configuration to use.
     * @param name The LoggerContext name.
     * @return a LoggerContext.
     */
    public LoggerContext getContext(final ClassLoader loader, final boolean currentContext,
            final Object externalContext, final URI configLocation, final String name);
    
    /**
     * Shutdown using the LoggerContext appropriate for the caller of this method.
     * This is equivalent to calling {@code LogManager.shutdown(false)}.
     *
     * This call is synchronous and will block until shut down is complete.
     * This may include flushing pending log events over network connections.
     *
     * @since 2.6
     */
    public void shutdown();
    
    /**
     * Shutdown the logging system if the logging system supports it.
     * This is equivalent to calling {@code LogManager.shutdown(LogManager.getContext(currentContext))}.
     *
     * This call is synchronous and will block until shut down is complete.
     * This may include flushing pending log events over network connections.
     *
     * @param currentContext if true a default LoggerContext (may not be the LoggerContext used to create a Logger
     *            for the calling class) will be used.
     *            If false the LoggerContext appropriate for the caller of this method is used. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            used and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be used.
     * @since 2.6
     */
    public void shutdown(final boolean currentContext);
    
    /**
     * Shutdown the logging system if the logging system supports it.
     * This is equivalent to calling {@code LogManager.shutdown(LogManager.getContext(currentContext))}.
     *
     * This call is synchronous and will block until shut down is complete.
     * This may include flushing pending log events over network connections.
     *
     * @param currentContext if true a default LoggerContext (may not be the LoggerContext used to create a Logger
     *            for the calling class) will be used.
     *            If false the LoggerContext appropriate for the caller of this method is used. For
     *            example, in a web application if the caller is a class in WEB-INF/lib then one LoggerContext may be
     *            used and if the caller is a class in the container's classpath then a different LoggerContext may
     *            be used.
     * @param allContexts if true all LoggerContexts that can be located will be shutdown.
     * @since 2.13.0
     */
    public void shutdown(final boolean currentContext, final boolean allContexts);
    
    /**
     * Shutdown the logging system if the logging system supports it.
     *
     * This call is synchronous and will block until shut down is complete.
     * This may include flushing pending log events over network connections.
     *
     * @param context the LoggerContext.
     * @since 2.6
     */
    public void shutdown(final LoggerContext context);
    
    /**
     * Returns a formatter Logger using the fully qualified name of the calling Class as the Logger name.
     * <p>
     * This logger lets you use a {@link java.util.Formatter} string in the message to format parameters.
     * </p>
     *
     * @return The Logger for the calling class.
     * @throws UnsupportedOperationException if the calling class cannot be determined.
     * @since 2.4
     */
    public Logger getFormatterLogger();
    
    /**
     * Returns a formatter Logger using the fully qualified name of the Class as the Logger name.
     * <p>
     * This logger let you use a {@link java.util.Formatter} string in the message to format parameters.
     * </p>
     * <p>
     * Short-hand for {@code getLogger(clazz, StringFormatterMessageFactory.INSTANCE)}
     * </p>
     *
     * @param clazz The Class whose name should be used as the Logger name.
     * @return The Logger, created with a {@link StringFormatterMessageFactory}
     * @throws UnsupportedOperationException if {@code clazz} is {@code null} and the calling class cannot be
     *             determined.
     * @see Logger#fatal(Marker, String, Object...)
     * @see Logger#fatal(String, Object...)
     * @see Logger#error(Marker, String, Object...)
     * @see Logger#error(String, Object...)
     * @see Logger#warn(Marker, String, Object...)
     * @see Logger#warn(String, Object...)
     * @see Logger#info(Marker, String, Object...)
     * @see Logger#info(String, Object...)
     * @see Logger#debug(Marker, String, Object...)
     * @see Logger#debug(String, Object...)
     * @see Logger#trace(Marker, String, Object...)
     * @see Logger#trace(String, Object...)
     * @see StringFormatterMessageFactory
     */
    public Logger getFormatterLogger(final Class<?> clazz);
    
    /**
     * Returns a formatter Logger using the fully qualified name of the value's Class as the Logger name.
     * <p>
     * This logger let you use a {@link java.util.Formatter} string in the message to format parameters.
     * </p>
     * <p>
     * Short-hand for {@code getLogger(value, StringFormatterMessageFactory.INSTANCE)}
     * </p>
     *
     * @param value The value's whose class name should be used as the Logger name.
     * @return The Logger, created with a {@link StringFormatterMessageFactory}
     * @throws UnsupportedOperationException if {@code value} is {@code null} and the calling class cannot be
     *             determined.
     * @see Logger#fatal(Marker, String, Object...)
     * @see Logger#fatal(String, Object...)
     * @see Logger#error(Marker, String, Object...)
     * @see Logger#error(String, Object...)
     * @see Logger#warn(Marker, String, Object...)
     * @see Logger#warn(String, Object...)
     * @see Logger#info(Marker, String, Object...)
     * @see Logger#info(String, Object...)
     * @see Logger#debug(Marker, String, Object...)
     * @see Logger#debug(String, Object...)
     * @see Logger#trace(Marker, String, Object...)
     * @see Logger#trace(String, Object...)
     * @see StringFormatterMessageFactory
     */
    public Logger getFormatterLogger(final Object value);
    
    /**
     * Returns a formatter Logger with the specified name.
     * <p>
     * This logger let you use a {@link java.util.Formatter} string in the message to format parameters.
     * </p>
     * <p>
     * Short-hand for {@code getLogger(name, StringFormatterMessageFactory.INSTANCE)}
     * </p>
     *
     * @param name The logger name. If null it will default to the name of the calling class.
     * @return The Logger, created with a {@link StringFormatterMessageFactory}
     * @throws UnsupportedOperationException if {@code name} is {@code null} and the calling class cannot be determined.
     * @see Logger#fatal(Marker, String, Object...)
     * @see Logger#fatal(String, Object...)
     * @see Logger#error(Marker, String, Object...)
     * @see Logger#error(String, Object...)
     * @see Logger#warn(Marker, String, Object...)
     * @see Logger#warn(String, Object...)
     * @see Logger#info(Marker, String, Object...)
     * @see Logger#info(String, Object...)
     * @see Logger#debug(Marker, String, Object...)
     * @see Logger#debug(String, Object...)
     * @see Logger#trace(Marker, String, Object...)
     * @see Logger#trace(String, Object...)
     * @see StringFormatterMessageFactory
     */
    public Logger getFormatterLogger(final String name);
    
    /**
	 * Returns a Logger with the name of the calling class.
	 *
	 * @return The Logger for the calling class.
	 * @throws UnsupportedOperationException if the calling class cannot be determined.
	 */
	public Logger getLogger();

	/**
     * Returns a Logger using the fully qualified name of the Class as the Logger name.
     *
     * @param clazz The Class whose name should be used as the Logger name. If null it will default to the calling
     *            class.
     * @return The Logger.
     * @throws UnsupportedOperationException if {@code clazz} is {@code null} and the calling class cannot be
     *             determined.
     */
    public Logger getLogger(final Class<?> clazz);
    
    /**
     * Returns a Logger using the fully qualified name of the Class as the Logger name.
     *
     * @param clazz The Class whose name should be used as the Logger name. If null it will default to the calling
     *            class.
     * @param messageFactory The message factory is used only when creating a logger, subsequent use does not change the
     *            logger but will log a warning if mismatched.
     * @return The Logger.
     * @throws UnsupportedOperationException if {@code clazz} is {@code null} and the calling class cannot be
     *             determined.
     */
    public Logger getLogger(final Class<?> clazz, final MessageFactory messageFactory);
    
    /**
     * Returns a Logger using the fully qualified class name of the value as the Logger name.
     *
     * @param value The value whose class name should be used as the Logger name. If null the name of the calling class
     *            will be used as the logger name.
     * @return The Logger.
     * @throws UnsupportedOperationException if {@code value} is {@code null} and the calling class cannot be
     *             determined.
     */
    public Logger getLogger(final Object value);
    
    /**
     * Returns a Logger using the fully qualified class name of the value as the Logger name.
     *
     * @param value The value whose class name should be used as the Logger name. If null the name of the calling class
     *            will be used as the logger name.
     * @param messageFactory The message factory is used only when creating a logger, subsequent use does not change the
     *            logger but will log a warning if mismatched.
     * @return The Logger.
     * @throws UnsupportedOperationException if {@code value} is {@code null} and the calling class cannot be
     *             determined.
     */
    public Logger getLogger(final Object value, final MessageFactory messageFactory);
    
    /**
     * Returns a Logger with the specified name.
     *
     * @param name The logger name. If null the name of the calling class will be used.
     * @return The Logger.
     * @throws UnsupportedOperationException if {@code name} is {@code null} and the calling class cannot be determined.
     */
    public Logger getLogger(final String name);
    
    /**
     * Returns a Logger with the specified name.
     *
     * @param name The logger name. If null the name of the calling class will be used.
     * @param messageFactory The message factory is used only when creating a logger, subsequent use does not change the
     *            logger but will log a warning if mismatched.
     * @return The Logger.
     * @throws UnsupportedOperationException if {@code name} is {@code null} and the calling class cannot be determined.
     */
    public Logger getLogger(final String name, final MessageFactory messageFactory);
    
    /**
     * Returns the root logger.
     *
     * @return the root logger, named {@link #ROOT_LOGGER_NAME}.
     */
    public Logger getRootLogger();

}

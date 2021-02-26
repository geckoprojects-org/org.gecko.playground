package org.gecko.playground.logging;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent.Builder;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.message.Message;
import org.osgi.framework.Bundle;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.admin.LoggerAdmin;

/**
 * Log Listener that connects to the OSGi LogService
 * https://docs.osgi.org/specification/osgi.cmpn/7.0.0/service.log.html
 * This Listener connects to the OSGi LogService and gathers all LogEvents to log them into Log4J2 backend.
 * 
 * This implementation is inspired by the org.apache.felix.logback
 * https://github.com/apache/felix-dev/tree/master/logback
 * https://github.com/apache/felix-dev/blob/master/logback/logback/src/main/java/org/apache/felix/logback/internal/LogbackLogListener.java
 *
 * Limitations:
 * It does not reflect changes in the Log4J configuration into OSGi during runtime.
 */
public class Log4JOSGiLogListener implements LogListener {

	private static final String EVENTS_BUNDLE = "Events.Bundle";
	private static final String EVENTS_FRAMEWORK = "Events.Framework";
	private static final String EVENTS_SERVICE = "Events.Service";
	private static final String LOG_SERVICE = "LogService";
//	private static final String SYSTEM_USER_PATTERN = "${java.system.property:user.name}"; //$NON-NLS-1$
//	private static final String HOST_PATTERN = "${java.system.property:riena.host.name}"; //$NON-NLS-1$
//	private static final String SYSTEM_USER_AND_HOST_PATTERN =
//			SYSTEM_USER_PATTERN + "@" + HOST_PATTERN;

	// OSGi Logger stuff
	private final Map<String, LogLevel> initialLogLevels;
	private final org.osgi.service.log.admin.LoggerContext osgiLoggerContext;
	// Log4J2 logger stuff
	private volatile LoggerContext loggerContext;
	private volatile Logger rootLogger;

	public Log4JOSGiLogListener(LoggerAdmin loggerAdmin) {
		osgiLoggerContext = loggerAdmin.getLoggerContext(null);
		initialLogLevels = osgiLoggerContext.getLogLevels();
		
		LoggerContext log4jLoggerContext = (LoggerContext) LogManager.getContext(true);
		initialize(log4jLoggerContext);
	}

	/**
	 * Update the initial log-levels for OSGi Logging, corresponding to the Log4J configuration
	 * @param context the Log4J log context
	 */
	public void initialize(LoggerContext context) {
		loggerContext = context;
		loggerContext.getRootLogger();
		rootLogger = loggerContext.getRootLogger();
	
		Map<String, LogLevel> updatedLevels = updateLevels(loggerContext, initialLogLevels);
		osgiLoggerContext.setLogLevels(updatedLevels);
	}

	/**
	 * Called from the OSGi LogService
	 * @param entry the OSGi LogEntry
	 */
	@Override
	public void logged(final LogEntry entry) {
		String loggerName = entry.getLoggerName();
		String message = entry.getMessage();
		Object[] arguments = null;
		Level level = from(entry.getLogLevel());
		final AtomicBoolean avoidCallerData = new AtomicBoolean();

		// Format special OSGi log events
		if (EVENTS_BUNDLE.equals(loggerName) ||
				EVENTS_FRAMEWORK.equals(loggerName) ||
				LOG_SERVICE.equals(loggerName)) {

			loggerName = formatBundle(entry.getBundle(), loggerName);
			loggerName+= "test";
			avoidCallerData.set(true);
		} else if (EVENTS_SERVICE.equals(loggerName)) {
			loggerName = formatBundle(entry.getBundle(), loggerName);
			message = message + " {}";
			arguments = new Object[] {entry.getServiceReference()};
			avoidCallerData.set(true);
		}

		Logger logger = loggerContext.getLogger(loggerName);

		// Check to see if there's a logger defined in our configuration and
		// if there is, then make sure it's handled as an override for the
		// effective level.
		if (!logger.equals(rootLogger) && !logger.isEnabled(level)) {
			return;
		}

		// create log message
		Message msg = rootLogger.getMessageFactory().newMessage(message, arguments);

		// create Log4J2 log event and "map" the lof event from OSGi log entry to an log4j2 LogEvent
		Builder eventBuilder = Log4jLogEvent.newBuilder();
		
		// create/set MDC context information
//		StringMap contextData = ContextDataFactory.createContextData();
//		contextData.putValue("corrID", getCorellationId(entry.getThreadInfo()));
//		contextData.putValue("userID", getUserId());
//		contextData.putValue("userAndHost", getUserAndHost());

		LogEvent event = eventBuilder.setLevel(level)
				.setLoggerName(loggerName)
				.setThreadName(entry.getThreadInfo())
				.setThrownProxy(createThrowableProxy(entry.getException()))
//				.setSource(entry.getLocation())
				.setTimeMillis(entry.getTime())
//				.setContextData(contextData)
				.setMessage(msg).build();
		// log that event into Log4J2
		loggerContext.getConfiguration().getLoggerConfig(loggerName).log(event);
	}

	/**
	 * Returns the correlation if for requests if available
	 * @return the correlation if for requests if available
	 */
//	private String getCorellationId(String threadName) {
//		IWorkingContextHolder holder = workingContextHolderRef.get();
//		if (holder != null && holder instanceof IExtendedWorkingContextHolder) {
//			IExtendedWorkingContextHolder extHolder = (IExtendedWorkingContextHolder) holder;
//				WorkingContext context = extHolder.getWorkingContext(threadName);
//				if (context != null && context.getProcessID() != null) {
//					return context.getProcessID().toString();
//				}
//		} 
//		return "";
//	}

	/**
	 * Returns the authenticated user name or an emtpy string
	 * @return the authenticated user name or an emtpy string
	 */
//	private String getUserId() {
//		ISubjectHolder holder = subjectHolderRef.get();
//		if (holder != null) {
//			Subject subject = holder.getSubject();
//			if (subject != null) {
//				Set<Principal> principals = subject.getPrincipals();
//				if (principals != null && !principals.isEmpty()) {
//					return principals.iterator().next().getName();
//				}
//			}
//		} 
//		return "";
//	}

	/**
	 * Returns user and host information or an empty string
	 * @return user and host information or an empty string
	 */
//	private String getUserAndHost() {
//		VariablesPlugin variablesPlugin = VariablesPlugin.getDefault();
//		if (variablesPlugin != null) {
//			IStringVariableManager variableManager = variablesPlugin.getStringVariableManager();
//			if (variableManager != null) {
//				try {
//					return variableManager.performStringSubstitution(SYSTEM_USER_AND_HOST_PATTERN);
//				} catch (CoreException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return "";
//	}

	/**
	 * Format the bundle naming using logger name and bundle information
	 * @param bundle the bundle
	 * @param loggerName the logger 
	 * @return formats the 
	 */
	String formatBundle(Bundle bundle, String loggerName) {
		return new StringBuilder().append(
				loggerName
				).append(
						"."
						).append(
								bundle.getSymbolicName()
								).toString();
	}

	/**
	 * Map log levels from Log4J into OSGi
	 * @param level the Log4J log-level
	 * @return the OSGi log level
	 */
	LogLevel from(Level level) {
		if (Level.ALL.equals(level)) {
			return LogLevel.TRACE;
		}
		else if (Level.DEBUG.equals(level)) {
			return LogLevel.DEBUG;
		}
		else if (Level.ERROR.equals(level)) {
			return LogLevel.ERROR;
		}
		else if (Level.INFO.equals(level)) {
			return LogLevel.INFO;
		}
		else if (Level.TRACE.equals(level)) {
			return LogLevel.TRACE;
		}
		else if (Level.WARN.equals(level)) {
			return LogLevel.WARN;
		}

		return LogLevel.WARN;
	}

	/**
	 * Maps the log levels from OSGi into Log4J
	 * @param logLevel the OSGi log level
	 * @return the Log4J log level
	 */
	Level from(LogLevel logLevel) {
		switch (logLevel) {
			case AUDIT:
				return Level.TRACE;
			case DEBUG:
				return Level.DEBUG;
			case ERROR:
				return Level.ERROR;
			case INFO:
				return Level.INFO;
			case TRACE:
				return Level.TRACE;
			case WARN:
			default:
				return Level.WARN;
		}
	}

	/**
	 * Creates a {@link ThrowableProxy} or null if no {@link Throwable} is provided
	 * @param t the {@link Throwable}, can be null
	 * @return a {@link ThrowableProxy} or <code>null</code>
	 */
	ThrowableProxy createThrowableProxy(Throwable t) {
		if (t == null)
			return null;

		ThrowableProxy throwableProxy = new ThrowableProxy(t);
		return throwableProxy;
	}

	/**
	 * Updates the OSGi LogService LogLevels based upon the configuration and sets default values if necessary
	 * @param loggerContext Log4J logger context
	 * @param levels {@link Map} with LoggerNames to OSGi LogLevels
	 * @return
	 */
	Map<String, LogLevel> updateLevels(LoggerContext loggerContext, Map<String, LogLevel> levels) {
		Map<String, LogLevel> copy = new HashMap<String, LogLevel>(levels);

		Logger root = loggerContext.getRootLogger();
		LogLevel rootLevel = from(root.getLevel());
//		if (rootLevel.implies(LogLevel.INFO)) {
			copy.put(org.osgi.service.log.Logger.ROOT_LOGGER_NAME, rootLevel);
//		} else {
//			copy.put(org.osgi.service.log.Logger.ROOT_LOGGER_NAME, LogLevel.INFO);
//		}
		copy.put(EVENTS_BUNDLE, LogLevel.TRACE);
		copy.put(EVENTS_FRAMEWORK, LogLevel.TRACE);
		copy.put(EVENTS_SERVICE, LogLevel.TRACE);
		copy.put(LOG_SERVICE, LogLevel.TRACE);

		for (String loggerName : loggerContext.getConfiguration().getLoggers().keySet()) {
			LoggerConfig lc = loggerContext.getConfiguration().getLoggerConfig(loggerName);
			if (lc.getParent() == null) {
				continue;
			}
			String name = loggerName;
			Level level = lc.getLevel();
			
			if (level != null) {
				copy.remove(name);
				if (level != Level.OFF) {
					copy.put(name, from(level));
				}
			}
		}

		return copy;
	}

}

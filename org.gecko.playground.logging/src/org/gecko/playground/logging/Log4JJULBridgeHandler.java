package org.gecko.playground.logging;


import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.LocalizedMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.LocationAwareLogger;

/**
 * Implemenation for the Java Util Logging - Handler.
 * This implementation is inspired by the Jul-To-Slf4J Bridge:
 * https://github.com/qos-ch/slf4j/tree/master/jul-to-slf4j
 * https://github.com/qos-ch/slf4j/blob/master/jul-to-slf4j/src/main/java/org/slf4j/bridge/SLF4JBridgeHandler.java
 */
public class Log4JJULBridgeHandler extends Handler {

	// The caller is java.util.logging.Logger
	private static final String FQCN = java.util.logging.Logger.class.getName();
	private static final String UNKNOWN_LOGGER_NAME = "unknown.jul.logger";

	private static final int TRACE_LEVEL_THRESHOLD = Level.FINEST.intValue();
	private static final int DEBUG_LEVEL_THRESHOLD = Level.FINE.intValue();
	private static final int INFO_LEVEL_THRESHOLD = Level.INFO.intValue();
	private static final int WARN_LEVEL_THRESHOLD = Level.WARNING.intValue();
	
	
  /**
   * Adds a Log4JJULBridgeHandler instance to jul's root logger.
   * <p/>
   * <p/>
   * This handler will redirect j.u.l. logging to Log4J. However, only logs enabled
   * in j.u.l. will be redirected. For example, if a log statement invoking a
   * j.u.l. logger is disabled, then the corresponding non-event will <em>not</em>
   * reach Log4JBridgeHandler and cannot be redirected.
   */
  public static void install() {
      getRootLogger().addHandler(new Log4JJULBridgeHandler());
  }

  /**
   * Removes previously installed Log4JJULBridgeHandler instances. 
   *
   * @throws SecurityException A <code>SecurityException</code> is thrown, if a security manager
   *                           exists and if the caller does not have
   *                           LoggingPermission("control").
   */
  public static void uninstall() throws SecurityException {
      java.util.logging.Logger rootLogger = getRootLogger();
      Handler[] handlers = rootLogger.getHandlers();
      for (int i = 0; i < handlers.length; i++) {
          if (handlers[i] instanceof Log4JJULBridgeHandler) {
              rootLogger.removeHandler(handlers[i]);
          }
      }
  }
  
  /**
   * Invoking this method removes/unregisters/detaches all handlers currently attached to the root logger
   * @since 1.6.5
   */
  public static void removeHandlersForRootLogger() {
      java.util.logging.Logger rootLogger = getRootLogger();
      java.util.logging.Handler[] handlers = rootLogger.getHandlers();
      for (int i = 0; i < handlers.length; i++) {
          rootLogger.removeHandler(handlers[i]);
      }
  }
  
  private static java.util.logging.Logger getRootLogger() {
    return java.util.logging.LogManager.getLogManager().getLogger("");
}

	/**
	 * Publish a LogRecord.
	 * <p/>
	 * The logging request was made initially to a Logger object, which
	 * initialized the LogRecord and forwarded it here.
	 * <p/>
	 * This handler ignores the Level attached to the LogRecord, as Log4J cares
	 * about discarding log statements.
	 *
	 * @param record Description of the log event. A null record is silently ignored
	 *               and is not published.
	 */
	@Override
	public void publish(LogRecord record) {
		// Silently ignore null records.
		if (record == null) {
			return;
		}

		Logger log4jLogger = getLog4JLogger(record);
		String message = record.getMessage(); // can be null!
		// this is a check to avoid calling the underlying logging system
		// with a null message. While it is legitimate to invoke j.u.l. with
		// a null message, other logging frameworks do not support this.
		// see also http://jira.qos.ch/browse/SLF4J-99
		if (message == null) {
			message = "";
		}
		if (log4jLogger instanceof LocationAwareLogger) {
			callLocationAwareLogger((LocationAwareLogger) log4jLogger, record);
		} else {
			callPlainLog4JLogger(log4jLogger, record);
		}
	}

	@Override
	public void flush() {
		// empty
	}

	@Override
	public void close() throws SecurityException {
		// empty
	}

	/**
	 * Return the Logger instance that will be used for logging.
	 */
	protected Logger getLog4JLogger(LogRecord record) {
		String name = record.getLoggerName();
		if (name == null) {
			name = UNKNOWN_LOGGER_NAME;
		}
		return org.apache.logging.log4j.LogManager.getLogger(name);
	}

	/**
	 * Logs the message with Log4J location aware logger using a JUL {@link LogRecord}
	 * @param lal the logger 
	 * @param record the JUL {@link LogRecord}
	 */
	protected void callLocationAwareLogger(LocationAwareLogger lal, LogRecord record) {
		int julLevelValue = record.getLevel().intValue();
		org.apache.logging.log4j.Level log4jLevel;

		if (julLevelValue <= TRACE_LEVEL_THRESHOLD) {
			log4jLevel = org.apache.logging.log4j.Level.TRACE;
		} else if (julLevelValue <= DEBUG_LEVEL_THRESHOLD) {
			log4jLevel = org.apache.logging.log4j.Level.DEBUG;
		} else if (julLevelValue <= INFO_LEVEL_THRESHOLD) {
			log4jLevel = org.apache.logging.log4j.Level.INFO;
		} else if (julLevelValue <= WARN_LEVEL_THRESHOLD) {
			log4jLevel = org.apache.logging.log4j.Level.WARN;
		} else {
			log4jLevel = org.apache.logging.log4j.Level.ERROR;
		}
		Message i18nMessage = getMessageI18N(record);
		lal.logMessage(log4jLevel, null, FQCN, null, i18nMessage, record.getThrown());
	}

	/**
	 * Logs the JUL {@link LogRecord} with a Log4J Logger
	 * @param log4jLogger the logger
	 * @param record the {@link LogRecord}
	 */
	protected void callPlainLog4JLogger(Logger log4jLogger, LogRecord record) {
		String i18nMessage = getMessageStringI18N(record);
		int julLevelValue = record.getLevel().intValue();
		if (julLevelValue <= TRACE_LEVEL_THRESHOLD) {
			log4jLogger.trace(i18nMessage, record.getThrown());
		} else if (julLevelValue <= DEBUG_LEVEL_THRESHOLD) {
			log4jLogger.debug(i18nMessage, record.getThrown());
		} else if (julLevelValue <= INFO_LEVEL_THRESHOLD) {
			log4jLogger.info(i18nMessage, record.getThrown());
		} else if (julLevelValue <= WARN_LEVEL_THRESHOLD) {
			log4jLogger.warn(i18nMessage, record.getThrown());
		} else {
			log4jLogger.error(i18nMessage, record.getThrown());
		}
	}

	/**
	 * Get the record's message, possibly via a resource bundle.
	 * @param record the JUL {@link LogRecord}
	 * @return the message {@link String}
	 */
	private String getMessageStringI18N(LogRecord record) {
		String message = record.getMessage();

		if (message == null) {
			return null;
		}

		ResourceBundle bundle = record.getResourceBundle();
		if (bundle != null) {
			try {
				message = bundle.getString(message);
			} catch (MissingResourceException e) {
			}
		}
		Object[] params = record.getParameters();
		// avoid formatting when there are no or 0 parameters. see also
		// http://jira.qos.ch/browse/SLF4J-203
		if (params != null && params.length > 0) {
			try {
				message = MessageFormat.format(message, params);
			} catch (IllegalArgumentException e) {
				// default to the same behavior as in java.util.logging.Formatter.formatMessage(LogRecord)
				// see also http://jira.qos.ch/browse/SLF4J-337
				return message;
			}
		}
		return message;
	}

	/**
	 * Get the record's message, possibly via a resource bundle.
	 * @param record the JUL {@link LogRecord}
	 * @return Log4J Message instance
	 */
	private Message getMessageI18N(LogRecord record) {
		String message = record.getMessage();

		if (message == null) {
			return null;
		}

		ResourceBundle bundle = record.getResourceBundle();
		Object[] params = record.getParameters();
		Message m = null;
		// avoid formatting when there are no or 0 parameters. see also
		// http://jira.qos.ch/browse/SLF4J-203
		if (params != null && params.length > 0) {
			m = new LocalizedMessage(bundle, message, params);
		} else {
			m = new LocalizedMessage(bundle, message);
		}
		return m;
	}

}

package org.gecko.log4.extension;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.message.MessageFactory;
import org.gecko.log4.extension.api.LogManagerProvider;

public class LogManagerProviderImpl implements LogManagerProvider {

	@Override
	public boolean exists(String name) {
		return LogManager.exists(name);
	}

	@Override
	public LoggerContext getContext() {
		return LogManager.getContext();
	}

	@Override
	public LoggerContext getContext(boolean currentContext) {
		return LogManager.getContext(currentContext);
	}

	@Override
	public LoggerContext getContext(ClassLoader loader, boolean currentContext) {
		return LogManager.getContext(loader, currentContext);
	}

	@Override
	public LoggerContext getContext(ClassLoader loader, boolean currentContext, Object externalContext) {
		return LogManager.getContext(loader, currentContext, externalContext);
	}

	@Override
	public LoggerContext getContext(ClassLoader loader, boolean currentContext, URI configLocation) {
		return LogManager.getContext(loader, currentContext, configLocation);
	}

	@Override
	public LoggerContext getContext(ClassLoader loader, boolean currentContext, Object externalContext,
			URI configLocation) {
		return LogManager.getContext(loader, currentContext, externalContext, configLocation);
	}

	@Override
	public LoggerContext getContext(ClassLoader loader, boolean currentContext, Object externalContext,
			URI configLocation, String name) {
		return LogManager.getContext(loader, currentContext, externalContext, configLocation, name);
	}

	@Override
	public void shutdown() {
		LogManager.shutdown();
	}

	@Override
	public void shutdown(boolean currentContext) {
		LogManager.shutdown(currentContext);
	}

	@Override
	public void shutdown(boolean currentContext, boolean allContexts) {
		LogManager.shutdown(currentContext, allContexts);
	}

	@Override
	public void shutdown(LoggerContext context) {
		LogManager.shutdown(context);
	}

	@Override
	public Logger getFormatterLogger() {
		return LogManager.getFormatterLogger();
	}

	@Override
	public Logger getFormatterLogger(Class<?> clazz) {
		return LogManager.getFormatterLogger(clazz);
	}

	@Override
	public Logger getFormatterLogger(Object value) {
		return LogManager.getFormatterLogger(value);
	}

	@Override
	public Logger getFormatterLogger(String name) {
		return LogManager.getFormatterLogger(name);
	}

	@Override
	public Logger getLogger() {
		return LogManager.getLogger();
	}

	@Override
	public Logger getLogger(Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}

	@Override
	public Logger getLogger(Class<?> clazz, MessageFactory messageFactory) {
		return LogManager.getLogger(clazz, messageFactory);
	}

	@Override
	public Logger getLogger(Object value) {
		return LogManager.getLogger(value);
	}

	@Override
	public Logger getLogger(Object value, MessageFactory messageFactory) {
		return LogManager.getLogger(value, messageFactory);
	}

	@Override
	public Logger getLogger(String name) {
		return LogManager.getLogger(name);
	}

	@Override
	public Logger getLogger(String name, MessageFactory messageFactory) {
		return LogManager.getLogger(name, messageFactory);
	}

	@Override
	public Logger getRootLogger() {
		return LogManager.getRootLogger();
	}

}

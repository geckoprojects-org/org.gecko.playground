package org.gecko.playground.exchange.api;

import org.gecko.playground.model.orders.Fill;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface FillListener {

	void filled(String exchangeId, Fill fill);

}

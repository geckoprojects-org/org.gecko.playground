package org.gecko.playground.model.orders;

import java.io.Serializable;


public final class Fill implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final long fillQuantity;
	private final Order bidOrder;
	private final Order askOrder;
	private final long time;

	public Fill(long fillQuantity, Order bidOrder, Order askOrder, long time) {
		this.fillQuantity = fillQuantity;
		this.bidOrder = bidOrder;
		this.askOrder = askOrder;
		this.time = time;
	}

	/**
	 * @return The quantity of each order that was filled. This quantity cannot
	 *         be more than the lesser out of the bid order quantity and the ask
	 *         order quantity, as they each existed at the time of the fill.
	 */
	public long getFillQuantity() {
		return fillQuantity;
	}

	/**
	 * The bid-side order that was filled, either in whole or in part.
	 */
	public Order getBidOrder() {
		return bidOrder;
	}

	/**
	 * The ask-side order that was filled, either in whole or in part.
	 */
	public Order getAskOrder() {
		return askOrder;
	}

	/**
	 * @return The time at which the fill was generated.
	 */
	public long getTime() {
		return time;
	}

}

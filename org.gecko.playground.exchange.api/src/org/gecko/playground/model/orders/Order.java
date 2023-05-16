package org.gecko.playground.model.orders;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final long customerId;
	private final String symbol;
	private final Side side;
	private final long price;
	private final long expiry;
	private final long initialQuantity;
	private final AtomicLong quantity;
	
	public Order(String symbol, Side side, long initialQuantity, long price) {
		this(UUID.randomUUID(), 0, symbol, side, initialQuantity, price, 0);
	}
	
	public Order(UUID id, long customerId, String symbol, Side side, long initialQuantity, long price, long expiry) {
		this.id = id;
		this.customerId = customerId;
		this.symbol = symbol;
		this.side = side;
		this.initialQuantity = initialQuantity;
		this.quantity = new AtomicLong(initialQuantity);
		this.price = price;
		this.expiry = expiry;
	}

	public UUID getId() {
		return id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public String getSymbol() {
		return symbol;
	}

	public Side getSide() {
		return side;
	}
	
	public long getInitialQuantity() {
		return initialQuantity;
	}

	public long getQuantity() {
		return quantity.get();
	}
	
	public void setQuantity(long newQuantity) {
		quantity.set(newQuantity);
	}

	public long getPrice() {
		return price;
	}

	public long getExpiry() {
		return expiry;
	}

	@Override
	public String toString() {
		return String.format("Order [id=%s, customerId=%s, symbol=%s, side=%s, quantity=%d, initialQuantity=%d, price=%d, expiry=%s]", id, customerId, symbol, side, quantity.get(), initialQuantity, price, expiry);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

package org.gecko.playground.model.orders;

public enum Side {
	Bid, Ask;

	public Side opposite() {
		Side result;
		switch (this) {
		case Bid:
			result = Ask;
			break;
		case Ask:
			result = Bid;
			break;
		default:
			throw new IllegalArgumentException("Invalid side: " + this);
		}
		return result;
	}

}

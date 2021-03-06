package ca.ualberta.cs.xpertsapp.model;

import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

/**
 * Represents the Complete state of a trade
 */
public class TradeStateComplete implements TradeState {
	@Override
	/** accept the trade - crash */
	public void accept(Trade context) {
		throw new AssertionError();
	}

	@Override
	/** decline the trade - crash */
	public void decline(Trade context) {
		throw new AssertionError();
	}

	@Override
	/** cancel the trade - crash */
	public void cancel(Trade context) {
		throw new AssertionError();
	}

	@Override
	public void complete(Trade context) {
		throw new AssertionError();
	}
}
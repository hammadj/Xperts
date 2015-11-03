package ca.ualberta.cs.xpertsapp.model;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;

public class TradeManager implements IObserver {
	private Map<String, Trade> trades = new HashMap<String, Trade>();

	// Get/Set
	public List<Trade> getTrades() {
		List<Trade> users = new ArrayList<Trade>();
		for (Trade user : this.trades.values()) {
			users.add(user);
		}
		return users;
	}

	public Trade getTrade(String id) {
		// If we have the trade loaded
		if (this.trades.containsKey(id)) {
			return this.trades.get(id);
		}
		// TODO:
		try {
			SearchHit<Trade> loadedTrades = IOManager.sharedManager().fetchData(Constants.serverTradeExtension() + id, new TypeToken<SearchHit<Trade>>() {
			});
			if (loadedTrades.isFound()) {
				this.addTrade(loadedTrades.getSource());
				return loadedTrades.getSource();
			} else {
				// TODO:
				return null;
			}
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}

	public Trade newTrade(String borrower, boolean isCounter) {
		Trade trade = new Trade(UUID.randomUUID().toString(), isCounter, UserManager.sharedManager().localUser().getID(), borrower);
		return trade;
	}

	public void addTrade(Trade trade) {
		trade.addObserver(this);
		this.trades.put(trade.getID(), trade);
	}

	public void clearCache() {
		this.trades.clear();
		// TODO:
	}

	// Singleton
	private static TradeManager instance = new TradeManager();

	private TradeManager() {
	}

	public static TradeManager sharedManager() {
		return TradeManager.instance;
	}

	// IObserver
	@Override
	public void notify(IObservable observable) {
		// TODO:
		IOManager.sharedManager().storeData(observable, Constants.serverTradeExtension() + ((Trade) observable).getID());
	}
}
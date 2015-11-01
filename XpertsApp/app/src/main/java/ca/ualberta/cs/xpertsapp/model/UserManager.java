package ca.ualberta.cs.xpertsapp.model;

import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;

public class UserManager implements IObserver {
	private Map<String, User> users = new HashMap<String, User>();

	// Get/Set
	public List<User> getUsers() {
		return new ArrayList<User>(this.users.values());
	}

	private void addUser(User user) {
		user.addObserver(this);
		this.users.put(user.getID(), user);
	}

	public User getUser(String id) {
		// If we have the user loaded
		if (this.users.containsKey(id)) {
			return this.users.get(id);
		}
		// TODO:
		try {
			SearchHit<User> loadedUser = IOManager.sharedManager().fetchData(Constants.serverUserExtension() + id);
			if (loadedUser.isFound()) {
				this.addUser(loadedUser.getSource());
				return loadedUser.getSource();
			} else {
				User newUser = new User(id);
				this.addUser(newUser);
				return newUser;
			}
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}

	public User localUser() {
		return this.getUser(Constants.deviceUUID());
	}

	/**
	 * Get a list of users sorted by match relevance
	 *
	 * @param meta What to search for
	 * @return The list of matching users with the most relevant first
	 */
	public List<User> findUsers(String meta) {
		List<SearchHit<User>> found = IOManager.sharedManager().searchData(Constants.serverUserExtension() + Constants.serverSearchExtension() + meta);
		List<User> users = new ArrayList<User>();
		for (SearchHit<User> user : found) {
			users.add(user.getSource());
		}
		return users;
	}

	public void clearCache() {
		this.users.clear();
		this.localUser();
		// TODO:
	}

	// Singleton
	private static UserManager instance = new UserManager();

	private UserManager() {
	}

	public static UserManager sharedManager() {
		return UserManager.instance;
	}

	// IObserver
	@Override
	public void notify(IObservable observable) {
		// TODO:
		IOManager.sharedManager().storeData(observable, Constants.serverUserExtension() + ((User) observable).getID());
	}
}
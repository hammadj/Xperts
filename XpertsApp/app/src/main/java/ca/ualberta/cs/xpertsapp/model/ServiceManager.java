package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class ServiceManager implements IObserver {

	private Map<String, Service> services = new HashMap<String, Service>();

	public List<Service> getServices() {
		return new ArrayList<Service>(this.services.values());
	}

	public void addService(Service service) {
		service.addObserver(this);
		this.services.put(service.getID(), service);
	}

	public void clearCache() {
		this.services.clear();
	}

	// Singleton
	private static ServiceManager instance = new ServiceManager();

	private ServiceManager() {
	}

	public static ServiceManager sharedManager() {
		return ServiceManager.instance;
	}

	/// IObserver
	@Override
	public void notify(IObservable observable) {
		// TODO: Save the changes
	}
}
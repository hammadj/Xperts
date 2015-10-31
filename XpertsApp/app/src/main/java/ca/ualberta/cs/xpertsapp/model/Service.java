package ca.ualberta.cs.xpertsapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class Service implements IObservable {

	private String id;
	private String name = "";
	private String description = "";
	private String category = "";
	private List<Bitmap> pictures = new ArrayList<Bitmap>();
	private boolean shareable = true;
	private String owner = "";

	Service(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (this.isEditable()) {
			this.name = name;
			this.notifyObservers();
		}

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (this.isEditable()) {
			this.description = description;
			this.notifyObservers();
		}
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		if (this.isEditable()) {
			this.category = category;
			this.notifyObservers();
		}
	}

	public List<Bitmap> getPictures() {
		return pictures;
	}

	public void addPicture(Bitmap picture) {
		if (this.isEditable()) {
			pictures.add(picture);
			this.notifyObservers();
		}
	}

	public void removePicture(Bitmap picture) {
		if (this.isEditable()) {
			pictures.remove(picture);
			this.notifyObservers();
		}
	}

	public boolean isShareable() {
		return shareable;
	}

	public void setShareable(boolean shareable) {
		if (this.isEditable()) {
			this.shareable = shareable;
			this.notifyObservers();
		}
	}

	private User getOwner() {
		// TO DO!!
		return null;
	}

	public void setOwner(String owner) {
		if (this.isEditable()) {
			this.owner = owner;
			this.notifyObservers();
		}
	}

	private boolean isEditable() {
		// TO DO!!
		return true;
	}

	private List<IObserver> observers = new ArrayList<IObserver>();

	@Override
	public void addObserver(IObserver observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (IObserver observer : this.observers) {
			observer.notify(this);
		}
	}
}
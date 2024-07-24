package de.tum.mvc.database.Model;
import de.tum.mvc.database.View.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

	private List<Observer> observers = new ArrayList<>();

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void addAllObservers(List<Observer> observers) {
		this.observers.addAll(observers);
	}
}

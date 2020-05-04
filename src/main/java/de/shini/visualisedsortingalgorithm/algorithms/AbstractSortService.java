package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import javafx.concurrent.Service;

public abstract class AbstractSortService<T> extends Service<Void> {

	protected List<T> list;
	protected int listSize;
	protected long millis;
	protected final BiPredicate<T, T> compare;
	protected final BiConsumer<T, T> swap;

	public AbstractSortService(List<T> list, BiPredicate<T, T> compare) {
		this(list, compare, null);
	}

	public AbstractSortService(List<T> list, BiPredicate<T, T> compare, long millis) {
		this(list, compare, null, millis);
	}

	public AbstractSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap) {
		this(list, compare, null, 0l);
	}

	public AbstractSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap, long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("millis should not be negative");
		}
		this.list = list;
		this.listSize = list.size();
		this.compare = compare;
		this.swap = swap;
		this.millis = millis;
	}

	public void setLines(List<T> list) {
		this.list = list;
		this.listSize = list.size();
	}

	public void setMillis(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("millis should not be negative");
		}
		this.millis = millis;
	}

}

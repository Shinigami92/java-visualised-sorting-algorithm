package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;

import javafx.concurrent.Task;

public class QuickSortNotThreadedService<T, U> extends AbstractSortService<T> {

	private final Function<T, U> pivotSave;
	private final BiPredicate<T, U> leftCompare;
	private final BiPredicate<T, U> rightCompare;

	public QuickSortNotThreadedService(List<T> list, BiPredicate<T, U> leftCompare, BiPredicate<T, U> rightCompare) {
		super(list, null);
		this.pivotSave = null;
		this.leftCompare = leftCompare;
		this.rightCompare = rightCompare;
	}

	public QuickSortNotThreadedService(List<T> list, BiPredicate<T, U> leftCompare, BiPredicate<T, U> rightCompare,
			long millis) {
		super(list, null, millis);
		this.pivotSave = null;
		this.leftCompare = leftCompare;
		this.rightCompare = rightCompare;
	}

	public QuickSortNotThreadedService(List<T> list, Function<T, U> pivotSave, BiPredicate<T, U> leftCompare,
			BiPredicate<T, U> rightCompare) {
		super(list, null);
		this.pivotSave = pivotSave;
		this.leftCompare = leftCompare;
		this.rightCompare = rightCompare;
	}

	public QuickSortNotThreadedService(List<T> list, Function<T, U> pivotSave, BiPredicate<T, U> leftCompare,
			BiPredicate<T, U> rightCompare, long millis) {
		super(list, null, millis);
		this.pivotSave = pivotSave;
		this.leftCompare = leftCompare;
		this.rightCompare = rightCompare;
	}

	public QuickSortNotThreadedService(List<T> list, Function<T, U> pivotSave, BiPredicate<T, U> leftCompare,
			BiPredicate<T, U> rightCompare, BiConsumer<T, T> swap) {
		super(list, null, swap);
		this.pivotSave = pivotSave;
		this.leftCompare = leftCompare;
		this.rightCompare = rightCompare;
	}

	public QuickSortNotThreadedService(List<T> list, Function<T, U> pivotSave, BiPredicate<T, U> leftCompare,
			BiPredicate<T, U> rightCompare, BiConsumer<T, T> swap, long millis) {
		super(list, null, swap, millis);
		this.pivotSave = pivotSave;
		this.leftCompare = leftCompare;
		this.rightCompare = rightCompare;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			private void quicksort(int lo, int hi) throws InterruptedException {
				if (lo < hi) {
					int p = partition(lo, hi);
					quicksort(lo, p);
					quicksort(p + 1, hi);
				}
			}

			private int partition(int lo, int hi) throws InterruptedException {
				@SuppressWarnings("unchecked")
				final U pivot = pivotSave != null ? pivotSave.apply(list.get(lo)) : (U) list.get(lo);
				int i = lo - 1;
				int j = hi + 1;
				while (true) {
					do {
						i++;
					} while (leftCompare.test(list.get(i), pivot));
					do {
						j--;
					} while (rightCompare.test(list.get(j), pivot));
					if (i >= j) {
						return j;
					}
					if (swap != null) {
						swap.accept(list.get(i), list.get(j));
					} else {
						Collections.swap(list, i, j);
					}
					Thread.sleep(millis);
				}
			}

			@Override
			protected Void call() throws Exception {
				quicksort(0, listSize - 1);
				return null;
			}
		};
	}
}

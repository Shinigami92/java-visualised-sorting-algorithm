package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import javafx.concurrent.Task;

public class SelectionSortService<T> extends AbstractSortService<T> {

	public SelectionSortService(List<T> list, BiPredicate<T, T> compare) {
		super(list, compare, null);
	}

	public SelectionSortService(List<T> list, BiPredicate<T, T> compare, long millis) {
		super(list, compare, null, millis);
	}

	public SelectionSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap) {
		super(list, compare, swap);
	}

	public SelectionSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap, long millis) {
		super(list, compare, swap, millis);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (int j = 0; j < listSize - 1; j++) {
					int iMin = j;
					for (int i = j + 1; i < listSize; i++) {
						if (compare.test(list.get(i), list.get(iMin))) {
							iMin = i;
						}
					}
					if (iMin != j) {
						if (swap != null) {
							swap.accept(list.get(j), list.get(iMin));
						} else {
							Collections.swap(list, j, iMin);
						}
						Thread.sleep(millis);
					}
				}

				return null;
			}
		};
	}
}

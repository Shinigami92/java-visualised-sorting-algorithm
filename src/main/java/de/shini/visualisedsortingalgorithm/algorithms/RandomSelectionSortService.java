package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import javafx.concurrent.Task;

public class RandomSelectionSortService<T> extends AbstractSortService<T> {

	private static final Random RND = new Random();

	public RandomSelectionSortService(List<T> list, BiPredicate<T, T> compare) {
		super(list, compare);
	}

	public RandomSelectionSortService(List<T> list, BiPredicate<T, T> compare, long millis) {
		super(list, compare, millis);
	}

	public RandomSelectionSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap) {
		super(list, compare, swap);
	}

	public RandomSelectionSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap, long millis) {
		super(list, compare, swap, millis);
	}

	private boolean isFinished() {
		for (int i = 0; i < listSize - 1; i++) {
			if (compare.test(list.get(i), list.get(i + 1))) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (isFinished()) {
					return null;
				}
				while (true) {
					int i1 = RND.nextInt(listSize);
					int i2 = RND.nextInt(listSize);
					if (i1 == i2) {
						continue;
					}
					T t1 = list.get(i1);
					T t2 = list.get(i2);
					if (compare.test(t1, t2)) {
						if (swap != null) {
							swap.accept(t1, t2);
						} else {
							Collections.swap(list, i1, i2);
						}
						Thread.sleep(millis);
					}
					if (isFinished()) {
						break;
					}
				}

				return null;
			}
		};
	}
}

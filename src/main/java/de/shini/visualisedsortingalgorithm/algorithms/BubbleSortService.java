package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import javafx.concurrent.Task;

public class BubbleSortService<T> extends AbstractSortService<T> {

	public BubbleSortService(List<T> list, BiPredicate<T, T> compare) {
		super(list, compare);
	}

	public BubbleSortService(List<T> list, BiPredicate<T, T> compare, long millis) {
		super(list, compare, millis);
	}

	public BubbleSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap) {
		super(list, compare, swap);
	}

	public BubbleSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap, long millis) {
		super(list, compare, swap, millis);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (int n = listSize; n > 1; n--) {
					for (int i = 0; i < listSize - 1; i++) {
						T t1 = list.get(i);
						T t2 = list.get(i + 1);
						if (compare.test(t1, t2)) {
							if (swap != null) {
								swap.accept(t1, t2);
							} else {
								Collections.swap(list, i, i + 1);
							}
							Thread.sleep(millis);
						}
					}
				}
				return null;
			}
		};
	}

}

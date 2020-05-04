package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import javafx.concurrent.Task;

public class InsertionSortService<T> extends AbstractSortService<T> {

	public InsertionSortService(List<T> list, BiPredicate<T, T> compare) {
		super(list, compare);
	}

	public InsertionSortService(List<T> list, BiPredicate<T, T> compare, long millis) {
		super(list, compare, millis);
	}

	public InsertionSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap) {
		super(list, compare, swap);
	}

	public InsertionSortService(List<T> list, BiPredicate<T, T> compare, BiConsumer<T, T> swap, long millis) {
		super(list, compare, swap, millis);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				// Wiki: why not size-1?
				// for i ← 1 to length(A) - 1
				// j ← i
				// while j > 0 and A[j-1] > A[j]
				// swap A[j] and A[j-1]
				// j ← j - 1
				// end while
				// end for
				for (int i = 1; i < listSize; i++) {
					int j = i;
					while (j > 0) {
						T t1 = list.get(j - 1);
						T t2 = list.get(j);
						if (!compare.test(t1, t2)) {
							break;
						}
						if (swap != null) {
							swap.accept(t2, t1);
						} else {
							Collections.swap(list, j, j - 1);
						}
						// System.out.println(list.stream().map(k ->
						// k.toString()).collect(Collectors.joining(", ",
						// "swap[" + t2 + ", " + t1 + "] -> ", "")));
						Thread.sleep(millis);
						j--;
					}
				}
				return null;
			}
		};
	}

}

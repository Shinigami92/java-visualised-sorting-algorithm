package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;

import javafx.concurrent.Task;

public class ShellSortService<T, U> extends AbstractSortService<T> {

	private final List<Integer> gapSequence;
	private final Function<T, U> tempSave;
	private final BiPredicate<T, U> compare;
	private final BiConsumer<T, T> swapPart1;
	private final BiConsumer<T, U> swapPart2;

	public ShellSortService(List<T> list, Function<T, U> tempSave, BiPredicate<T, U> compare,
			BiConsumer<T, T> swapPart1, BiConsumer<T, U> swapPart2) {
		super(list, null);
		this.tempSave = tempSave;
		this.compare = compare;
		this.swapPart1 = swapPart1;
		this.swapPart2 = swapPart2;
		this.gapSequence = Arrays.asList(1750, 701, 301, 132, 57, 23, 10, 4, 1);
	}

	public ShellSortService(List<T> list, Function<T, U> tempSave, BiPredicate<T, U> compare,
			BiConsumer<T, T> swapPart1, BiConsumer<T, U> swapPart2, long millis) {
		super(list, null, millis);
		this.tempSave = tempSave;
		this.compare = compare;
		this.swapPart1 = swapPart1;
		this.swapPart2 = swapPart2;
		this.gapSequence = Arrays.asList(1750, 701, 301, 132, 57, 23, 10, 4, 1);
	}

	public ShellSortService(List<T> list, BiPredicate<T, U> compare) {
		super(list, null);
		this.compare = compare;
		this.tempSave = null;
		this.swapPart1 = null;
		this.swapPart2 = null;
		this.gapSequence = Arrays.asList(1750, 701, 301, 132, 57, 23, 10, 4, 1);
	}

	public ShellSortService(List<T> list, BiPredicate<T, U> compare, long millis) {
		super(list, null, millis);
		this.compare = compare;
		this.tempSave = null;
		this.swapPart1 = null;
		this.swapPart2 = null;
		this.gapSequence = Arrays.asList(1750, 701, 301, 132, 57, 23, 10, 4, 1);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			protected Void call() throws Exception {
				for (Integer gap : gapSequence) {
					int gapVal = gap.intValue();
					if (gapVal > listSize) {
						continue;
					}
					for (int i = gapVal; i < listSize; i++) {
						final U temp = tempSave != null ? tempSave.apply(list.get(i)) : (U) list.get(i);
						int j;
						for (j = i; j >= gapVal && compare.test(list.get(j - gapVal), temp); j -= gap) {
							if (swapPart1 != null) {
								swapPart1.accept(list.get(j), list.get(j - gapVal));
							} else {
								Collections.swap(list, j, j - gapVal);
							}
							// Thread.sleep(millis);
						}
						// JA tempSave!
						if (tempSave != null) {
							swapPart2.accept(list.get(j), temp);
						} else {
							list.set(j, (T) temp);
						}
						Thread.sleep(millis);
					}
				}
				return null;
			}
		};
	}
}

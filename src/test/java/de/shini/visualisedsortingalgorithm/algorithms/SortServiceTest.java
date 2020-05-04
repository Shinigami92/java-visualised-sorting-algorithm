package de.shini.visualisedsortingalgorithm.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Timer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import javafx.application.Platform;

@RunWith(JfxRunner.class)
public class SortServiceTest {

	// Test-Variables
	private final List<Integer> list;
	private static final int N = 100;

	// Test-Init
	public SortServiceTest() {
		list = new ArrayList<>();
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("@BeforeClass");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("@AfterClass");
	}

	@Before
	public void setUp() {
		System.out.println("@Before");
		for (int i = 0; i < N; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
	}

	@After
	public void tearDown() {
		System.out.println("@After");
		list.clear();
	}

	private class State {
		private boolean finished = false;

		public void finish() {
			this.finished = true;
		}

		public boolean isFinished() {
			return this.finished;
		}
	}

	// Tests
	@Test(timeout = 30000)
	public void testBubbleSort() {
		System.out.println("BubbleSort");
		printList();
		BubbleSortService<Integer> sortService = new BubbleSortService<>(list, (i1, i2) -> i1 > i2);
		sortService.start();
		waitingForService(sortService);
		printList();
		Assert.assertTrue(checkSorted());
	}

	@Test(timeout = 30000)
	public void testSelectionSort() {
		System.out.println("SelectionSort");
		printList();
		SelectionSortService<Integer> sortService = new SelectionSortService<>(list, (i1, i2) -> i1 < i2);
		sortService.start();
		waitingForService(sortService);
		printList();
		Assert.assertTrue(checkSorted());
	}

	@Test(timeout = 30000)
	public void testInsertionSort() {
		System.out.println("InsertionSort");
		printList();
		InsertionSortService<Integer> sortService = new InsertionSortService<>(list, (i1, i2) -> i1 > i2);
		sortService.start();
		waitingForService(sortService);
		printList();
		Assert.assertTrue(checkSorted());
	}

	@Test(timeout = 30000)
	public void testShellSort() {
		System.out.println("ShellSort");
		printList();
		ShellSortService<Integer, Integer> sortService = new ShellSortService<>(list, (i, v) -> i > v);
		sortService.start();
		waitingForService(sortService);
		printList();
		Assert.assertTrue(checkSorted());
	}

	@Test(timeout = 30000)
	public void testQuickSort() {
		System.out.println("QuickSort");
		printList();
		QuickSortService<Integer, Integer> sortService = new QuickSortService<>(list, (i, v) -> i < v, (i, v) -> i > v);
		sortService.start();
		waitingForService(sortService);
		printList();
		Assert.assertTrue(checkSorted());
	}

	@Test(timeout = 30000)
	public void testQuickSortNotThreaded() {
		System.out.println("QuickSortNotThreaded");
		printList();
		QuickSortNotThreadedService<Integer, Integer> sortService = new QuickSortNotThreadedService<>(list, (i, v) -> i < v, (i, v) -> i > v);
		sortService.start();
		waitingForService(sortService);
		printList();
		Assert.assertTrue(checkSorted());
	}

	@Test(timeout = 30000)
	public void testRandomSelectionSort() {
		System.out.println("RandomSelectionSort");
		printList();
		RandomSelectionSortService<Integer> sortService = new RandomSelectionSortService<>(list,
				(i1, i2) -> list.indexOf(i1) < list.indexOf(i2) && i1 > i2
						|| list.indexOf(i1) > list.indexOf(i2) && i1 < i2);
		sortService.start();
		waitingForService(sortService);
		printList();
		Assert.assertTrue(checkSorted());
	}

	// Test-Help-Methods
	private void waitingForService(AbstractSortService<?> service) {
		State state = new State();
		Timer t = new Timer(10, e -> {
			Platform.runLater(() -> {
				if (!service.isRunning()) {
					state.finish();
				}
			});
			if (state.isFinished()) {
				((Timer) e.getSource()).stop();
			}
		});
		t.start();
		while (!state.isFinished()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void printList() {
		// System.out.println(list.stream().map(i ->
		// i.toString()).collect(Collectors.joining(", ")));
	}

	private boolean checkSorted() {
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i) > list.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

}

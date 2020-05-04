package de.shini.visualisedsortingalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import de.shini.visualisedsortingalgorithm.Canvas.Shema;
import de.shini.visualisedsortingalgorithm.algorithms.AbstractSortService;
import de.shini.visualisedsortingalgorithm.algorithms.BubbleSortService;
import de.shini.visualisedsortingalgorithm.algorithms.InsertionSortService;
import de.shini.visualisedsortingalgorithm.algorithms.QuickSortNotThreadedService;
import de.shini.visualisedsortingalgorithm.algorithms.QuickSortService;
import de.shini.visualisedsortingalgorithm.algorithms.RandomSelectionSortService;
import de.shini.visualisedsortingalgorithm.algorithms.SelectionSortService;
import de.shini.visualisedsortingalgorithm.algorithms.ShellSortService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainFrame extends Application {

	// Variables
	private double screewidth = 1240.0;
	private double screeheight = 700.0;
	private int N = 1000;
	private long millis = 1l;
	private final List<Integer> list = new ArrayList<>();
	private Canvas.Shema shema = Shema.DEFAULT_DOTS;

	private enum SortAlgorithmName {
		BubbleSort, SelectionSort, InsertionSort, ShellSort, RandomSelectionSort, QuickSort, QuickSortNotThreaded
	}

	// GUI
	private Canvas canvas = new Canvas();
	private BorderPane root = new BorderPane();
	private HBox statusbar = new HBox(3.0);
	private Text statusbarSortalgorithm = new Text();
	private ChoiceBox<SortAlgorithmName> sbSortAlgorithm;
	private Button bStart;
	private Button bShuffle;
	private TextField tN;
	private CheckBox cbDelay;
	private ChoiceBox<Canvas.Shema> sbShema;

	// Sorting Algorithms
	private final Map<SortAlgorithmName, AbstractSortService<Integer>> sortServices = new HashMap<>();
	private AbstractSortService<Integer> sortService = null;

	// Methods
	private void fillList() {
		list.clear();
		for (int i = 0; i < N; i++) {
			list.add(i);
		}
	}

	private void randomizeList() {
		Collections.shuffle(list);
	}

	public void switchSortAlgorithm(SortAlgorithmName sortAlgorithmName) {
		if (sortService != null && sortService.isRunning()) {
			System.out.println("Cant switch because sorting is currently in progress");
			return;
		}
		switch (sortAlgorithmName) {
		case BubbleSort:
			if (!(sortService instanceof BubbleSortService)) {
				System.out.println("Switched to BubbleSort");
				statusbarSortalgorithm.setText("BubbleSort");
				sortService = sortServices.get(SortAlgorithmName.BubbleSort);
			}
			break;
		case SelectionSort:
			if (!(sortService instanceof SelectionSortService)) {
				System.out.println("Switched to SelectionSort");
				statusbarSortalgorithm.setText("SelectionSort");
				sortService = sortServices.get(SortAlgorithmName.SelectionSort);
			}
			break;
		case InsertionSort:
			if (!(sortService instanceof InsertionSortService)) {
				System.out.println("Switched to InsertionSort");
				statusbarSortalgorithm.setText("InsertionSort");
				sortService = sortServices.get(SortAlgorithmName.InsertionSort);
			}
			break;
		case QuickSort:
			if (!(sortService instanceof QuickSortService)) {
				System.out.println("Switched to QuickSort");
				statusbarSortalgorithm.setText("QuickSort");
				sortService = sortServices.get(SortAlgorithmName.QuickSort);
			}
			break;
		case QuickSortNotThreaded:
			if (!(sortService instanceof QuickSortNotThreadedService)) {
				System.out.println("Switched to QuickSortNotThreaded");
				statusbarSortalgorithm.setText("QuickSortNotThreaded");
				sortService = sortServices.get(SortAlgorithmName.QuickSortNotThreaded);
			}
			break;
		case ShellSort:
			if (!(sortService instanceof ShellSortService)) {
				System.out.println("Switched to ShellSort");
				statusbarSortalgorithm.setText("ShellSort");
				sortService = sortServices.get(SortAlgorithmName.ShellSort);
			}
			break;
		case RandomSelectionSort:
			if (!(sortService instanceof RandomSelectionSortService)) {
				System.out.println("Switched to RandomSelectionSort");
				statusbarSortalgorithm.setText("RandomSelectionSort");
				sortService = sortServices.get(SortAlgorithmName.RandomSelectionSort);
			}
			break;
		default:
			if (!(sortService instanceof BubbleSortService)) {
				System.out.println("Switched to BubbleSort");
				statusbarSortalgorithm.setText("BubbleSort");
				sortService = sortServices.get(SortAlgorithmName.BubbleSort);
			}
			break;
		}
	}

	private void disableUIElements(boolean disable) {
		if (disable) {
			sbSortAlgorithm.setDisable(true);
			bStart.setText("Stop!");
			bShuffle.setDisable(true);
			tN.setDisable(true);
		} else {
			sbSortAlgorithm.setDisable(false);
			bStart.setText("Sort!");
			bShuffle.setDisable(false);
			tN.setDisable(false);
		}
	}

	// Application Methods
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		super.init();
		sortServices.put(SortAlgorithmName.BubbleSort,
				new BubbleSortService<Integer>(list, (i1, i2) -> i1 < i2, millis));
		sortServices.put(SortAlgorithmName.SelectionSort,
				new SelectionSortService<Integer>(list, (i1, i2) -> i1 > i2, millis));
		sortServices.put(SortAlgorithmName.InsertionSort,
				new InsertionSortService<Integer>(list, (i1, i2) -> i1 < i2, millis));
		sortServices.put(SortAlgorithmName.RandomSelectionSort,
				new RandomSelectionSortService<Integer>(list, (i1, i2) -> list.indexOf(i1) < list.indexOf(i2) && i1 < i2
						|| list.indexOf(i1) > list.indexOf(i2) && i1 > i2, millis));
		sortServices.put(SortAlgorithmName.ShellSort,
				new ShellSortService<Integer, Integer>(list, (i, v) -> i < v, millis));
		sortServices.put(SortAlgorithmName.QuickSort,
				new QuickSortService<Integer, Integer>(list, (i, v) -> i > v, (i, v) -> i < v, millis));
		sortServices.put(SortAlgorithmName.QuickSortNotThreaded,
				new QuickSortNotThreadedService<Integer, Integer>(list, (i, v) -> i > v, (i, v) -> i < v, millis));
		switchSortAlgorithm(SortAlgorithmName.SelectionSort);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Visualised Sorting Algorithm");

		Text statusbarSortServiceIsRunning = new Text("fresh shuffled");
		statusbar.getChildren().addAll(statusbarSortalgorithm, statusbarSortServiceIsRunning);

		sbSortAlgorithm = new ChoiceBox<>(FXCollections.observableArrayList(SortAlgorithmName.values()));
		sbSortAlgorithm.getSelectionModel().select(SortAlgorithmName.SelectionSort);
		sbSortAlgorithm.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> switchSortAlgorithm(newValue));

		sbShema = new ChoiceBox<>(FXCollections.observableArrayList(Canvas.Shema.values()));
		sbShema.getSelectionModel().select(Canvas.Shema.DEFAULT_DOTS);
		sbShema.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> shema = newValue);

		bStart = new Button("Sort!");
		bStart.setOnAction(e -> {
			if (sortService.isRunning()) {
				sortService.cancel();
			} else {
				sortService.setLines(list);
				sortService.restart();
			}
		});

		bShuffle = new Button("Shuffle!");
		bShuffle.setOnAction(e -> {
			if (!sortService.isRunning()) {
				randomizeList();
				statusbarSortServiceIsRunning.setText("fresh shuffled");
			}
		});

		// Text tN = new Text(String.valueOf(N));
		// VBox vbN = new VBox(tN);
		// Button bNPlus = new Button("^");
		// Button bNMinus = new Button("v");
		// VBox vbNButtons = new VBox(bNPlus, bNMinus);
		// HBox hbN = new HBox(vbN, vbNButtons);

		tN = new TextField(String.valueOf(N));
		tN.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("\\d*")) {
				try {
					int value = Integer.parseInt(newValue);
					if (value >= 10 && value <= 15000) {
						N = value;
						fillList();
						randomizeList();
					} else {
						tN.setText(oldValue);
					}
				} catch (NumberFormatException nfe) {
					tN.setText(oldValue);
				}
			} else {
				tN.setText(oldValue);
			}
		});

		cbDelay = new CheckBox("Delay between swaps");
		cbDelay.setSelected(true);
		cbDelay.setOnAction(e -> {
			millis = millis == 1l ? 0l : 1l;
			sortServices.values().forEach(s -> s.setMillis(millis));
		});

		Pane menu = new VBox(3.0, sbSortAlgorithm, bStart, bShuffle, tN, cbDelay, sbShema);
		menu.widthProperty().addListener((observable, oldValue, newValue) -> {
			double value = newValue.doubleValue();
			sbSortAlgorithm.setPrefWidth(value);
			bStart.setPrefWidth(value);
			bShuffle.setPrefWidth(value);
			tN.setPrefWidth(value);
			cbDelay.setPrefWidth(value);
			sbShema.setPrefWidth(value);
		});

		Timer t = new Timer(10, e -> Platform.runLater(() -> canvas.repaint(list, shema)));
		Pane sp = new Pane(canvas);

		root.setCenter(sp);
		root.setBottom(statusbar);
		root.setRight(menu);

		sortServices.values().forEach(serv -> {
			serv.setOnScheduled(e -> {
				statusbarSortServiceIsRunning.setText("scheduled");
				disableUIElements(false);
			});
			serv.setOnSucceeded(e -> {
				statusbarSortServiceIsRunning.setText("succeeded");
				disableUIElements(false);
			});
			serv.setOnRunning(e -> {
				statusbarSortServiceIsRunning.setText("running");
				disableUIElements(true);
			});
			serv.setOnCancelled(e -> {
				statusbarSortServiceIsRunning.setText("cancelled");
				disableUIElements(false);
			});
		});

		Scene rootScene = new Scene(root, screewidth, screeheight);
		rootScene.setOnKeyPressed(e -> {
			// boolean sortServiceIsRunning = sortService.isRunning();
			switch (e.getCode()) {
			// case SPACE:
			// if (sortServiceIsRunning) {
			// sortService.cancel();
			// } else {
			// sortService.setLines(list);
			// sortService.restart();
			// }
			// break;
			// case F1:
			// if (!sortServiceIsRunning) {
			// randomizeList();
			// statusbarSortServiceIsRunning.setText("fresh shuffled");
			// }
			// break;
			case ESCAPE:
				Platform.exit();
				break;
			case F:
				primaryStage.setFullScreen(!primaryStage.isFullScreen());
				break;
			// case DIGIT1:
			// switchSortAlgorithm(SortAlgorithmName.BubbleSort);
			// break;
			// case DIGIT2:
			// switchSortAlgorithm(SortAlgorithmName.SelectionSort);
			// break;
			// case DIGIT3:
			// switchSortAlgorithm(SortAlgorithmName.InsertionSort);
			// break;
			// case DIGIT4:
			// switchSortAlgorithm(SortAlgorithmName.ShellSort);
			// break;
			// case DIGIT5:
			// switchSortAlgorithm(SortAlgorithmName.RandomSelectionSort);
			// break;
			// case DIGIT6:
			// switchSortAlgorithm(SortAlgorithmName.QuickSort);
			// break;
			default:
				break;
			}
		});
		primaryStage.setScene(rootScene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		primaryStage.setMinWidth(480);
		primaryStage.setMinHeight(360);
		primaryStage.setOnCloseRequest(e -> t.stop());
		canvas.widthProperty().bind(sp.widthProperty());
		canvas.heightProperty().bind(sp.heightProperty());

		fillList();
		randomizeList();

		t.start();
	}
}

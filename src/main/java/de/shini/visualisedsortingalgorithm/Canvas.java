package de.shini.visualisedsortingalgorithm;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Canvas extends javafx.scene.canvas.Canvas {

	public enum Shema {
		DEFAULT_DOTS, DEFAULT_LINES, DEFAULT_DOTS_WITH_RED, DEFAULT_LINES_WITH_RED, HSV_DOTS, HSV_LINES
	}

	public void repaint(List<Integer> list, Shema shema) {
		GraphicsContext gc = getGraphicsContext2D();
		double width = getWidth();
		double height = getHeight();
		double dw = width / list.size();
		double dh = height / list.size();
		int N = list.size();

		gc.clearRect(0.0, 0.0, width, height);
		if (list != null && !list.isEmpty()) {
			switch (shema) {
			case HSV_DOTS:
				for (int x = 0; x < N; x++) {
					int o = list.get(x);
					gc.setFill(Color.hsb((double) o / N * 360.0, 0.8, 0.9));
					gc.fillRect(dw * x, dh * o, 2.0, 2.0);
				}
				break;
			case HSV_LINES:
				for (int x = 0; x < N; x++) {
					int o = list.get(x);
					gc.setFill(Color.hsb((double) o / N * 360.0, 0.8, 0.9));
					gc.fillRect(dw * x, dh * o, 2.0, height);
				}
				break;
			case DEFAULT_LINES:
				gc.setFill(Color.BLACK);
				for (int x = 0; x < N; x++) {
					int o = list.get(x);
					gc.fillRect(dw * x, dh * o, 2.0, height);
				}
				break;
			case DEFAULT_LINES_WITH_RED:
				for (int x = 0; x < N; x++) {
					int o = list.get(x);
					gc.setFill(-(o - N + 1) == x ? Color.RED : Color.BLACK);
					gc.fillRect(dw * x, dh * o, 2.0, height);
				}
				break;
			case DEFAULT_DOTS_WITH_RED:
				for (int x = 0; x < N; x++) {
					int o = list.get(x);
					gc.setFill(-(o - N + 1) == x ? Color.RED : Color.BLACK);
					gc.fillRect(dw * x, dh * o, 2.0, 2.0);
				}
				break;
			case DEFAULT_DOTS:
			default:
				gc.setFill(Color.BLACK);
				for (int x = 0; x < N; x++) {
					int o = list.get(x);
					gc.fillRect(dw * x, dh * o, 2.0, 2.0);
				}
				break;
			}
		}
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double prefWidth(double height) {
		return getWidth();
	}

	@Override
	public double prefHeight(double width) {
		return getHeight();
	}
}

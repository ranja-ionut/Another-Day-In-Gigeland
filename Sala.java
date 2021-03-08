import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class Sala {
	static class Task {
		public static final String INPUT_FILE = "sala.in";
		public static final String OUTPUT_FILE = "sala.out";

		int N, M;
		long castig = 0;
		Point[] inputs;
		
		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
								
				N = sc.nextInt();
				M = sc.nextInt();
				
				inputs = new Point[N];
				
				for (int i = 0; i < N; i++) {
					inputs[i] = new Point(sc.nextInt(), sc.nextInt());
				}
				
				sc.close();
				
				Arrays.sort(inputs, new Comparator<Point>() {
					public int compare(Point a, Point b) {
						if (a.getX() < b.getX()) {
							return 1;
						}
						
						if (a.getX() > b.getX()) {
							return -1;
						}
						
						return 0;
					}
				});
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput() {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.println(castig);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		/*
			Am incercat un greedy approach asuprea acestei probleme.
		*/

		private void compute_solution() {
			long curr = 0, prev = -1, repetitii = 0, min = 0;
			Point p;
			Vector<Point> best = new Vector<>();
			for (int i = 0; i < M; i++) {
				best.add(inputs[i]);
			}
			while (curr >= prev && best.size() > 0) {
				prev = curr;
				repetitii = 0;
				p = null;
				min = (long)inputs[0].getX();
				for (int i = 0; i < best.size(); i++) {
					repetitii += (long)inputs[i].getY();
					if (min >= (long)inputs[i].getX()) {
						min = (long)inputs[i].getX();
						p = inputs[i];
					}
					curr = min * repetitii;
					if (curr > castig) {
						castig = curr;
					}
				}
				if (p != null) {
					best.remove(p);
				}
			}

		}

		public void solve() {
			readInput();
			compute_solution();
			writeOutput();
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}

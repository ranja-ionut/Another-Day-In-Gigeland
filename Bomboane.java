import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class Bomboane {
	static class Task {
		public static final String INPUT_FILE = "bomboane.in";
		public static final String OUTPUT_FILE = "bomboane.out";

		int N, M, result = 0;
		int[][] already_found = new int[50][2001];
		static final int MOD = 1000000007;
		Vector<Point> intervale = new Vector<>();
		
		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));

				N = sc.nextInt();
				M = sc.nextInt();
				for (int i = 0; i < N; i++) {
					intervale.add(new Point(sc.nextInt(), sc.nextInt()));
				}
				
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/*
			Functia genereaza toate posibilitatile plecand de la o suma
			si un pas dat, unde pasul reprezinta intervalul curent.
			
			Am folosit varianta top-down a unei abordari naive cu 
			backtracking la care am adaugat memoizare pentru apelurile
			recursive care au fost deja calculate anterior pentru a reduce
			timpul necesar calculelor.
		*/
		
		private int findPossibilitiesFrom(int interval, int suma) {	
			if (suma < 0) {
				return 0;
			}
			
			if (interval == N) {
				if (suma == 0) {
					return 1;
				} else {
					return 0;
				}
			}
			
			/*
				Partea de memoizare -> daca deja am calculat pentru suma curenta
				si pasul curent numarul de posibilitati, atunci nu mai e nevoie
				sa o fac iar.
			*/
			
			if (already_found[interval][suma] >= 0) {
				return already_found[interval][suma];
			}
			
			/*
				Partea de generare de de posibilitati -> abordarea naiva ajutata
				de memoizare.
				
				Complexitatea este O(N * M log M). (In medie va realiza
				N * M log M apeluri)
			*/
			
			Point p = intervale.get(interval);
			int possibilities = 0;
			for (int i = (int)p.getX(); i <= (int)p.getY(); i++) {
				possibilities = (int)((possibilities % MOD 
					+ findPossibilitiesFrom(interval + 1, suma - i) % MOD) % MOD);
			}
			
			/*
				Partea de memoizare -> daca am trecut de partea de memoizare de mai sus,
				inseamna ca nu calculasem numarul de posibilitati pentru suma si pasul
				curent.
			*/
			
			already_found[interval][suma] = possibilities;
			return possibilities;
		}
		
		private void writeOutput() {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.println(result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void compute_solution() {
			for (int i = 0; i < 50; i++) {
				for (int j = 0; j <= 2000; j++) {
					already_found[i][j] = -1;
				}
			}
			
			result = findPossibilitiesFrom(0, M);
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

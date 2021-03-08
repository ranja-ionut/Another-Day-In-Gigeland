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

public class Gard {
	static class Task {
		public static final String INPUT_FILE = "gard.in";
		public static final String OUTPUT_FILE = "gard.out";

		Vector<Point> garduri = new Vector<>();
		int N, redundant = 0;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
								
				N = sc.nextInt();
				for (int i = 0; i < N; i++) {
					garduri.add(new Point(sc.nextInt(), sc.nextInt()));
				}
				
				sc.close();
				
				/*
					Fiind o abordare Greedy, am sortat crescator gardurile dupa
					capatul de inceput, iar pentru garduri cu acelasi inceput am 
					sortat descrescator dupa capatul de sfarsit.
					
					Din ce am gasit din documentatia Java Oracle, metoda sort
					din Vector are o complexitate de O(N log N).
				*/
				
				garduri.sort(new Comparator<Point>() {
					public int compare(Point p1, Point p2) {
						int x1 = (int) p1.getX(), x2 = (int) p2.getX();
						int y1 = (int) p1.getY(), y2 = (int) p2.getY();
						if (x1 > x2) {
							return 1;
						}
						if (x1 < x2) {
							return -1;
						}
						if (y1 > y2) {
							return -1;
						}
						if (y1 < y2) {
							return 1;
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
				pw.println(redundant);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void compute_solution() {
			int choice;
			Point comp = garduri.get(0), curr;
			
			/*
				Pentru a afla gardurile redundante am sortat gardurile crescator dupa
				capatul de inceput pentru a ma asigura ca daca gardul curent are 
				pozitiile X Y, atunci urmatorul gard va avea automat dimensiuni >= X,
				iar Y nu conteaza pentru aceasta parte.
				
				Pentru garduri cu acelasi capat de inceput, am sortat descrescator
				dupa capatul de sfarsit. Pentru a intelege de ce am sortat in acest
				mod este necesara explicarea codului de mai jos.
				
				Plec cu primul gard pe care il consider gard de comparatie pentru
				celelalte garduri. Daca gardul curent are dimensiuni incluse in
				gardul comp, atunci acel gard este redundant. 
				Daca gardul curent se afla strict la dreapta gardului comp, adica
				are X > comp.X si Y > comp.Y atunci gardul curent va deveni comp.
				
				Atribuirea gardului curent lui comp este facuta eficient prin faptul
				ca daca am gasit un gard mai larg decat cel folosit atunci sunt sigur
				ca el este cel mai bun si ce urmeaza ori este strict in dreapta lui,
				ori este inclus in el (din sortarea crescatoare dupa capatul de inceput
				ma asigur ca iau gardurile in ordine, si din sortarea descrescatoare
				dupa capatul de final ma asigur ca, in momentul ce am schimbat gardul
				folosit in comparatii, ceea ce urmeaza are capatul de final mai mic - 
				in cazul in care capatele de inceput sunt egale).
				
				Complexitatea este O(N), fiind facuta o simpla parcurgere a gardurilor.
				
				In total vom avea complexitate O(N log N).
			*/
			
			for (int i = 1; i <= garduri.size() - 1; i++) {
				curr = garduri.get(i);
				if (curr.getX() > comp.getX() && curr.getY() > comp.getY()) {
					comp = curr;
				} else {
					if (curr.getY() <= comp.getY() && curr.getX() >= comp.getX()) {
						redundant++;
					}
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

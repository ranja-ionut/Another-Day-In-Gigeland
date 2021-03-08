import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Bani {
	static class Task {
		public static final String INPUT_FILE = "bani.in";
		public static final String OUTPUT_FILE = "bani.out";

		int N, type;
		int moduri_aranjare = 5;
		static final int MOD = 1000000007;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
								
				type = sc.nextInt();
				N = sc.nextInt();
				
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput() {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.println(moduri_aranjare);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void type_1() {
			int base = 2;

			/*
				Pentru a rezolva tipul 1 de input, trebuie sa calculez 5 * 2 ^ (N - 1).
				
				Explicatia pentru aceasta formula vine din faptul ca dupa fiecare 
				bancnota urmeaza automat alte 2 bancnote, deci pornind de la cele 5, 
				vom avea 2 * 5, apoi 2 * 2 * 5, etc.
				
				La inceput aveam probleme cu TLE cand doar inmulteam cu 2 intr-un for,
				si mi-am adus aminte ca in labul 1 am facut un FAST POW, si m-am folosit
				de el pentru a castiga timp.
				
				Practic pentru exponent impar face rezultat * baza si apoi exponentul
				va deveni par, deci putem face skip la un pas inmultind baza cu ea
				insasi, apoi vom face iar rezultat * baza (datorat shiftarii
				pe biti care garanteaza ca vom avea pe LSB valoarea 1 la un moment dat).
				
				In final vom obtine rezultatul mult mai rapid in timp de O(log N).
																		(de la shiftare)
			*/
			
			N--;
			while (N > 0) {
				if ((N & 1) != 0) {
					moduri_aranjare = (int)((1L * moduri_aranjare * base) % MOD);
				}
				base = (int)((1L * base * base) % MOD);
				N >>= 1;
			}
		}
		
		/*
			Pentru celalalt tip de aranjare a bancnotelor a fost nevoie sa gasesc o
			alta formula pentru a putea calcula rezultatul.
			
			In acest caz, in locul unei formule, trebuie folosita recurenta bancnotelor:
				
				*	cea de 10 lei este generata de cele de 500, 100 si 50 de lei
				* 	cea de 50 de lei este generata de cele de 200 si 10 lei
				*	cea de 100 de lei este generata de cele de 200, 100 si 10 lei
				*	cea de 200 de lei este generata de cele de 500 si 50 de lei
				*	cea de 500 de lei este generata de cea de 200 de lei
				
			Aplicand aceasta recurenta de N - 1 ori, vom obtine rezultat cautat. Deci are
			complexitatea O(N);
		*/
		private void type_2() {
			long old_b10 = 1, old_b50 = 1, old_b100 = 1, old_b200 = 1, old_b500 = 1;
			long new_b10 = 1, new_b50 = 1, new_b100 = 1, new_b200 = 1, new_b500 = 1;
			
			for (int i = 0; i < N - 1; i++) {
				new_b10 = (old_b500 % MOD + old_b100 % MOD + old_b50 % MOD) % MOD;
				new_b50 = (old_b200 % MOD + old_b10 % MOD) % MOD;
				new_b100 =  (old_b200 % MOD + old_b100 % MOD + old_b10 % MOD) % MOD; 
				new_b200 = (old_b500 % MOD + old_b50 % MOD) % MOD; 
				new_b500 =  old_b200 % MOD;
				
				old_b10 = new_b10;
				old_b50 = new_b50;
				old_b100 = new_b100;
				old_b200 = new_b200;
				old_b500 = new_b500;
			}
			
			moduri_aranjare = (int)((new_b10 % MOD + new_b50 % MOD + new_b100 % MOD 
				+ new_b200 % MOD + new_b500 % MOD) % MOD);
		}
		
		private void compute_solution() {
			if (type == 1) {
				type_1();
			}
			
			if (type == 2) {
				type_2();
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

import java.util.Scanner;

public class CalculoPiParaleloMonteCarlo {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int bucket = n/m;
		int operations;

		OperatorsMonteCarlo Hilos[] = new OperatorsMonteCarlo[m];

		for(int i=0; i<m; i++){
			operations = Math.min(bucket,n - i*bucket);
			Hilos[i] = new OperatorsMonteCarlo(operations);
		}

		long start = System.currentTimeMillis();
		for(int i=0; i<m; i++){
			Hilos[i].start();
		}
		for(int i=0; i<m; i++){
			try {
				Hilos[i].join();
			} catch(InterruptedException e) {}
		}
		long time_elapsed = System.currentTimeMillis() - start;

		int points_in = 0;
		for(int i=0; i<m; i++){
			points_in += Hilos[i].getAns();
		}

		double Pi = 4.0*points_in / n;
		System.out.format("Tiempo: %d ms\n",time_elapsed);
		System.out.format("Pi = %.16f\n",Pi);
	}
}

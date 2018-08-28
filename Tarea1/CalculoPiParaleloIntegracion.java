import java.util.Scanner;

public class CalculoPiParaleloIntegracion {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int m = sc.nextInt();

		OperatorsInt Hilos[] = new OperatorsInt[m];
		int bucket = n/m;
		long start = System.currentTimeMillis();
		for(int i=0; i<m; i++){
			Hilos[i] = new OperatorsInt(n,i*bucket,Math.min(n,(i+1)*bucket));
			Hilos[i].start();
		}
		for(int i=0; i<m; i++){
			try {
				Hilos[i].join();
			} catch(Exception e) {}
		}
		long time_elapsed = System.currentTimeMillis() - start;
		double Pi = 0.0;
		for(int i=0; i<m; i++){
			Pi += Hilos[i].getAns();
		}
		System.out.format("Tiempo: %d ms\n",time_elapsed);
		System.out.format("Pi: %.30f\n",Pi);
	}
	
	
}

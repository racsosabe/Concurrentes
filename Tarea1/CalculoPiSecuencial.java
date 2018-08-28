import java.util.Scanner;

public class CalculoPiSecuencial {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		
		int points_in = 0;
		long start = System.currentTimeMillis();
		for(int i=0; i<n; i++){
			double x = Math.random()*2 - 1.0;
			double y = Math.random()*2 - 1.0;
			if(x*x + y*y <= 1.0) points_in++;
		}
		double Pi = 4.0*points_in/n;
		long time_elapsed = System.currentTimeMillis() - start;
		System.out.format("Tiempo: %d ms\n",time_elapsed);
		System.out.format("Pi = %.16f\n",Pi);
	}
}

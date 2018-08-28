public class OperatorsMonteCarlo extends Thread {
	private int n;
	private int inside;

	OperatorsMonteCarlo(int n){
		this.n = n;
		inside = 0;
	}

	public int getAns(){
		return inside;
	}

	public void run(){
		double x,y;
		for(int i=0; i<n; i++){
			x = Math.random()*2 - 1.0;
			y = Math.random()*2 - 1.0;
			if(x*x + y*y <= 1.0) inside++;
		}
	}
}

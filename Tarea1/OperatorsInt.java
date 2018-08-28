public class OperatorsInt extends Thread {
	private double ans;
	private int len;
	private int s;
	private int e;
	
	public OperatorsInt(int len, int s, int e) {
		this.len = len;
		this.s = s;
		this.e = e;
	}
	
	public double getAns() {
		return ans;
	}
	
	public double f(double x) {
		return 4.0 / (1+x*x);
	}

	public void run() {
		ans = 0.0;
		double delta = 1.0/len;
		for(int i=s; i<e; i++){
			ans += f(i*delta);
		}
		ans *= delta;
	}
}

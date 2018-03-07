import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
	private int N;
	private int trialNum;
	private double result[];
	
	public PercolationStats(int n,int trials){
		if(n<=0 || trials<=0)
			throw new IllegalArgumentException("n and trials must be greater than 0.");
		
		N=n;
		trialNum=trials;
		result=new double[trialNum];
		
		for(int i=0;i<trialNum;i++){
			result[i]=singleTrial();
		}
	}
	
	public double mean(){
		return StdStats.mean(result);
	}
	
	public double stddev(){
		return StdStats.stddev(result);
	}
	
	public double confidenceLo(){
		return mean()-1.96*stddev()/Math.sqrt(trialNum);
	}
	
	public double confidenceHi(){
		return mean()+1.96*stddev()/Math.sqrt(trialNum);
	}
	
	private double singleTrial(){
		int openNum=0;
		Percolation p=new Percolation(N);
		while(!p.percolates()){
			int row=StdRandom.uniform(N)+1;
			int col=StdRandom.uniform(N)+1;
			if(!p.isOpen(row, col)){
				p.open(row, col);
				openNum++;
			}
		}
		
		return (double)openNum/(N*N);
	}
	
	public static void main(String[] args){
		int n=Integer.parseInt(args[0]);
		int trials=Integer.parseInt(args[1]);
		
		PercolationStats ps=new PercolationStats(n,trials);
		StdOut.println("mean = "+ps.mean());
		StdOut.println("stddev = "+ps.stddev());
		StdOut.println("95% confidence interval = ["
					+ps.confidenceLo()
					+","+ps.confidenceHi()+"]");
	}
}

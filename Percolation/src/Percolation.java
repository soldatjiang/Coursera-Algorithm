import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF wUF;
	private boolean site[];
	private int N;
	private int numOpen;//open site number
	
	public Percolation(int n){
		if(n<=0)
			throw new IllegalArgumentException("n must be greater or equal to 0");
		wUF=new WeightedQuickUnionUF(n*n+2);
		site=new boolean[n*n];
		N=n;
		numOpen=0;
	}
	
	private int xyTo1D(int row,int col){
		return N*(row-1)+col-1;
	}
	
	public boolean isOpen(int row,int col){
		return site[xyTo1D(row,col)]==true;
	}
	
	private boolean out_of_range(int row,int col){
		if((col>0 && col<=N) && (row>0 && row<=N))
			return false;
		return true;
	}
	
	public void open(int row,int col){
		if(out_of_range(row,col))
			throw new IllegalArgumentException("Index out of range...");
		if(isOpen(row,col))
			return;
		
		if(row==1){ //第一行元素与顶点连通
			wUF.union(0,col);
			site[xyTo1D(row,col)]=true;
		} else if(row==N){ //最后一行元素与底点连通
			wUF.union(N*N+1,xyTo1D(row,col)+1);
			site[xyTo1D(row,col)]=true;
		} 
		
		site[xyTo1D(row,col)]=true;
		
		if(!out_of_range(row-1,col) && isOpen(row-1,col)){//若上方点开放，与上方点连通
			wUF.union(xyTo1D(row-1,col)+1, xyTo1D(row,col)+1);
		}
		
		if(!out_of_range(row+1,col) && isOpen(row+1,col)){//若下方点开放，与下方点连通
			wUF.union(xyTo1D(row+1,col)+1, xyTo1D(row,col)+1);
		}
		
		if(!out_of_range(row,col-1) && isOpen(row,col-1)){//若左方点开放，与左方点连通
			wUF.union(xyTo1D(row,col-1)+1, xyTo1D(row,col)+1);
		}
		
		if(!out_of_range(row,col+1) && isOpen(row,col+1)){//若右方点开放，与右方点连通
			wUF.union(xyTo1D(row,col+1)+1, xyTo1D(row,col)+1);
		}
		
		numOpen++;
	}
	
	public boolean isFull(int row,int col){
		return wUF.connected(xyTo1D(row,col)+1,0);
	}
	
	public int numberOfOpenSites(){
		return numOpen;
	}
	
	public boolean percolates(){
		return wUF.connected(0, N*N+1);
	}
	
	/*public static void main(String[] args) {
		try{
			FileInputStream in=new FileInputStream("percolation/input10-no.txt");
			System.setIn(in);
			System.out.println("Read file successfully!");
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            percolation.open(i, j);
        }
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());
	}*/
}

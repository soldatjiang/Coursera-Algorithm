import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] pts;
    private Point[] ptsCopy;
    private int num;
    private LineSegment[] lineSeg;
    private int numLineSeg=0;
    private double lastSlope=Double.NEGATIVE_INFINITY;

    public FastCollinearPoints(Point[] points){
        if(points==null)
            throw new IllegalArgumentException("Null Argument in constructor");

        for(int i=0;i<points.length;i++){
            if(points[i]==null)
                throw new IllegalArgumentException("Null point in array");

            for(int j=i+1;j<points.length;j++){
                if(points[i].compareTo(points[j])==0)
                    throw new IllegalArgumentException("Contains a repeated point");
            }
        }

        pts=Arrays.copyOf(points,points.length);
        ptsCopy=Arrays.copyOf(points,points.length);
        num=points.length;
        lineSeg=new LineSegment[4*points.length];

        Arrays.sort(pts);
        //Point origin;
        //Point[] temp=new Point[4];
        for(int i=0;i<pts.length;i++){
            //Point[] other=new Point[pts.length-1];
            //StdOut.println("pts:"+pts[i].toString());
            //StdOut.println("ptsCopy:"+ptsCopy[0].toString()
            Arrays.sort(ptsCopy);
            Arrays.sort(ptsCopy,pts[i].slopeOrder());

            if(pts[i].slopeTo(ptsCopy[1])==lastSlope)
                continue;
            findSegment(ptsCopy,pts[i]);
        }
    }

    /*Return an int array ret
    ret[0]:the length of segement
    ret[1]:the start index
    ret[2]:end index
     */
    private void findSegment(Point[] other,Point origin){
        int cnt;
        int start,end=-1;

        for(int i=1;i<other.length-2;i++){
            cnt=1;
            start=i;
            end=i;
            if(lastSlope==origin.slopeTo(other[start]))
                continue;

            lastSlope=origin.slopeTo(other[start]);
            if(origin.compareTo(other[start])==1)
                continue;
            for(int j=i+1;j<other.length;j++){
                if(origin.slopeTo(other[j])!=origin.slopeTo(other[j-1])){
                    if(cnt>=3){
                        lineSeg[numLineSeg++]=new LineSegment(origin,other[end]);
                    }
                    cnt=1;
                    break;
                }
                cnt++;
                end++;
            }

            if(cnt>=3){
                lineSeg[numLineSeg++]=new LineSegment(origin,other[end]);
            }
        }
    }

    public int numberOfSegments(){
        return numLineSeg;
    }

    public LineSegment[] segments(){
        LineSegment[] ret=new LineSegment[numLineSeg];

        for(int i=0;i<numLineSeg;i++)
            ret[i]=lineSeg[i];

        return ret;
    }

    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            //StdOut.println(points[i].toString());
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment lineseg : collinear.segments()) {
            StdOut.println(lineseg);
            lineseg.draw();
        }
        StdDraw.show();
    }
}

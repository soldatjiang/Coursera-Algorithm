import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    private Point[] pts=null;
    private LineSegment[] lineseg;
    private int numLineSeg=0;

    public BruteCollinearPoints(Point[] points){
        int p,q,r,s;
        int num;

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

        pts=points;
        num=pts.length;
        lineseg=new LineSegment[4*pts.length];

        Arrays.sort(pts);
        for(p=0;p<num;p++){
            for(q=p+1;q<num;q++){
                double pq=pts[p].slopeTo(pts[q]);
                for(r=q+1;r<num;r++){
                    double qr=pts[q].slopeTo(pts[r]);
                    if(pq==qr){
                        for(s=r+1;s<num;s++){
                            double rs=pts[r].slopeTo(pts[s]);
                            if(qr==rs)
                                lineseg[numLineSeg++]=new LineSegment(pts[p],pts[s]);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments(){
        return numLineSeg;
    }

    public LineSegment[] segments() {
        LineSegment[] ret=new LineSegment[numLineSeg];
        for(int i=0;i<numLineSeg;i++)
            ret[i]=lineseg[i];
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment lineseg : collinear.segments()) {
            StdOut.println(lineseg);
            lineseg.draw();
        }
        StdDraw.show();
    }
}

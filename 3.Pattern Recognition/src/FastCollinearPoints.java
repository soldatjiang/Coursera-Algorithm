import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] pts;
    private int num;
    private LineSegment[] lineSeg;
    private int numLineSeg=0;

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
        num=points.length;
        lineSeg=new LineSegment[4*points.length];

        Point origin;
        Point[] temp=new Point[4];
        for(int i=0;i<pts.length;i++){
            origin=pts[i];
            temp[0]=origin;
            Arrays.sort(pts,pts[i].slopeOrder());
            for(int j=1;j<pts.length-2;j++){
                if(Double.compare(pts[j].slopeTo(origin),pts[j+1].slopeTo(origin))==0 &&
                        Double.compare(pts[j+1].slopeTo(origin),pts[j+2].slopeTo(origin))==0){
                    for(int k=1;k<=3;k++)
                        temp[k]=pts[k];

                    Arrays.sort(temp);
                    lineSeg[numLineSeg++]=new LineSegment(temp[0],temp[3]);
                }
            }
        }
    }

    /*Return an int array ret
    ret[0]:the length of segement
    ret[1]:the start index
    ret[2]:end index
     */
    private int[] findSegment(Point[] other,Point origin){
        int cnt;
        int start,end;
        for(int i=1;i<other.length-2;i++){
            cnt=1;
            for(int j=i+1;j<other.length;j++){

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment lineseg : collinear.segments()) {
            StdOut.println(lineseg);
            lineseg.draw();
        }
        StdDraw.show();
    }
}

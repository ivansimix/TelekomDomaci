

public class Point {
    public double x,y;
    public Point(double x, double y) {

    if (x<-1 || x>1) {
        this.x=0;
    }
    if (y<-1 || y>1) {
        this.y=0;

    }
    this.x=x;
    this.y=y;

    }
    public static double Distance(Point t1, Point t2) {
        double ret= Math.sqrt((t1.x-t2.x)*(t1.x-t2.x) +  (t1.y-t2.y)*(t1.y-t2.y));
        return ret;
    }


}

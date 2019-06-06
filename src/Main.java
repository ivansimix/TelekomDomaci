
public class Main {
    public static final int N = 100000; //number of bits HAS TO BE AN EVEN NUMBER
    public static int[] bits;
    public static int[] bitsAltered;
    public static Point[] points;
    public static Point[] pointsAltered;

    public static void generateBits(double prob) {
        bits = new int[N];
        if (prob < 0 || prob > 1) {
            System.out.println("Probability has to be between 0 and 1!");
            System.exit(1);
        }
        for (int i = 0; i < N; i++) {
            double randomNumber = Math.random();
            int bit;
            if (randomNumber < prob) {
                bit = 0;
            } else {
                bit = 1;
            }
            bits[i] = bit;
        }
    }

    public static void makePoints() {
        points = new Point[N / 2];
        for (int i = 0; i < N; i = i + 2) {
            int x = bits[i];
            int y = bits[i+1];
            if (x == 0) x=-1;
            if (y == 0) y=-1;
            points[i / 2] = new Point(x, y);
        }
    }


    public static void addNoise(double sigma) {
        pointsAltered = new Point[N/2];
        for (int i=0; i<N/2; i++) {
            pointsAltered[i] = new Point(points[i].x, points[i].y);
            java.util.Random r = new java.util.Random();
            double noise = r.nextGaussian() * Math.sqrt(sigma);
            pointsAltered[i].x+=noise;
            noise = r.nextGaussian() * Math.sqrt(sigma);
            pointsAltered[i].y +=noise;
        }
    }

    public static void generateAlteredBits() {
        bitsAltered = new int[N];
        for (int i=0; i<N/2; i++) {
            Point corrected = calculatePoint(pointsAltered[i]);
            bitsAltered[2*i] = (int)corrected.x;
            bitsAltered[2*i+1] = (int)corrected.y;
            if (bitsAltered[2*i] == -1) bitsAltered[2*i]=0;
            if (bitsAltered[2*i+1] == -1) bitsAltered[2*i+1]=0;
        }

    }

    public static Point calculatePoint(Point p) {
        Point p1 = new Point(-1,-1);
        Point p2 = new Point(-1,1);
        Point p3 = new Point(1,-1);
        Point p4 = new Point(1,1);

        double minDist = Point.Distance(p, p1);
        Point minPoint = p1;
        if (minDist > Point.Distance(p,p2)) {
            minDist = Point.Distance(p,p2);
            minPoint=p2;
        }
        if (minDist > Point.Distance(p,p3)) {
            minDist = Point.Distance(p,p3);
            minPoint=p3;
        }
        if (minDist > Point.Distance(p,p4)) {
            minPoint=p4;
        }

        return minPoint;


    }

    public static double calculateError() {
        int zeros1=0;
        int zeros2=0;
        for (int i=0; i<N; i++) {
            if (bits[i] == 0) zeros1++;
            if (bitsAltered[i] == 0) zeros2++;
        }
        double ret = (double)(Math.abs(zeros1-zeros2))/(double)zeros1;
        return ret;
    }

    public static void main(String[] args) {
        int M=100;
        double ret=0;
        for (int i=0; i<100; i++) {
            //args[0] is the probability of 0
            generateBits(Double.parseDouble(args[0]));
            makePoints();
            //args[1] is sigma squared
            addNoise(Double.parseDouble(args[0]));
            generateAlteredBits();
            double retu = calculateError();
            ret+=retu;
        }
        ret/=M;
        System.out.println();
        System.out.println(ret);

    }
}

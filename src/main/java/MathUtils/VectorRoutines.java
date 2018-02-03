package MathUtils;

import java.awt.Point;

public class VectorRoutines {
    public static Point vectorMultiplicationByScalar(int scalar, Point vector){

        vector.x *= scalar;
        vector.y *= scalar;

        return vector;
    }

    public static Point getVector(Point A, Point B){

        return new Point(B.x - A.x, B.y - A.y);
    }

    public static Point turnToOpositeDirection(Point A){

        return vectorMultiplicationByScalar(-1, A);
    }
}

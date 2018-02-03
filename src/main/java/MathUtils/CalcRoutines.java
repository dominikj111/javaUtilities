package MathUtils;

import java.util.Random;

/**
 * Created by DEli on 31.3.2016.
 */
public class CalcRoutines {

    private static final Random random = new Random();

    public static boolean isEven(int number){

        return number % 2 == 0;
    }

    public static boolean isOdd(int number){

        return !isEven(number);
    }

    public static int getRandomInt(int min_include, int max_exclude){

        if(min_include >= max_exclude) {throw new IllegalArgumentException("Arguments can't be same and min value should be less then max value.");}
        return random.nextInt(max_exclude - min_include) + min_include;
    }

    public static int getRandomIntOdd(int min_include, int max_exclude){

        return getRandomInt((min_include + 1) / 2, (max_exclude + 1) / 2) * 2;
    }

    public static int signum(int number){
        return (number > 0) ? +1 : (number < 0) ? -1 : 0;
    }
}

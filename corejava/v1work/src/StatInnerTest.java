/**
 * Demo static (or nested) inner class
 * Use static inner classes when want to use an inner class simply to hide one
 * class inside another; and you don’t need the inner class to have a reference
 * to the outer class object. You can suppress the generation of that reference
 * by declaring the inner class static.
 */
public class StatInnerTest {
    public static void main(String[] args)  {
        var values = new double[20];
        for (int i = 0; i < values.length; i++)
            values[i] = 100 * Math.random();

        ArrayAlgo.MinMaxPair p = ArrayAlgo.minmax(values);
        System.out.println("min = " + p.getMin() + " max = " + p.getMax());
    }

    /*
     * Solve a potential name clash with another programmer by making MinMaxPair
     * a public inner class inside ArrayAlg. Then the class will be known to
     * the public as ArrayAlgo.MinMaxPair:
     */
    class ArrayAlgo   {
        /*
         * However, unlike the inner classes used in previous examples, we do
         * not want to have a reference to any other object inside a MinMaxPair
         * object. That reference can be suppressed by declaring the inner class
         * static:
         */
        public static class MinMaxPair  {
            private double min;
            private double max;

            /**
             * Constructs a pair from two floating-point numbers
             * @param f the first number
             * @param s the second number
             */
            public MinMaxPair(double mn, double mx)
            {
                min = mn;
                max = mx;
            }

            /**
             * Returns the first number of the pair
             * @return the first number
             */
            public double getMin()
            {
                return min;
            }

            /**
             * Returns the second number of the pair
             * @return the second number
             */
            public double getMax()
            {
                return max;
            }
        }

        /**
         * Computes both the minimum and the maximum of an array
         * @param values an array of floating-point numbers
         * @return a pair whose first element is the minimum and whose second
         * element is the maximum
         */
        public static MinMaxPair minmax(double[] values)
        {
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            for (double v : values)
            {
                if (min > v) min = v;
                if (max < v) max = v;
            }
            return new MinMaxPair(min, max);
        }
    }
}

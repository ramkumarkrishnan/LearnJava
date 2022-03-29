public class VariableParamMethod {
    public static void main(String[] args) {
        System.out.println("Method call with variable parameters");
        System.out.println("Max value of 1, 3, 5, 7, 9 is " + myMax(1,3,5,7,9));
    }

    public static int myMax(int... values) {
        int maxo = Integer.MIN_VALUE;
        for (int v : values)
            if (v > maxo) maxo = v;
        return maxo;
    }
}

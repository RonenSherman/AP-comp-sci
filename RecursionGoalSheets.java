public class RecursionGoalSheets {

    public static void main(String[] args) {
        System.out.println(WriteSquares(8));

    }

    public static String StarString(int n) {
        if (n == 0) {
            return "*";
        } else {
            String output = StarString(n - 1);
            return output + output;
        }
    }

    public static String WriteNums(int n) {
        if (n == 0) {
            return "1";
        } else {
            String output = (WriteNums(n - 1));

            return output + "," + (n + 1);

        }
    }

    public static String writeSequence(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be at least 1");
        }
        if (n == 1) {
            return "1";
        } else if (n == 2) {
            return "1 1";
        } else {
            int num = (n + 1) / 2; // Determine the outermost number
            return num + " " + writeSequence(n - 2) + " " + num;
        }
    }

    public static double sumTo(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        else if (n == 0) {
            return 0.0;
        } else {
            return 1 / (double) n + sumTo((n - 1));
        }

    }

    public static int Fibonacci(int n) {
        if (n <= 1) return n;
        int prev1 = 0, prev2 = 1;
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        return prev2;
    }


    public static String WriteSquares(int n)               //
    {
        if (n == 0) {
            return "";
        } else {
            String output = (WriteSquares(n - 1));
            return output + "," + (n*n);
        }
    }
}


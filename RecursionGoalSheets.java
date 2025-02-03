public class RecursionGoalSheets {

    public static void main(String[] args) {
        System.out.println(writeSequence(10));

    }

    public static String StarString(int n)
    {
        if(n == 0)
        {
            return "*";
        }else
        {
            String output = StarString(n-1) ;
            return output + output;
        }
    }

    public static String WritenNums(int n)
    {
        if(n == 0)
        {
            return "1";
        }else
        {
            String output = (WritenNums(n-1));

            return  output + "," + (n+1);

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

}

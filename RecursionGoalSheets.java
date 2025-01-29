public class RecursionGoalSheets {

    public static void main(String[] args) {
        System.out.println(WritenNums(4));

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
                String output = WritenNums(n-1);
                return output + "," +  (output+1);

            }
        }

}

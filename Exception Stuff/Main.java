/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/
public class Main
{
	public static void main(String[] args) {
	    test1();
	}
	public static void test1()
	{
	   try
	   {
	        int x = 4;
	        int y = 0;
	        int z = x/y;
	        System.out.println(z);
	   }catch(ArithmeticException e)
	   {
	       System.out.println("Not good");
	       e.printStackTrace();
	   }
	   finally
	   {
	        System.out.println("and one more thing...");
	   }
	}
	
}

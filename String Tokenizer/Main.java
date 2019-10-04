/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/
import java.util.StringTokenizer;
public class Main
{
	public static void main(String[] args) {
        m2();
	}
	public static void m1()
	{
	    String str = "Use the force";
	    StringTokenizer tokenizer = new StringTokenizer(str);
	    while(tokenizer.hasMoreTokens())
	    {
            String currentToken = tokenizer.nextToken();
            System.out.println(currentToken);
	    }
	}
	public static void m2()
	{
	    String str = "Use the force";
	    StringTokenizer tokenizer = new StringTokenizer(str,"e",false);
	    while(tokenizer.hasMoreTokens())
	    {
            String currentToken = tokenizer.nextToken();
            System.out.println(currentToken);
	    }
	}	
}

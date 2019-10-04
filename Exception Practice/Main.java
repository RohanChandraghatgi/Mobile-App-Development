public class Main{
    public static void main(String[]args){
        try4();
    }    
    public static void try1(){
        try{
            int x = 10;
            int y = 0;
            int z = x/y;
            System.out.println(z);
        }catch(ArithmeticException e){
            e.printStackTrace();
        }
    }
    public static void try2(){
        try{
            String neel = null;
            String mehar = "mehar";
            if(neel.equals(mehar))
                System.out.println("true");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void try3(){
        try{
           int [] neel = new int [2];
           System.out.println(neel[12]);
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public static void try4(){
        try{
           int [] neel = new int [-12];
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
}
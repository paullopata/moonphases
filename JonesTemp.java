/*
 * Prorgram to calculate Jones polynomial given a braid word and 
 * convention of directionality.  Note the braidword here is read
 * from left to right and bottom to top.  
 */
public class JonesTemp {   
   public static void ArrayRowPrint(int[] arrayToPrint) { //print Array as a row vector
   	   System.out.print("{ ");
   	   for (int i = 0; i < arrayToPrint.length - 1; i++) {
   	   	   System.out.print(arrayToPrint[i]);
   	   	   System.out.print(" , ");
   	   }
   	   System.out.print(arrayToPrint[arrayToPrint.length -1]);
   	   System.out.println(" }");
	}
	
	public static int Sign(int input) { //returns sign of the integer
		int temp = 0;
		if (input < 0) temp = -1;
		if (input == 0) temp = 0;
		if (input > 0) temp = 1;
		return temp;
	}
	
	public static int Writhe(int[] inputArray) { //sums the sign of the arguments of an array
		int tempSum = 0;
		for (int i = 0; i < inputArray.length; i++) {
			tempSum = tempSum + Sign(inputArray[i]);
		}
		return tempSum;
	}
	
	public static void main(String[] args) {
   	   //for (int i = 0; i < 10; i++)
   	   //System.out.println("Hello, world from Mac!");
   	   int[] Braidword = {-1, -2, -3, -1, -2, -3, 1,2,3};
   	   ArrayRowPrint(Braidword); //check to see if Braidword is as we've defined
   	   System.out.println(Writhe(Braidword));
   	  // System.out.println(Sign(1111));
   	   // System.out.println(Sign(0));
   	}
}

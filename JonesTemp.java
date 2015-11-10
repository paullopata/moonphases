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
	
	public static int ArraySum(int[] inputArray) { //sums the arguments of an array 
		int tempSum = 0;
		for (int i = 0; i < inputArray.length; i++) {
			tempSum = tempSum + inputArray[i];
		}
		return tempSum;
	}
	
	public static int AbsoluteValue(int input) {
		if (input < 0) return -input;
		else return input;
	}
	
	public static int MaxArrayAbsValue(int[] inputArray) { //returns maximum value of absolute value of Array elements
		int temp = AbsoluteValue(inputArray[0]);
		for (int i=1; i < inputArray.length; i++) {
			if (AbsoluteValue(inputArray[i]) > temp) temp = AbsoluteValue(inputArray[i]);
		}
		return temp;
	}
	
	public static int[] abArray(int input, int[] inputArray) { //generates an array representing of the A, B choices for the state
		int[] tempArray = new int[inputArray.length];
		int[] referenceArray = new int[inputArray.length];
		int temp = input;
		for (int i=0; i < inputArray.length; i++) {
			referenceArray[inputArray.length - 1 - i] = Power(2, i);
		}
		//
		if (input < 0 || input >= Power(2, inputArray.length) ) { //check for errors, make sure it's in bounds
			for (int i = 0; i < inputArray.length; i++) {
				tempArray[i] = 999; //error message.  Hopefully gets caught.  Is there a better way to handle this??
			}
		}
		else {
			for (int i = 0; i< inputArray.length; i++) {
				if (temp >= referenceArray[i]) {
					tempArray[i] = 1;
					temp = temp - referenceArray[i];
				}
				else {
					tempArray[i] = 0;
					temp = temp;
				}
			}
		}
		return tempArray;
	}
			
		
	public static int[][] BraidMatrix(int[] inputArray) { //returns BraidMatrix with input of a Braidword array
		int[][] tempArray = new int[inputArray.length +2][MaxArrayAbsValue(inputArray)+2]; 
		// //this could get defined at runtims (methinks?) so we need this
		// new int[*rowsize*][*columnsize*] language to save the right amount of space
		int ii = 0; //bottom row of matrix
		for (int j = 0; j < MaxArrayAbsValue(inputArray) + 2; j++) {
			tempArray[ii][j] = 0; //sets whole bottom row of matrix to zero
		}
		//
		ii = inputArray.length + 1; //top row of matrix
		for (int j = 0; j < MaxArrayAbsValue(inputArray) + 2; j++) {
			tempArray[ii][j] = 0; //sets whole top row of matrix to zero
		}
		//
		for (int i = 0; i < inputArray.length + 2; i++) {
			int jj = 0; //left column of matrix
			tempArray[i][jj] = 0; //sets whole left column to zero
		}
		//
		for (int i = 0; i < inputArray.length + 2; i++) {
			int jj = MaxArrayAbsValue(inputArray) + 1; //right column of matrix
			tempArray[i][jj] = 0; //sets whole right column to zero
		}
		//	
		for (int i = 1; i < (inputArray.length + 1); i++) {
			for (int j = 1; j < (MaxArrayAbsValue(inputArray) + 1); j++) {
				if (AbsoluteValue( inputArray[i-1] ) == j ) tempArray[i][j] = Sign(inputArray[i-1]) ;
				else tempArray[i][j] = 0;
				// This inserts a +1/-1 where in the row/column where an over/under-crossing
				//would appear in a diagram of the braid.  Flanked by zeroes on the bottom
				//and top rows and left and right columns.  This puts the braid on a grid.
				// These extra zeroes make the 
				// arithmetic for determining the walk around states easier-for me at least.
			}
		}
		for (int i = 0; i < tempArray.length / 2; i++) { //reverse rows of array
			int[] temp = tempArray[i];
			tempArray[i] = tempArray[(tempArray.length - 1) - i];
			tempArray[(tempArray.length - 1) - i] = temp;
		}
		//row reversal done to maintain convention of reading braidwords from
		//the bottom of the diagram to the top.
		return tempArray;
	}
	
	public static int Power(int inputBase, int inputExponent) { // computes Base^Exponent
		int temp = 1;
		for (int i = 0; i < inputExponent; i++) {
			temp = temp * inputBase;
		}
		return temp;
	}
	
	public static void MatrixOutput(int[][] inputMatrix) { //displays a non-ragged matrix 
		for (int i = 0; i < inputMatrix.length; i++) {
			for (int j = 0; j < inputMatrix[i].length; j++) {
				if (inputMatrix[i][j] < 0) System.out.print(" " + inputMatrix[i][j] + " ");
				else System.out.print("  " + inputMatrix[i][j] + " ");
			}
			System.out.println("");
		}
	}
		
	
	public static void main(String[] args) {
   	   //for (int i = 0; i < 10; i++)
   	   //System.out.println("Hello, world from Mac!");
   	   int[] Braidword = {1,-2,1,-2}; //list of integers
   	   ArrayRowPrint(Braidword); //check to see if Braidword is as we've defined
   	   System.out.println("Writhe is " + Writhe(Braidword));
   	   //System.out.println(MaxArrayAbsValue(Braidword));
   	   int HeightDim = Braidword.length + 2;
   	   int WidthDim = MaxArrayAbsValue(Braidword) +2;
   	   System.out.println("HeightDim is " + HeightDim );
   	   System.out.println("WidthDim is " + WidthDim );
   	   int[][] tempMatrix = BraidMatrix(Braidword);
   	   MatrixOutput(tempMatrix);
   	   int[] LoopCountVector = new int[Power(2,Braidword.length)];
   	   for (int i = 0; i < LoopCountVector.length; i++) {
   	   	   LoopCountVector[i] = 0;
   	   } //creates an array to store all the values of the loop counts from every state
   	   //System.out.println("Sum of entries in LoopCountVector = " + Writhe(LoopCountVector));
   	   //System.out.println("LoopCountVector length is " + LoopCountVector.length);
   	   //ArrayRowPrint(LoopCountVector);
   	   int index = 6; //arbitrary choice to test abArray() function
   	   System.out.println("ABIndex is " + index);
   	   System.out.print("ABVector is ");
   	   ArrayRowPrint(abArray(index, Braidword)); //this is the binary representation of ABIndex, written as an Array
   	}
}

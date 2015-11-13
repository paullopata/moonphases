/*
 * Prorgram to calculate Jones polynomial given a braid word and 
 * convention of directionality.  Note the braidword here is read
 * from left to right and bottom to top.  
 */
public class JonesTemp {   
   public static void ArrayRowPrintln(int[] arrayToPrint) { //print Array as a row vector
   	   System.out.print("{ ");
   	   for (int i = 0; i < arrayToPrint.length - 1; i++) {
   	   	   System.out.print(arrayToPrint[i]);
   	   	   System.out.print(" , ");
   	   }
   	   System.out.print(arrayToPrint[arrayToPrint.length -1]);
   	   System.out.println(" }");
	}

	public static int Count(int[] inputArray, int instance) { //counts the number of times instance shows up in inputArray
		int counter = 0;
		for (int i=0; i < inputArray.length; i++) {
			if (inputArray[i] == instance) counter++;
		}
		return counter;
	}
	
	public static void ArrayRowPrint(int[] arrayToPrint) { //print Array as a row vector
   	   System.out.print("{ ");
   	   for (int i = 0; i < arrayToPrint.length - 1; i++) {
   	   	   System.out.print(arrayToPrint[i]);
   	   	   System.out.print(" , ");
   	   }
   	   System.out.print(arrayToPrint[arrayToPrint.length -1]);
   	   System.out.print(" }");
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
	
	public static int[] AbArray(int input, int[] inputArray) { //generates an array representing of the A, B choices for the state
		//this doesn't seem to use inputArray except for length.  Should it be called with its length instead?
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
			
			
	public static int[][] BraidMatrix(int[] inputArray) { //returns BraidOrStateMatrix with input of a Braidword array
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
	
	public static int[][] BraidToState(int inputAB, int inputBraidword[]) { 
		//for a choice of inputAB (as an index from 0 to inputBraidword.length) 
		//and Braidword, outputs the state matrix
		int[] tempStateword = new int[inputBraidword.length];
		for (int i=0; i < inputBraidword.length; i++) {
			int[] tempAbArray = AbArray(inputAB, inputBraidword);
			tempStateword[i] = (2*tempAbArray[i]-1)*inputBraidword[i];
		}
		return BraidMatrix(tempStateword); //multiplying Braidword by the Stateword
		//then forming the matrix from that accounts for the fact that a +1 crossing
		//in the Braidword going via an A state transformation is the same
		//as a -1 crossing going via a B state transformation.  We could go to the
		//BraidMatrix first, then multiply the whole rows by +1 or -1 accordingly,
		//but multiplying the Braidword by +1 or -1 first, the forming the matrix from
		//that is a little quicker.
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
	
	public static int LatticeWalkLoopCount(int inputAbIndex, int[] inputBraidword) {
		//this function takes the Braidword and AbIndex as inputs
		//and determines the number of loops in the state diagram
		int tempLoopCounter = 0;
		//
		int[][] tempStateMatrix = BraidToState(inputAbIndex, inputBraidword);
		//
		int tempLMHeight = tempStateMatrix.length - 1;
		int tempLMWidth = tempStateMatrix[1].length - 1;  
		//these are used several times, so defining these values makes code easier to read and fewer function calls		
		int[][] tempLocationMatrix = new int[tempLMHeight][tempLMWidth];
		for (int i=0; i < (tempLMHeight); i++) { 
			for (int j=0; j < tempLMWidth; j++) {
				tempLocationMatrix[i][j]=0; //initialize tempLocationMatrix to all zeroes,
				//indicating that none of the locations have been traversed yet
			}
		}
		int[] tempStartingPointArray = new int[tempLMHeight*tempLMWidth];
		for (int i=0; i<tempStartingPointArray.length; i++) {
			tempStartingPointArray[i] = i; 
			//for the tempStartingPointArray, we're going to keep track of the various grid points
			//in tempLocationMatrix that we've visited by numbering each point, and crossing its
			//corresponding number off the tempStartingPointArray list after it's been visited.
			//In this way, once we complete a loop, we can immediately jump to the next valid
			//starting point by reading the smallest non-zero value off the tempStartingPointArray
		}
		int[] tempCurrentLocationAndDirection = {0,0,1} ;
		//first two indices are row and column location, and third index 
		//is the direction (+1 = up, -1 = down)
		while (tempStartingPointArray[0] > -1) { //check to see if any valid starting points remain
			/*
			System.out.print("tempStartingPointArray: ");
			ArrayRowPrintln(tempStartingPointArray);
			*/
			while (tempLocationMatrix[ tempCurrentLocationAndDirection[0] ][tempCurrentLocationAndDirection[1] ] == 0) { //check to see if we've been here before
				tempLocationMatrix[ tempCurrentLocationAndDirection[0] ][tempCurrentLocationAndDirection[1] ] = 1; //mark that we've been here
				tempStartingPointArray = RemoveStartingPoint(tempCurrentLocationAndDirection, tempStartingPointArray, tempLMWidth);
				tempCurrentLocationAndDirection = UpdateCurrentLocationAndDirection(tempCurrentLocationAndDirection, tempStateMatrix);
				/*
				System.out.print("tempStartingPointArray: ");
				ArrayRowPrintln(tempStartingPointArray);
				*/
			}
			tempLoopCounter++;
			tempCurrentLocationAndDirection[0] = ( (tempStartingPointArray[0])/tempLMWidth);
			tempCurrentLocationAndDirection[1] = (tempStartingPointArray[0]) % tempLMWidth ;
			tempCurrentLocationAndDirection[2] = 1 ; //Always start going up
		}
	return tempLoopCounter;		
	//
	}
	
	public static int Find(int input, int[] inputArray) {
		//finds the instance of input within inputArray and returns the Array index
		//tested.  looks okay
		int tempCounter;
		for (tempCounter = 0; tempCounter < inputArray.length; tempCounter++) {
			if (inputArray[tempCounter] == input) {
				break;
			}
		}
		return tempCounter;
	}
	
	public static int[] RemoveStartingPoint(int[] inputCurrentLocationAndDirection, int[] inputStartingPointArray, int inputWidth) {
		//function goes here.  Remove the incriminating point by sliding all subsequent points
		//one slot to the left and tacking a zero on at the end.
		//tested.  looks okay
		int[] tempStartingPointArray = inputStartingPointArray;
		int tempCurrentPoint = inputCurrentLocationAndDirection[0]*inputWidth + inputCurrentLocationAndDirection[1];
		int index = Find(tempCurrentPoint, tempStartingPointArray);
		for (int i=index; i < (tempStartingPointArray.length - 1); i++) {
			tempStartingPointArray[i] = tempStartingPointArray[i+1];
		}
		tempStartingPointArray[tempStartingPointArray.length - 1] = -1;
		return tempStartingPointArray;
	}
	
	
	public static int[] UpdateCurrentLocationAndDirection(int[] inputCurrentLocationAndDirection, int[][] inputStateMatrix) {
		//tested.  looks okay.  !
		int[] tempNewLocationAndDirection = new int[3];
		int row = inputCurrentLocationAndDirection[0] + (1 - inputCurrentLocationAndDirection[2])/2;
		int blColumn = inputCurrentLocationAndDirection[1];
		int brColumn = blColumn + 1;
		int bl = inputStateMatrix[row][blColumn];
		int br = inputStateMatrix[row][brColumn];
		int dir = inputCurrentLocationAndDirection[2];
		int xDel = (1 - bl - br)*(bl - br)/2;
		int yDel = dir * (bl + 1)*(br + 1)*(1 + (bl - 1)*(br - 1))/2;
		int newDir = dir * (1 - 2 * xDel * xDel);
		int tempLMHeight = inputStateMatrix.length - 1;		
		if ( (inputCurrentLocationAndDirection[0] - yDel) == tempLMHeight ) tempNewLocationAndDirection[0] = 0;
		else if ( (inputCurrentLocationAndDirection[0] - yDel) == -1 )  tempNewLocationAndDirection[0] = (tempLMHeight - 1);
		else tempNewLocationAndDirection[0] = (inputCurrentLocationAndDirection[0] - yDel);
		//this last if / else if tree takes account of fact that change in y direction
		//might put you on a location above the top row or below the bottom row.  
		//this brings you back.  The minus sign of yDel corresponds to matrices in opposite
		//direction to when all the arithmetic was done.  No big deal.
		tempNewLocationAndDirection[1] = inputCurrentLocationAndDirection[1] + xDel;
		tempNewLocationAndDirection[2] = newDir;
		return tempNewLocationAndDirection;
	}
		
			
	public static void main(String[] args) {
   	   //for (int i = 0; i < 10; i++)
   	   //System.out.println("Hello, world from Mac!");
   	   int[] Braidword = {1,-2,3,1,-2,3}; //list of integers
   	   ArrayRowPrintln(Braidword); //check to see if Braidword is as we've defined
   	   System.out.println("Writhe is " + Writhe(Braidword));
   	   //System.out.println(MaxArrayAbsValue(Braidword));
   	   /*
   	   int HeightDim = Braidword.length + 2;
   	   int WidthDim = MaxArrayAbsValue(Braidword) +2;
   	   System.out.println("HeightDim is " + HeightDim );
   	   System.out.println("WidthDim is " + WidthDim );
   	   int[][] tempMatrix = BraidMatrix(Braidword);
   	   MatrixOutput(tempMatrix);
   	   */
   	   int[] loopCountArray = new int[Power(2,Braidword.length)];
   	   int[] aCountArray = new int[Power(2,Braidword.length)];
   	   //for (int i = 0; i < loopCountArray.length; i++) {
   	   	 //  loopCountArray[i] = 0;
   	   //}
   	   //creates an array to store all the values of the loop counts from every state
   	   //System.out.println("Sum of entries in LoopCountVector = " + Writhe(LoopCountVector));
   	   //System.out.println("LoopCountVector length is " + LoopCountVector.length);
   	   //ArrayRowPrint(LoopCountVector);
   	   for(int abIndex = 0; abIndex < loopCountArray.length; abIndex++) {
   	   	   loopCountArray[abIndex] = LatticeWalkLoopCount(abIndex, Braidword);
   	   	   aCountArray[abIndex] = Count( AbArray(abIndex, Braidword) , 0); //keeps track of how many A "crossings" were used for a given abIndex
   	   }
   	   System.out.println("aCountArray: ");
   	   ArrayRowPrintln(aCountArray);
   	   System.out.println("loopCountArray: ");
   	   ArrayRowPrintln(loopCountArray);
   	   
   	   /*
   	   int trialABIndex = 0; //arbitrary choice to test abArray() function
   	   System.out.print("ABIndex is " + trialABIndex + " :  ");
   	   ArrayRowPrintln(AbArray(trialABIndex, Braidword)); //this is the binary representation of ABIndex, written as an Array
   	   System.out.println("State Matrix:");
   	   int[][] testStateMatrix = BraidToState(trialABIndex, Braidword);
   	   MatrixOutput(testStateMatrix);
   	   //System.out.print("ABVector is ");
   	   int loopTest = LatticeWalkLoopCount(trialABIndex, Braidword);
   	   System.out.println("Loop count is " + loopTest);
   	   */
   	   //int[] testArray = {0,1,2,3,4,5,6,7};
   	   //int target = 3;
   	   //ArrayRowPrint(testArray);
   	   //System.out.println("Target " + target + " found at location " + Find(target, testArray) );
   	   //int[] testCurrentLocationAndDirection = {1,0,1};
   	   //int[] newArray = RemoveStartingPoint(testCurrentLocationAndDirection, testArray, 4);
   	   //ArrayRowPrint(newArray);
   	 /*  int lmHeight = testStateMatrix.length - 1;
   	   int lmWidth = testStateMatrix[1].length - 1;
   	   int[] testStartingPointArray = new int[lmHeight*lmWidth];
		for (int i=0; i<testStartingPointArray.length; i++) {
			testStartingPointArray[i] = i; 
		}
   	   System.out.print("StartingPointArray: ");
		ArrayRowPrintln(testStartingPointArray);
   	   int[] testLocAndDir = {0,0,1};
   	   System.out.print("Initial Location and Direction are:");
   	   ArrayRowPrint(testLocAndDir);
   	   testStartingPointArray = RemoveStartingPoint(testLocAndDir, testStartingPointArray  ,lmWidth );
   	   System.out.print(" StartingPointArray: ");
   	   ArrayRowPrintln(testStartingPointArray);
   	   int[] updatedLocAndDir = UpdateCurrentLocationAndDirection(testLocAndDir, testStateMatrix);
   	   System.out.print("New Location and Direction: ");
   	   ArrayRowPrint(updatedLocAndDir);
   	   testStartingPointArray = RemoveStartingPoint(updatedLocAndDir, testStartingPointArray  ,lmWidth );
   	   System.out.print(" StartingPointArray: ");
   	   ArrayRowPrintln(testStartingPointArray);
   	    for (int i=0; i<4; i++){
   	   	   updatedLocAndDir = UpdateCurrentLocationAndDirection(updatedLocAndDir, testStateMatrix);
   	   	   System.out.print("New Location and Direction: ");
   	   	   ArrayRowPrint(updatedLocAndDir);
   	   	   testStartingPointArray = RemoveStartingPoint(updatedLocAndDir, testStartingPointArray  ,lmWidth );
   	   	   System.out.print(" StartingPointArray: ");
   	   	   ArrayRowPrintln(testStartingPointArray);
   	   } 
   	   */
   	}
}

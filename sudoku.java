import java.io.*;
import java.util.*;


public class sudoku {
	
	/* MAIN: Deals with main menu to load and to start to play sudoku. */
    public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner scnr = new Scanner(System.in);
		String promptUser = "";
		String filename = "";
		int [][] sudokuNums = new int[9][9];
		int count = 0;
		
		//The menu and user input will repeat as long as the input is NOT 3 AND count is less than 3
		while (!promptUser.equals("3") && count < 3) {
		
		//Welcome message
		System.out.println("\n Welcome to Miner's Sudoku!");
		
		//Display menu
		System.out.println("\n Please pick a menu option: "
		+ "\n 1. Load Sudoku "
		+ "\n 2. Play Sudoku" 
		+ "\n 3. Exit");
		
		//Assign promptUser to the user input
		promptUser = scnr.next();
		
		switch (promptUser) {
			/*If user picks 1: Then ask for a filename and assign the users input to filename*/
			case "1" : 
				System.out.println("\n What is the name and filetype of your file? (Ex. sudoku.txt)");
				filename = scnr.next();
				File file = new File(filename); 
				
				if (!file.exists() && !file.isDirectory()) { //If the file does not exist or not the directory name
					count++;
					System.out.println("This file does not exist or is an incorrect format. Please try again! Count: " + count);
					
					if (count == 3) {
						System.out.println("The program has ended due to 3 wrong inputs");
					}
					break;
				}
				
				//Assign sudokuNums array to loadSudoku that takes in the filename
				sudokuNums = loadSudoku (filename);
				System.out.println("\n What do you want to do next?"); //Show the menu again
				break;
			
			/* If user picks 2: If the filename does not exist then go back to this menu. Otherwise, call playSudoku that takes in the array
			sudokuNums */
			case "2" :
				if (filename == "") {//No file loaded into the program
					count++;
					System.out.println("Please upload a puzzle before playing sudoku. Count: " + count);
					promptUser = "again";
					
					if (count == 3) {
						System.out.println("The program has ended due to 3 wrong inputs");
					}
					break;
				}
				playSudoku(sudokuNums);//To play the main game
				break;
			
			/* If user picks 3: Say bye bye and end program */
			case "3" :
				System.out.println("Good bye!");
				break;
			
			/* Anything else then add one to count and show error message. End program after 3 wrong attemps (count == 3) */
			default :
				count++;
				System.out.println("Incorrect input. Please input a valid integer from 1-3. Count: " + count);
				
				if (count == 3) {
					System.out.println("The program has ended due to 3 wrong inputs");
				}
		}
		}
		
	}
	
	//LOADSUDOKU: Loads the sudoku puzzle from the text files and puts it in a 2d array
	public static int[][] loadSudoku (String filename) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(filename); //Create a file reader
		BufferedReader textReader = new BufferedReader(fr); 
		
		String [] currentLine;  //Used to hold in a single line from the text file
		int [][] sudokuNums = new int [9][9]; //Will hold in the txt files contents
		
		textReader.ready(); //Open the file to be read in
		for (int i = 0; i < 9; i++) {
			currentLine = textReader.readLine().split(","); //Split the numbers in the current line at "," 
			
			for (int j = 0; j < 9; j++) {
				sudokuNums [i][j] = Integer.valueOf(currentLine[j]); //Convert the elements of currentLine to integer and assign to 2d array
			}
		}
		//Close the file
		textReader.close();
		return sudokuNums;
	}
	
	//PLAYSUDOKU: MAIN GAMEPLAY AND ALLOWS USERS TO EITHER GET HINTS OR JUST PLAY REGULARLY
	public static void playSudoku (int [][] sudokuNums) {
		Scanner scnr = new Scanner(System.in);
		String promptUser = "";
		int rowNum = 0; //First bracket index for 2d array
		int colNum = 0; //Seconf bracket index for 2d array
		int userDigit = 0; //The number the user wants to put in the puzzle
		int count = 0; //To count wrong inputs
		
		//Will repeat the menu and puzzle entry as long as the puzzle is not filled AND the count is less than 3
		while (!isFull(sudokuNums) && (count < 3) ) { 
		
		//Print the puzzle from the 2d array
		printPuzzle(sudokuNums);
		
		//Display menu to either get hint or just play it regularly
		System.out.println("\n Please pick a menu option: "
		+ "\n 1. Get hints "
		+ "\n 2. Just provide input");
		
		//Assign promptUser to user input
		promptUser = scnr.next();
		
		switch (promptUser) {
			case "1": //Hints
				//Ask for a row number and assign rowNum to user's input
				System.out.println("Row number: ");
				rowNum = scnr.nextInt();
				rowNum -= 1; //Minus 1 to take into account that index starts at 0
				
				//Ask for a column number and assign colNum to users input
				System.out.println("Column number: ");
				colNum = scnr.nextInt();
				colNum -= 1; //Minus 1 to take into account that index starts at 0
				
				if (rowNum < 0 || rowNum >= 9 || colNum < 0 || colNum >= 9) {
					count++;
					System.out.println("Incorrect input. Please input a valid integer from 1-9. Count: " + count);
						
						if (count == 3) {
							System.out.println("The program has reverted to the main menu");
						}
					break;
				}
				
				//Display hint coordinates and the contents from sudokuHints
				System.out.print("Hints for " + (rowNum+1) + "," + (colNum+1) + ": "); 
				sudokuHints(sudokuNums, rowNum, colNum);
				break;
			
			case "2": //Regular input
				//Ask user to put in a row number and Assign rowNum to users input
				System.out.println("You can now add digits to the puzzle. Please enter the row of the puzzle where you want to add a number:");
				rowNum = scnr.nextInt();
				rowNum -= 1; //Minus 1 to take into account that index starts at 0
				
				//Ask uswe to put in a column number and Assign colNum to users input
				System.out.println("Please enter the column of the puzzle where you want to add a number:");
				colNum = scnr.nextInt(); 
				colNum -= 1; //Minus 1 to take into account that index starts at 0
				
				//Assign userDigit to user input 
				System.out.println("Enter the digit:");
				userDigit = scnr.nextInt();
				
				if (rowNum < 0 || rowNum >= 9 || colNum < 0 || colNum >= 9) {
					count++;
					System.out.println("Incorrect input. Please input a valid integer from 1-9. Count: " + count);
			
						if (count == 3) {
							System.out.println("The program has reverted to the main menu");
							break;
						}
					break;
				}
					
				//If the input is valid and does not break any sudoku rules then add in the digit into puzzle
				if (isValidInput(sudokuNums, rowNum, colNum, userDigit)) {
					sudokuNums[rowNum][colNum] = userDigit;
					break;
				}
			
			default: //Anything else then add to count and show error message. Go to main menu after count == 3
				count++;
				System.out.println("Incorrect input. Please input a valid integer from 1-2. Count: " + count);
				
				if (count == 3) {
					System.out.println("The program has reverted to the main menu");
					break;
				}
		}
		}
		
		//Exiting the loop, if the puzzle is filled then show congrats message and show completed puzzle. Go to main menu
		if (isFull(sudokuNums)) {
			System.out.println("Congratulations! You solved the puzzle!");
			printPuzzle(sudokuNums);
		}
	}
	
	//ISVALIDINPUTROW: CHECKS AT THE ROW NUMBER INPUTTED BY USER TO SEE IF THE USERS NUMBER IS NOT DUPLICATED ALREADY IN ROW
	public static boolean isValidInputRow (int [][] A, int rowNum, int userDigit) {
		//Repeat 9 times to see if a row has a similar number to the users digit
		for (int i = 0; i < 9 ; i++) {
			if ( A[rowNum][i] == userDigit)//Checks at same row but checks each column within it
				return false;
		}
		return true;
	}
	
	//ISVALIDINPUTCOL: CHECKS AT THE COLUMN NUMBER INPUUTED BY USER TO SEE IF THE USERS NUMBER IS NOT DUPLICATED ALREADY IN COLUMNS
	public static boolean isValidInputCol (int[][]A, int colNum, int userDigit) {
		//Repeat 9 times to see if a row has a similar number to the users digit
		for (int i = 0; i < 9 ; i++) {
			if ( A[i][colNum] == userDigit) //Checks at same column but checks each row within it
				return false;
		}
		return true;
	}
	
	//ISVALIDINPUTGRID: CHECKS A 3X3 GRID BASED ON THE LOCATION OF THE ROW AND COLUMN NUMBER AND TO SEE ANY DUPLICATED NUMBERS
	public static boolean isValidInputGrid (int[][]A, int rowNum, int colNum, int userDigit) {
		/* Divides the rowNum by mod 3 to get a remainder that is then subtracted from rowNum. Same process applies to colNum*/
		int r = rowNum - rowNum % 3; 
		int c = colNum - colNum % 3;
		
		//Nested for loop to check 2d array
		for (int i = r ; i< r+3 ; i++) { //i is assigned to r and i has to be less than r + 3
			for (int j = c; j < c+3 ; j++) {
				if(A[i][j] == userDigit) 
					return false;
            }
		}
		return true;
	}
	
	//ISVALIDINPU: CALLED ISVALIDINPUTROW, ISVALIDINPUTCOL, ISVALIDINPUTGRID AND IF ALL CONDITIONS ARE MET THEN IT IS VALID
	public static boolean isValidInput (int[][] sudokuNums, int rowNum, int colNum, int userDigit) {
		if (userDigit <= 0 || userDigit > 9) // No negative numbers ir numbers greaters than 9
			return false;
		
		if (isValidInputRow(sudokuNums, rowNum, userDigit) && isValidInputCol(sudokuNums, colNum, userDigit) &&
		isValidInputGrid(sudokuNums, rowNum, colNum, userDigit))
			return true;
		
		return false;
	}
	
	//ISFULL: CHECKS IF THE PUZZLE HAS ANY ZEROES AND IF IT DOES NOT THEN IT IS COMPLETED
	public static boolean isFull (int [][] A) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (A[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	
	//PRINTPUZZLE: PRINTS THE TEXT FILE PUZZLE AND WILL CHANGE ACCORDING TO THE USERS INPUT
	public static void printPuzzle (int [][] A) {
        System.out.println();
        
		//Nested for loop
		for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                if (A[i][j] == 0) //If P at [i][j] is equal to 0 then print 0 with space to accomodate dashses
					System.out.print("0 ");
                else 
					System.out.print(A[i][j] + " "); //Anything else then print the element at A[i][j] with space
                if (j==2 || j==5) 
					System.out.print("| "); //In between 3x3 grid then print dash to seperate 
            }
            
			System.out.println();
            
			if (i==2 || i == 5) { //Seperate 3x3 rows with dashes
                System.out.println("----- + ----- + -----");
            }
        }
        System.out.println();
	}
	
	//SUDOKUHINTS: CALLED INROW, INCOL, INGRID AND IF ALL CONDTIONS ARE MET THEN IT WILL DISPLAY VIABLE NUMBERS IN CELL INPUTTED FROM USER
	public static void sudokuHints (int [][] A, int rowNum, int colNum) {
		for (int i = 1; i <= 9; i++) { 
		if (!inRow(A,rowNum,i) && !inCol(A,colNum,i) && !inGrid(A,rowNum,colNum,i))
				System.out.print(i + " ");
		}
		
	}
	
	//INROW: CHECKS WITHIN A ROW IF A ROW HAS THE SAME NUMBERS AT N
	public static boolean inRow (int [][] A, int rowNum, int n) {
		for (int i = 0; i < 9; i++) {
			if (A[rowNum][i] == n)
				return true;
		}
		
		return false;
	}
	
	//INCOL: CHECKS WITHIN A COLUMN IF A COLUMN HAS THE SAME NUMBER AT N
	public static boolean inCol (int [][] A, int colNum, int n) {
		for (int i = 0; i < 9; i++) {
			if (A[i][colNum] == n)
				return true;
		}
		
		return false;
		
	}
	
	//INGRID: CHECKS WITHIN A 3X3 BASED ON ROWNUM AND COLNUM IF THE GRID HAS THE SAME NUMBER AT N
	public static boolean inGrid (int[][]A, int rowNum, int colNum, int n)  {
		/* Divides the rowNum by mod 3 to get a remainder that is then subtracted from rowNum. Same process applies to colNum*/
		int r = rowNum - rowNum % 3;
		int c = colNum - colNum % 3;
		
		//Nested for loop to check 2d array
		for (int i = r ; i< r+3 ; i++) {
			for (int j = c; j < c+3 ; j++) { //i is assigned to r and i has to be less than r + 3
				if(A[i][j] == n)
					return true;
			}
		}
		return false;
	}
	

}
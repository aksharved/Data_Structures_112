package restaurant;
/**
 * Use this class to test your Menu method. 
 * This class takes in two arguments:
 * - args[0] is the menu input file
 * - args[1] is the output file
 * 
 * This class:
 * - Reads the input and output file names from args
 * - Instantiates a new RUHungry object
 * - Calls the menu() method 
 * - Sets standard output to the output and prints the restaurant
 *   to that file
 * 
 * To run: java -cp bin restaurant.Menu menu.in menu.out
 * 
 */
public class Menu {
    public static void main(String[] args) {

	// 1. Read input files
	// Option to hardcode these values if you don't want to use the command line arguments
	   
	
        // 2. Instantiate an RUHungry object
        RUHungry rh = new RUHungry();

	// 3. Call the menu() method to read the menu
        rh.menu("menu.in");
        rh.createStockHashTable("stock.in");
        rh.updatePriceAndProfit();
        
        
        StdIn.setFile("order3.in");
        int amount = StdIn.readInt();
        for (int i = 0; i < amount; i++)
        {
                int quantity2 = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.order(item, quantity2);

        }
        


        
        //  StdIn.setFile("donate2.in");
        // int amount1 = StdIn.readInt();
        // for (int i = 0; i < amount1; i++)
        // {
        //         int quantity3 = StdIn.readInt();
        //         StdIn.readChar();
        //         String item1 = StdIn.readLine();
        //         rh.donation(item1, quantity3);


        // }
        
         
       
        StdIn.setFile("restock3.in");
        int amount2 = StdIn.readInt();
        for (int i = 0; i < amount2; i++)
        {
                int quantity4 = StdIn.readInt();
                StdIn.readChar();
                String item2 = StdIn.readLine();
                rh.restock(item2, quantity4);

                
        }
        
        
        
         

                
        


	// 4. Set output file
	// Option to remove this line if you want to print directly to the screen

	// 5. Print restaurant
        rh.printRestaurant();
    }
}

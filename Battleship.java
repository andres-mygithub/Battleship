import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battleship {
    public static void main(String[] args)
    {
        printGrid();
        prompt();
    }

    public static void prompt()
    {
        ArrayList<Integer> userX = new ArrayList<Integer>();
        ArrayList<Integer> userY = new ArrayList<Integer>();

        ArrayList<Integer> userGuessX = new ArrayList<Integer>();
        ArrayList<Integer> userGuessY = new ArrayList<Integer>();

        ArrayList<Integer> computerX = new ArrayList<Integer>();
        ArrayList<Integer> computerY = new ArrayList<Integer>();

        ArrayList<Integer> compGuessX = new ArrayList<Integer>();
        ArrayList<Integer> compGuessY = new ArrayList<Integer>();

        ArrayList<Integer> computerShips = new ArrayList<Integer>();

        Scanner input = new Scanner(System.in);

        System.out.print("Enter X coordinate for your ship: ");
        int x = input.nextInt();

        System.out.print("Enter Y coordinate for your ship: ");
        int y = input.nextInt();

        userX.add(x);
        userY.add(y);
        printArray(userX, userY);

        for (int i = 0; i < 4; i++) {

            if (userX.size() > 0 && userY.size() > 0)
            {

                System.out.print("Enter X coordinate for your ship: ");
                x = input.nextInt();

                System.out.print("Enter Y coordinate for your ship: ");
                y = input.nextInt();


                while (((userX.indexOf(x) != -1 && userY.indexOf(y) != -1) && (userX.indexOf(x) == userY.indexOf(y)))
                        || x > 9 || x < 0 || y > 9 || y < 0)
                {
                    System.out.println("Invalid entry. Please choose another value.");

                    if (x > 10) System.out.println("Your X coordinate cannot be greater than 10.");
                    else if (x < 0) System.out.println("Your X coordinate cannot be less than 0.");
                    if (y > 10) System.out.println("Your Y coordinate cannot be greater than 10.");
                    else if (y < 0) System.out.println("Your Y coordinate cannot be less than 0.");

                    System.out.print("Enter X coordinate for your ship: ");
                    x = input.nextInt();

                    System.out.print("Enter Y coordinate for your ship: ");
                    y = input.nextInt();

                }
                userX.add(x);
                userY.add(y);
            }
            printArray(userX, userY);
        }

        ArrayList<Integer> userHitList = new ArrayList<Integer>();
        userHitList.add(1000);
        userHitList.add(1000);
        userHitList.add(1000);
        userHitList.add(1000);
        userHitList.add(1000);

        ArrayList<Integer> compHitList = new ArrayList<Integer>();
        compHitList.add(1000);
        compHitList.add(1000);
        compHitList.add(1000);
        compHitList.add(1000);
        compHitList.add(1000);

        printGrid(userX, userY);
        computerShips = generateComputerShips(userX, userY, computerX, computerY, userHitList, compHitList);
        deployComputerShips();

        while (!checkWinner(userHitList) && !checkWinner(compHitList))
        {
            System.out.println("HERE");
            userBattle(computerX, computerY, userX, userY, userHitList, compHitList, userGuessX, userGuessY, compGuessX, compGuessY);
            compBattle(computerX, computerY, userX, userY, userHitList, compHitList, compGuessX, compGuessY, userGuessX, userGuessY);

            System.out.print("This is user list: ");
            printSimpleArray(userHitList);
            System.out.print("This is comp list: ");
            printSimpleArray(compHitList);
            printGrid(userX, userY, computerX, computerY, userHitList, compHitList);
        }

        if (checkWinner(userHitList)) System.out.println("Hooray! You won!");
        else System.out.println("Sorry, computer won.");
    }

    public static void printGrid()
    {
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println();
        System.out.println("Right now, the sea is empty.\n");

        for (int i = 0; i <= 11; i++)
        {
            if (i == 0 || i == 11) System.out.print("    ");

            for (int j = 0; j < 10; j++)
            {
                if (i == 0 || i == 11) System.out.print(j);
                else if(j == 1) System.out.print((i - 1) + " |  ");
                else if (j == 9) System.out.print(" | " + (i - 1));
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void printGrid(ArrayList<Integer> x, ArrayList<Integer> y)
    {
        System.out.println("**** This is what the grid looks like ****");
        System.out.println();

        boolean skip = false;
        boolean hasShip = false;

        for (int i = 0; i <= 11; i++)
        {
            if (i == 0 || i == 11) System.out.print("   ");

            for (int j = 0; j < 10; j++)
            {
                hasShip = false;

                if(i > 0 && i < 11 && j == 0) System.out.print((i - 1) + " |");
                if (i == 0 || i == 11) System.out.print(j);
                else if (i != 0 || i != 11)
                {
                    for (int idx = 0; idx < y.size(); idx++)
                    {
                        if (x.get(idx) == -1 && y.get(idx) == -1)
                        {
                            //System.out.print("[" + x.get(idx) + "," + y.get(idx) + "]");
                            System.out.print("X");
                            hasShip = true;
                        }
                        else if (x.get(idx) == j && y.get(idx) == (i - 1))
                        {
                            //System.out.print("[" + x.get(idx) + "," + y.get(idx) + "]");
                            System.out.print("@");
                            hasShip = true;
                        }
                    }
                    if (!hasShip)
                    {
                        //System.out.print(j);
                        System.out.print(" ");
                        hasShip = false;
                    }
                }
                if (i > 0 && i < 11 &&  j == 9) System.out.print("| " + (i - 1));
            }
            System.out.println();
        }

    }

    public static void printGrid(ArrayList<Integer> userX, ArrayList<Integer> userY,
                                 ArrayList<Integer> computerX, ArrayList<Integer> computerY,
                                 ArrayList<Integer> userHitList, ArrayList<Integer> compHitList)
    {
        boolean skip = false;
        boolean hasShip = false;

        System.out.print("User x: ");
        printSimpleArray(userX);
        System.out.print("User y: ");
        printSimpleArray(userY);

        System.out.print("User HitList: ");
        printSimpleArray(userHitList);

        System.out.print("Computer x: ");
        printSimpleArray(computerX);
        System.out.print("Computer y: ");
        printSimpleArray(computerY);

        System.out.print("Comp HitList: ");
        printSimpleArray(compHitList);
        System.out.println();

        for (int i = 0; i <= 11; i++)
        {
            if (i == 0 || i == 11) System.out.print("   ");

            for (int j = 0; j < 10; j++)
            {
                if(i > 0 && i < 11 && j == 0) System.out.print((i - 1) + " |");
                if (i == 0 || i == 11) System.out.print(j);
                else if (i != 0 || i != 11)
                {
                    hasShip = false;

                    for (int idx = 0; idx < userY.size(); idx++)
                    {
                        if (userX.get(idx) == j && userY.get(idx) == (i - 1) && compHitList.get(idx) == -1)
                        {
                            //System.out.print("[" + x.get(idx) + "," + y.get(idx) + "]" + " idx: " + idx);
                            System.out.print("X");
                            hasShip = true;
                        }
                        else if (userX.get(idx) == j && userY.get(idx) == (i - 1))
                        {
                            //System.out.print("[" + x.get(idx) + "," + y.get(idx) + "]");
                            System.out.print("@");
                            hasShip = true;
                        }
                        if (computerX.get(idx) == j && computerY.get(idx) == (i - 1) && userHitList.get(idx) == -1)
                        {
                            //System.out.print("[" + x.get(idx) + "," + y.get(idx) + "]");
                            System.out.print("!");
                            hasShip = true;
                        }
                        else if (computerX.get(idx) == j && computerY.get(idx) == (i - 1))
                        {
                            //System.out.print("[" + x.get(idx) + "," + y.get(idx) + "]");
                            System.out.print("#");
                            hasShip = true;
                        }
                    }
                    if (!hasShip)
                    {
                        //System.out.print(j);
                        System.out.print(" ");
                        hasShip = true;
                    }
                }
                if (i > 0 && i < 11 &&  j == 9) System.out.print("| " + (i - 1));
            }
            System.out.println();
        }
    }

    public static void printSimpleArray(ArrayList<Integer> arr)
    {
        System.out.print("[");
        for (int i = 0; i < arr.size(); i++)
        {
            if (i == arr.size() - 1) System.out.print(arr.get(i));
            else System.out.print(arr.get(i) + ", ");
        }
        System.out.println("]");
    }

    public static void printArray(ArrayList<Integer> userX, ArrayList<Integer> userY)
    {
        System.out.print("This is userX: [");
        for (int i = 0; i < userX.size(); i++)
        {
            if (i == userX.size() - 1) System.out.print(userX.get(i));
            else System.out.print(userX.get(i) + ", ");
        }
        System.out.println("]");

        System.out.print("This is userY: [");
        for (int i = 0; i < userY.size(); i++)
        {
            if (i == userY.size() - 1) System.out.print(userY.get(i));
            else System.out.print(userY.get(i) + ", ");
        }
        System.out.println("]");
    }

    public static void printCompArray(ArrayList<Integer> computerX, ArrayList<Integer> computerY)
    {
        System.out.print("This is computerX: [");
        for (int i = 0; i < computerX.size(); i++)
        {
            if (i == computerX.size() - 1) System.out.print(computerX.get(i));
            else System.out.print(computerX.get(i) + ", ");
        }
        System.out.println("]");

        System.out.print("This is computerY: [");
        for (int i = 0; i < computerY.size(); i++)
        {
            if (i == computerY.size() - 1) System.out.print(computerY.get(i));
            else System.out.print(computerY.get(i) + ", ");
        }
        System.out.println("]");
    }

    private static int getPos(int val, ArrayList<Integer> checkArr)
    {
        for (int i = 0; i < checkArr.size(); i++) {
            if (val == checkArr.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private static void deployComputerShips()
    {
        System.out.println("Computer is deploying ships");
        System.out.println("1. ship DEPLOYED");
        System.out.println("2. ship DEPLOYED");
        System.out.println("3. ship DEPLOYED");
        System.out.println("4. ship DEPLOYED");
        System.out.println("5. ship DEPLOYED");
        System.out.println("-----------------");
    }

    private static ArrayList generateComputerShips(ArrayList<Integer> userX, ArrayList<Integer> userY,
                                                   ArrayList<Integer> computerX, ArrayList<Integer> computerY,
                                                   ArrayList<Integer> userHitList, ArrayList<Integer> compHitList)
    {
        Random random = new Random();
        int compX;
        int compY;

        compX = random.nextInt(9);
        compY = random.nextInt(9);

        for (int i = 0; i < 5; i++)
        {
            compX = random.nextInt(9);
            compY = random.nextInt(9);

            for (int j = 0; j < 5; j++)
            {
                while (compX == userX.get(j) && compY == userY.get(j))
                {
                    compX = random.nextInt(9);
                    compY = random.nextInt(9);
                }
            }

            computerX.add(compX);
            computerY.add(compY);

        }

        ArrayList<Integer> computerShips = new ArrayList<Integer>();

        for (int i = 0, j = 0; i < 5 && j < 5; i++, j++)
        {
            computerShips.add(computerX.get(i));
            computerShips.add(computerY.get(j));
        }


        //printCompArray(computerX, computerY);
        printGrid(userX, userY, computerX, computerY, userHitList, compHitList);
        return computerShips;
    }

    private static void userBattle(ArrayList<Integer> compX, ArrayList<Integer> compY,
                                        ArrayList<Integer> userX, ArrayList<Integer> userY,
                                        ArrayList<Integer> userHitList, ArrayList<Integer> compHitList,
                                   ArrayList<Integer> userGuessX, ArrayList<Integer> userGuessY,
                                   ArrayList<Integer> compGuessX, ArrayList<Integer> compGuessY)
    {
        Scanner in = new Scanner(System.in);
        int x = 0, y = 0;

        System.out.println("YOUR TURN");
        System.out.print("Enter X coordinate: ");
        x = in.nextInt();
        System.out.print("Enter Y coordinate: ");
        y = in.nextInt();
        System.out.println();

        boolean beenGuessed = true;

        while(beenGuessed)
        {
            //Check if coordinates have already been guessed
            for (int i = 0; i < userGuessX.size(); i++)
            {
                if (x == userGuessX.get(i))
                {
                    if (y == userGuessY.get(i))
                    {
                        System.out.print("Sorry, you already guessed that coordinate.");
                        System.out.println("Please try again.");
                        System.out.println("Enter X coordinate: ");
                        x = in.nextInt();
                        System.out.println("Enter Y coordinate: ");
                        y = in.nextInt();
                        System.out.println();
                    }
                }
            }
            beenGuessed = false;

            //Check if coordinates have already been guessed by Computer
            for (int i = 0; i < compGuessX.size(); i++)
            {
                if (x == compGuessX.get(i))
                {
                    if (y == compGuessY.get(i))
                    {
                        beenGuessed = true;

                        System.out.print("Sorry, the computer already guessed that coordinate.");
                        System.out.println("Please try again.");
                        System.out.println("Enter X coordinate: ");
                        x = in.nextInt();
                        System.out.println("Enter Y coordinate: ");
                        y = in.nextInt();
                        System.out.println();
                    }
                }
            }
        }

        userGuessX.add(x);
        userGuessY.add(y);

        System.out.print("User guesses: ");
        printSimpleArray(userGuessX);
        System.out.print("              ");
        printSimpleArray(userGuessY);

        //Check if user guessed correctly
        for (int i = 0; i < userX.size(); i++)
        {
            if (x == compX.get(i)) {
                if (y == compY.get(i)) {
                    userHitList.set(i, -1);
                    System.out.println("Boom! You sunk the ship!");
                    break;
                }
            }
            else if (x == userX.get(i))
            {
                if (y == userY.get(i))
                {
                    System.out.println("Oh no, you sunk your own ship :(");
                    compHitList.set(i, -1);
                    break;
                }
            }
            else if (i == userX.size())
            {
                System.out.println("Sorry, you missed");
                break;
            }
        }
    }

    private static void compBattle(ArrayList<Integer> userX, ArrayList<Integer> userY, ArrayList<Integer> compX,
                                        ArrayList<Integer> compY, ArrayList<Integer> userHitList,  ArrayList<Integer> compHitList,
                                        ArrayList<Integer> compGuessX, ArrayList<Integer> compGuessY,
                                        ArrayList<Integer> userGuessX, ArrayList<Integer> userGuessY)
    {
        Scanner in = new Scanner(System.in);
        Random random = new Random();
        int x = 0, y = 0;

        x = random.nextInt(9);
        y = random.nextInt(9);

        System.out.println("COMPUTER'S TURN");

        boolean beenGuessed = true;

        while(beenGuessed)
        {
            //Check if coordinates have already been guessed
            for (int i = 0; i < compGuessX.size(); i++)
            {
                System.out.println("Inside 1st Loop");
                if (x == compGuessX.get(i))
                {
                    if (y == compGuessY.get(i))
                    {
                        x = random.nextInt(9);
                        y = random.nextInt(9);
                    }
                }

            }

            beenGuessed = false;

            //Check if coordinates have already been guessed by user
            for (int j = 0; j < userGuessX.size(); j++)
            {
                if (x == userGuessX.get(j))
                {
                    if (y == userGuessY.get(j))
                    {
                        beenGuessed = true;
                        x = random.nextInt(9);
                        y = random.nextInt(9);
                    }
                }
            }
        }

        compGuessX.add(x);
        compGuessY.add(y);

        System.out.print("Computer guesses: ");
        printSimpleArray(compGuessX);
        System.out.print("                  ");
        printSimpleArray(compGuessY);

        System.out.println("Computer X: " + x);
        System.out.println("Computer Y: " + y);

        //Check if user guessed correctly
        for (int i = 0; i < compX.size(); i++)
        {
            if (x == compX.get(i)) {
                if (y == compY.get(i)) {
                    compHitList.set(i, -1);
                    System.out.println("The Computer sunk your ship.");
                    break;
                }
            }
            else if (x == userX.get(i))
            {
                if (y == userY.get(i))
                {
                    userHitList.set(i, -1);
                    System.out.println("The Computer sunk one of its own ships!");
                    break;
                }
            }
            else if (i == compX.size() - 1)
            {
                System.out.println("Computer missed");
                break;
            }

        }
    }

    private static boolean checkWinner(ArrayList<Integer> arr)
    {
        int count = 0;

        for (int i = 0; i < arr.size(); i++) if (arr.get(i) == -1) count++;

        if (count == 5) return true;
        else return false;
    }

}


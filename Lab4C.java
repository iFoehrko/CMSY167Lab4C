/**
 * CMSY167 - 001
 *
 * @author Ingrid Sobieski Foehrkolb
 *
 * This program has an array of current bee names which is initialized into a List.
 * The list is sorted alphabetically using a recursive insertion sort algorithm.
 * The program prompts the user for a name and then uses a recursive binary search
 * algorithm to determine if the name is already taken.  The program will repeat
 * the prompt and search until the user enters 0.
 *
 * Bug solution:  I would write a class Bee where each Bee has a String name and
 * an int ID.  This way, multiple bees may have the same name as long as they have
 * a unique ID which will be automatically assigned based on the number of bees
 * already named (i.e. if the user enters a new name after the original 14, their
 * bee would have an ID of 15).
 */
package lab4c;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Lab4C {//Lab4C

    public static void main(String[] args) {//MAIN
        String[] names = {"Angel", "Thurmon", "Nancy", "Bubba", "Thelonius",
            "Calico", "Cassius", "Jojo", "Zeke", "Yancy", "Diane",
            "Xavier eXeter", "Elaine Elosky", "Cardi Bee"};                     //Creates an array of existing bee names
        List<String> hive = initialize(names);                                  //Saves the initialized list of existing names in "hive"

        Scanner input = new Scanner(System.in);
        String name;

        do {

            printHive(hive);                                                    //Prints the hive
            System.out.println("Please enter a bee's name or 0 to exit: ");     //Prompt user for a name or 0 to exit
            name = input.nextLine();                                            //User's input saved in name

            if (!name.equalsIgnoreCase("0")) {                                  //Test for exit value

                int taken = binarySearchHive(hive, name, 0, (hive.size() - 1));   //Calls recursive Binary Search function which returns -1 if available, index if taken

                if (taken != -1) {                                              //Test return value from binary search, if taken = -1, the name is taken
                    System.out.println("Bee more creative... This name is "
                            + "taken.\n\n");                                    //Output if name is taken
                } else {
                    System.out.println("You got a bee!\n\n");                   //Output if name is available
                }

            }

        } while (!name.equalsIgnoreCase("0"));                                  //Repeat until the user enters 0

    }//END MAIN

    public static List<String> initialize(String[] names) {//Initialize
        List<String> hive = new ArrayList(Arrays.asList(names));                //Creates a list from the array of strings passed in
        sortHive(hive, hive.size());                                            //Calls the recursive sort function
        return hive;                                                            //Return the newly initialized and sorted hive list
    }//END initialize

    /**
     * This method uses recursion to perform an insertion sort on the hive list
     *
     * @param hive contains the list of names in various orders
     * @param index is the number of elements in the hive that ARE sorted
     * @return hive after current element is put in its proper place
     */
    public static List<String> sortHive(List<String> hive, int index) {//sortHive

        //Index is the number of elements before the current element
        if (index == 0) {//BASE CASE
            return hive;                                                        //returns the hive as is
        } else {//RECURSION CASE
            hive = sortHive(hive, index - 1);                                     //Recursive call: sets previous elements in alphabetical order and saves result in hive
            String current = hive.get(index - 1);                                 //Current name being compared to previous elements in list
            int i = index - 2;                                                    //sets number of elements before the two elements being compared

            while (i >= 0 && hive.get(i).compareToIgnoreCase(current) > 0) {//while there is still an element to compare AND the current element comes before each element alphabetically
                hive.set(i + 1, hive.get(i));                                     //Move each alphabetically greater element down one index
                i--;                                                            //decrement i because there is one less element to compare to current element
            }
            hive.set(i + 1, current);                                             //Once an element that is alphabetically less than current element is found, set current element in next spot
            return hive;                                                        //returns hive with the new order
        }//end recursion case

    }//END sortHive

    public static void printHive(List<String> hive) {//printHive
        System.out.println("Current Names in the Hive: ");
        hive.stream().forEach(b -> System.out.println(b));                      //uses a stream to print out all names in the hive
        System.out.println("--------------------------");                       //separator
    }//END printHive

    /**
     * This method uses recursion to perform a binary search on the hive list.
     *
     * @param remainingBees contains the bees that could possibly match "search"
     * @param search search term
     * @param low this is the first index of possible name matches
     * @param high this is the last index of possible name matches
     * @return index of found name, -1 if no name matches (NOT case sensitive)
     */
    public static int binarySearchHive(List<String> remainingBees, String search, int low, int high) {//binarySearchHive
        int middle = (high + low) / 2;                                          //index of middle element of remaining bees, element in this location is compared
        int foundIndex = -1;                                                    //@return -1 if a match is not found, index of match if found, 

        if (low > high) {//BASE CASE, no match found
            return foundIndex;                                                  //returns -1
        }

        if (remainingBees.get(middle).compareToIgnoreCase(search) == 0) {//BASE CASE, match found
            foundIndex = middle;                                                //returns index of middle element being compared where match was found
        }

        if (remainingBees.get(middle).compareToIgnoreCase(search) < 0) {//if search is alphabetically greater, eliminate first half of list
            foundIndex = binarySearchHive(remainingBees, search, middle + 1, high);//low is now the element after middle
        }

        if (remainingBees.get(middle).compareToIgnoreCase(search) > 0) {//if search is alphabetically less, eliminate second half of list
            foundIndex = binarySearchHive(remainingBees, search, low, middle - 1);//high is now the element before middle
        }

        return foundIndex;                                                      //returns whatever foundIndex contains
    }//END binarySearchHive
}

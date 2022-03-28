package de.leuphana.va.onlineshop.customerserviceinvoker.view;

import java.util.List;
import java.util.Scanner;

/**
 * This View allows the user to select between multiple commands.
 * A message and multiple commands will be displayed.
 * The user can execute one of the commands by entering the desired number into the console.
 */
public abstract class SelectionView extends View {

    /**
     * This method displays the view, reads the user input, and executes the given command.
     */
    public int displaySelection() {
        // display view
        System.out.println(separator);
        System.out.println(getMessage());

        int optionNumber = 1;
        for (String option : getOptions()) {
            if (option == null) {
                System.out.println();
                continue;
            }

            System.out.printf("[%s] %s\n", optionNumber++, option);
        }

        // read user input
        int inputInteger = readIntInput();

        // execute command
        return inputInteger - 1;
    }

    protected abstract List<String> getOptions();

    /**
     * Reads an integer from the console.
     * Only accepts integer values in the range of the selectionOptions.
     *
     * @return the selected integer value
     */
    private int readIntInput() {
        System.out.print(inputPrefix);
        Scanner scanner = new Scanner(System.in);
        long optionCount = getOptions().size();

        while (scanner.hasNext()) {
            if (!scanner.hasNextInt()) {
                // input is not a number
                System.out.println("Bitte geben Sie eine Zahl zwischen 1 und " + optionCount + " ein");
                System.out.print(inputPrefix);
                scanner.next();
                continue;
            }

            int enteredNumber = scanner.nextInt();

            if (enteredNumber <= 0 || enteredNumber > optionCount) {
                // input number is out of valid range
                System.out.println("Bitte geben Sie eine Zahl zwischen 1 und " + optionCount + " ein");
                System.out.print(inputPrefix);
                continue;
            }

            // input number is valid
            return enteredNumber;
        }

        // this return statement should never be reached. -1 indicates an error
        return -1;
    }
}
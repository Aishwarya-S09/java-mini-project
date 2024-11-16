import java.util.Scanner;
public class Simple {
    // Method to check if a given date is valid
    public static boolean validity(int day, int month, int year) {
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12:
                if (day < 1 || day > 31 || year <= 0) {
                    return false; 
                }
                break;
            // February - Special handling for leap and non-leap years
            case 2:
                if (leapYear(year)) { // If leap year, max days are 29
                    if (day < 1 || day > 29 || year <= 0) {
                        return false; 
                    }
                } else { // Non-leap year, max days are 28
                    if (day < 1 || day > 28 || year <= 0) {
                        return false; 
                    }
                }
                break;
            case 4, 6, 9, 11:
                if (day < 1 || day > 30 || year <= 0) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }
    // Method to check if a given year is a leap year
    public static boolean leapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 != 0 || year % 400 == 0) {
                return true;
            }
        }
        return false;
    }
    // Method to calculate age based on Date of Birth and reference date
    public static void calculateAge(int dobDay, int dobMonth, int dobYear, int refDay, int refMonth, int refYear) {
        int years = refYear - dobYear;
        int months = refMonth - dobMonth; 
        int days = refDay - dobDay; 
        // Adjust months and days if the day difference is negative
        if (days < 0) {
            months--;
            days += 30; // Approximate days in a month
        }
        // Adjust years if the month difference is negative
        if (months < 0) {
            years--;
            months += 12; // Borrow months from the year
        }
        // Print the calculated age
        System.out.println("Age is: " + years + " years, " + months + " months, " + days + " days.");
    }
    // Method to calculate Date of Birth based on age and reference date
    public static void calculateDOB(int ageDay, int ageMonth, int ageYear, int refDay, int refMonth, int refYear) {
        int dobYear = refYear - ageYear; 
        int dobMonth = refMonth - ageMonth; 
        int dobDay = refDay - ageDay + 1;
        // Adjust the month and day if the day difference is negative
        if (dobDay < 0) {
            dobMonth--;
            dobDay += 30; // Approximate days in a month
        }
        // Adjust the year if the month difference is negative
        if (dobMonth < 0) {
            dobYear--;
            dobMonth += 12; // Borrow months from the year
        }
        // Check for leap year correction if 29th Feb falls in a non-leap year
        if (dobDay == 29 && dobMonth == 2 && !leapYear(dobYear)) {
            dobDay = 1; // Change to 1st March
            dobMonth = 3;
        }
        // Print the calculated Date of Birth
        System.out.println("Date of Birth is: " + dobDay + "-" + dobMonth + "-" + dobYear);
    }
    // Method to parse a date string based on the provided format and delimiter
    public static int[] parseDate(String dateStr, String format, String delimiter) {
        String[] dateParts = dateStr.split(delimiter); 
        int day = 0, month = 0, year = 0; 
        // Parse date based on the provided format
        if (format.equals("DDdlcMMdlcYYYY")) {
            day = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } else if (format.equals("YYYYdlcMMdlcDD")) {
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            day = Integer.parseInt(dateParts[2]);
        } else if (format.equals("MMdlcDDdlcYYYY")) {
            month = Integer.parseInt(dateParts[0]);
            day = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        }
        return new int[]{day, month, year}; 
    }
    // Main method: Entry point of the program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 
        // Input 1: User provides either DOB or AGE
        System.out.println("Enter the input(e.g.,DOB=27-02-2001 or AGE=12-05-0020 or EXIT)\n(Enter the date and months in format) : ");
        String input = sc.nextLine(); 
      /*  
        if (input.startsWith("EXIT")) {
            System.exit(0);
        } */
        // Input 2: Reference or current date
        System.out.println("Enter the reference date or current date (e.g., 27-02-2024): ");
        String refDate = sc.nextLine(); 
        // Input 3: Date format (e.g., DDdlcMMdlcYYYY)
        System.out.println("Enter the date format (e.g., DDdlcMMdlcYYYY, YYYYdlcMMdlcDD, MMdlcDDdlcYYYY): ");
        String format = sc.nextLine();
        // Input 4: Delimiter used in the date (e.g., -, /)
        System.out.println("Enter the delimiter (e.g., -, /, .): ");
        String delimiter = sc.nextLine();
        // Parse the reference date using the provided format and delimiter
        int[] refDateParts = parseDate(refDate, format, delimiter);
        int refDay = refDateParts[0];
        int refMonth = refDateParts[1];
        int refYear = refDateParts[2];
        // Validate the reference date
        if (!validity(refDay, refMonth, refYear)) {
            System.out.println("Reference date is invalid. Please enter valid details.");
            sc.close();
            return; // Exit if the reference date is invalid
        }
        // Check if the input starts with "DOB" or "AGE"
        if (input.startsWith("DOB")) {
            // If input is DOB, calculate the age
            String dobStr = input.split("=")[1]; // Extract the DOB
            int[] dobParts = parseDate(dobStr, format, delimiter);
            int dobDay = dobParts[0];
            int dobMonth = dobParts[1];
            int dobYear = dobParts[2];
            // Validate the DOB
            if (!validity(dobDay, dobMonth, dobYear)) {
                System.out.println("DOB is invalid. Please enter valid details.");
                sc.close();
                return; 
            }
            calculateAge(dobDay, dobMonth, dobYear, refDay, refMonth, refYear); // Calculate the age
        } else if (input.startsWith("AGE")) {
            // If input is AGE, calculate the DOB
            String agestr = input.split("=")[1];
            int[] ageParts = parseDate(agestr, format, delimiter);
            int ageDay = ageParts[0];
            int ageMonth = ageParts[1];
            int ageYear = ageParts[2];
            calculateDOB(ageDay, ageMonth, ageYear, refDay, refMonth, refYear); // Calculate the DOB
        } else {
            System.out.println("Invalid input. Please provide either DOB or AGE."); // Handle invalid input
        }    
        sc.close();
    }
}
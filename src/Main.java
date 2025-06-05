import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class User {
    String username;
    String password;
    String phone;
    String firstName;
    String lastName;

    public User(String username, String password, String phone, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

class Login {
    private User registeredUser;
    private boolean loginSuccess;

    // Checks if username is valid
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks password complexity
    public boolean checkPasswordComplexity(String password) {
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        return password.length() >= 8 && hasUpper && hasNumber && hasSpecial;
    }

    // Checks if the phone number is in valid SA format with international code
    public boolean checkCellPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^\\+27\\d{9}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    // Handles user registration
    public String registerUser(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username (must contain underscore and max 5 characters): ");
        String username = scanner.nextLine();
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        } else {
            System.out.println("Username successfully captured.");
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        } else {
            System.out.println("Password successfully captured.");
        }

        System.out.print("Enter phone number (e.g., +2712345678): ");
        String phone = scanner.nextLine();
        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        } else {
            System.out.println("Cell phone number successfully added.");
        }

        registeredUser = new User(username, password, phone, firstName, lastName);
        return "User has been registered successfully.";
    }

    // Handles login
    public boolean loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        if (registeredUser != null &&
                inputUsername.equals(registeredUser.username) &&
                inputPassword.equals(registeredUser.password)) {
            loginSuccess = true;
        } else {
            loginSuccess = false;
        }
        return loginSuccess;
    }

    // Returns login status message
    public String returnLoginStatus() {
        if (loginSuccess && registeredUser != null) {
            return "Welcome " + registeredUser.firstName + " " + registeredUser.lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== Registration ===");
        String registrationResult = login.registerUser(scanner);
        System.out.println(registrationResult);

        if (registrationResult.equals("User has been registered successfully.")) {
            System.out.println("\n=== Login ===");
            login.loginUser(scanner);
            System.out.println(login.returnLoginStatus());
        }

        scanner.close();
    }
}

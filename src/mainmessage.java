public class mainmessage {
        public static void main(String[] args) {
            // Create message system instance
            MessageSystem messageSystem = new MessageSystem();

            // Print header for application start
            System.out.println("===========================================");
            System.out.println("      MESSAGE MANAGEMENT APPLICATION      ");
            System.out.println("===========================================");

            // Populate with test data
            System.out.println("\n--- Populating Test Data ---");
            messageSystem.addMessage("Did you get the cake?", "+27834557896", "Sent");
            messageSystem.addMessage("Where are you? You are late! I have asked you to be on time.", "+27838884567", "Stored");
            messageSystem.addMessage("Yohoooo, I am at your gate.", "+27834484567", "Disregard");
            messageSystem.addMessage("It is dinner time!", "0838884567", "Sent");
            messageSystem.addMessage("Ok, I am leaving without you.", "+27838884567", "Stored");
            System.out.println("Added 5 test messages to the system");

            // Demonstrate all required functionality
            System.out.println("\n--- Display Sent Messages ---");
            messageSystem.displaySentMessages();

            System.out.println("\n--- Display Longest Message ---");
            messageSystem.displayLongestMessage();

            System.out.println("\n--- Search by Message ID ---");
            messageSystem.searchByMessageId("0838884567");

            System.out.println("\n--- Search by Recipient ---");
            messageSystem.searchByRecipient("+27838884567");

            System.out.println("\n--- Delete Message ---");
            // Delete the second message and verify deletion
            String hashToDelete = messageSystem.getMessageHashByContent("Where are you? You are late! I have asked you to be on time.");
            messageSystem.deleteMessageByHash(hashToDelete);
            System.out.println("Verifying message count after deletion: " + messageSystem.getAllMessages().size());

            System.out.println("\n--- Display Message Report ---");
            messageSystem.displayReport();

            // Run the comprehensive unit tests
            System.out.println("\n===========================================");
            System.out.println("           RUNNING UNIT TESTS             ");
            System.out.println("===========================================");
            MessageTests.runTests();
        }
    }


import java.util.ArrayList;
import java.util.List;


    import java.util.ArrayList;
import java.util.List;

    /**
     * Comprehensive test suite for the MessageSystem
     * Implements multiple unit tests to verify functionality
     */
    class MessageTests {
        /**
         * Main test runner method
         * Executes all unit tests and reports results
         */
        public static void runTests() {
            int totalTests = 0;
            int passedTests = 0;

            // Create a fresh message system for testing
            MessageSystem testSystem = new MessageSystem();
            populateTestData(testSystem);

            // Run the basic functionality tests
            totalTests++;
            if (testSentMessagesArray(testSystem)) passedTests++;

            totalTests++;
            if (testDisplayLongestMessage(testSystem)) passedTests++;

            totalTests++;
            if (testSearchById(testSystem)) passedTests++;

            totalTests++;
            if (testSearchByRecipient(testSystem)) passedTests++;

            totalTests++;
            if (testDeleteMessage(testSystem)) passedTests++;

            // Run additional validation tests
            totalTests++;
            if (testEmptyRecipientValidation()) passedTests++;

            totalTests++;
            if (testEmptyContentValidation()) passedTests++;

            totalTests++;
            if (testInvalidFlagValidation()) passedTests++;

            totalTests++;
            if (testEmptyHashValidation()) passedTests++;

            totalTests++;
            if (testHashConsistency()) passedTests++;

            // Print summary
            System.out.println("\n===========================================");
            System.out.println("           TEST SUMMARY                  ");
            System.out.println("===========================================");
            System.out.println("Total Tests: " + totalTests);
            System.out.println("Passed Tests: " + passedTests);
            System.out.println("Failed Tests: " + (totalTests - passedTests));
            System.out.println("Success Rate: " + (passedTests * 100 / totalTests) + "%");
            System.out.println("===========================================");
        }

        /**
         * Populates test system with sample data
         *
         * @param system The MessageSystem to populate
         */
        private static void populateTestData(MessageSystem system) {
            System.out.println("\nPopulating test data for unit tests...");
            system.addMessage("Did you get the cake?", "+27834557896", "Sent");
            system.addMessage("Where are you? You are late! I have asked you to be on time.", "+27838884567", "Stored");
            system.addMessage("Yohoooo, I am at your gate.", "+27834484567", "Disregard");
            system.addMessage("It is dinner time!", "0838884567", "Sent");
            system.addMessage("Ok, I am leaving without you.", "+27838884567", "Stored");
            System.out.println("Test data populated successfully");
        }

        /**
         * Tests if sent messages array is correctly populated
         *
         * @param system The MessageSystem to test
         * @return true if test passed, false otherwise
         */
        private static boolean testSentMessagesArray(MessageSystem system) {
            System.out.println("\nTest: Sent Messages array correctly populated");

            List<Message> sentMessages = system.getSentMessages();

            // Verify array size
            boolean sizeCorrect = sentMessages.size() == 2;

            // Verify array contents
            boolean contentsCorrect = true;
            List<String> expectedContents = new ArrayList<>();
            expectedContents.add("Did you get the cake?");
            expectedContents.add("It is dinner time!");

            for (Message message : sentMessages) {
                if (!expectedContents.contains(message.getContent())) {
                    contentsCorrect = false;
                    break;
                }
            }

            boolean testPassed = sizeCorrect && contentsCorrect;

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));
            System.out.println("- Array size correct: " + sizeCorrect + " (expected 2, got " + sentMessages.size() + ")");
            System.out.println("- Array contents correct: " + contentsCorrect);

            if (testPassed) {
                System.out.println("- Sent messages found:");
                for (Message message : sentMessages) {
                    System.out.println("  \"" + message.getContent() + "\" to " + message.getRecipient());
                }
            } else {
                System.out.println("- Error details:");
                if (!sizeCorrect) {
                    System.out.println("  Wrong number of sent messages. Expected 2, got " + sentMessages.size());
                }
                if (!contentsCorrect) {
                    System.out.println("  Incorrect message content in sent messages array");
                    System.out.println("  Found messages:");
                    for (Message message : sentMessages) {
                        System.out.println("  \"" + message.getContent() + "\"");
                    }
                    System.out.println("  Expected messages:");
                    for (String content : expectedContents) {
                        System.out.println("  \"" + content + "\"");
                    }
                }
            }

            return testPassed;
        }

        /**
         * Tests if the longest message function works correctly
         *
         * @param system The MessageSystem to test
         * @return true if test passed, false otherwise
         */
        private static boolean testDisplayLongestMessage(MessageSystem system) {
            System.out.println("\nTest: Display longest message");

            // Find the longest message
            String longest = null;
            int maxLength = 0;

            for (Message message : system.getAllMessages()) {
                if (message.getContent().length() > maxLength) {
                    maxLength = message.getContent().length();
                    longest = message.getContent();
                }
            }

            boolean testPassed = longest != null &&
                    longest.equals("Where are you? You are late! I have asked you to be on time.");

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));

            if (testPassed) {
                System.out.println("- Longest message correctly identified:");
                System.out.println("  \"" + longest + "\"");
                System.out.println("- Length: " + longest.length() + " characters");
            } else {
                System.out.println("- Error: Failed to identify correct longest message");
                System.out.println("- Expected: \"Where are you? You are late! I have asked you to be on time.\"");
                System.out.println("- Found: \"" + (longest != null ? longest : "null") + "\"");
            }

            return testPassed;
        }

        /**
         * Tests the search by message ID functionality
         *
         * @param system The MessageSystem to test
         * @return true if test passed, false otherwise
         */
        private static boolean testSearchById(MessageSystem system) {
            System.out.println("\nTest: Search by message ID");

            String testId = "0838884567";
            Message foundMessage = null;

            for (Message message : system.getAllMessages()) {
                if (message.getId().equals(testId)) {
                    foundMessage = message;
                    break;
                }
            }

            boolean correctMessageFound = foundMessage != null &&
                    foundMessage.getContent().equals("It is dinner time!");

            System.out.println("Test " + (correctMessageFound ? "PASSED" : "FAILED"));

            if (correctMessageFound) {
                System.out.println("- Correctly found message with ID " + testId + ":");
                System.out.println("  \"" + foundMessage.getContent() + "\"");
                System.out.println("- Recipient: " + foundMessage.getRecipient());
                System.out.println("- Status: " + foundMessage.getFlag());
            } else {
                System.out.println("- Error: Failed to find correct message with ID " + testId);
                if (foundMessage == null) {
                    System.out.println("- No message found with this ID");
                } else {
                    System.out.println("- Found incorrect message: \"" + foundMessage.getContent() + "\"");
                    System.out.println("- Expected: \"It is dinner time!\"");
                }
            }

            return correctMessageFound;
        }

        /**
         * Tests the search by recipient functionality
         *
         * @param system The MessageSystem to test
         * @return true if test passed, false otherwise
         */
        private static boolean testSearchByRecipient(MessageSystem system) {
            System.out.println("\nTest: Search by recipient");

            String testRecipient = "+27838884567";
            List<String> messages = new ArrayList<>();

            for (Message message : system.getAllMessages()) {
                if (message.getRecipient().equals(testRecipient)) {
                    messages.add(message.getContent());
                }
            }

            boolean correctCount = messages.size() == 2;
            boolean correctContent = messages.contains("Where are you? You are late! I have asked you to be on time.") &&
                    messages.contains("Ok, I am leaving without you.");

            boolean testPassed = correctCount && correctContent;

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));

            if (testPassed) {
                System.out.println("- Found " + messages.size() + " messages for recipient " + testRecipient);
                System.out.println("- Messages:");
                for (String message : messages) {
                    System.out.println("  \"" + message + "\"");
                }
            } else {
                System.out.println("- Error details:");
                if (!correctCount) {
                    System.out.println("  Wrong message count. Expected 2, got " + messages.size());
                }
                if (!correctContent) {
                    System.out.println("  Incorrect message content found");
                    System.out.println("  Found:");
                    for (String message : messages) {
                        System.out.println("  \"" + message + "\"");
                    }
                    System.out.println("  Should contain both:");
                    System.out.println("  \"Where are you? You are late! I have asked you to be on time.\"");
                    System.out.println("  \"Ok, I am leaving without you.\"");
                }
            }

            return testPassed;
        }

        /**
         * Tests the message deletion functionality
         *
         * @param system The MessageSystem to test
         * @return true if test passed, false otherwise
         */
        private static boolean testDeleteMessage(MessageSystem system) {
            System.out.println("\nTest: Delete a message using hash");

            // Get hash of message to delete
            String messageToDelete = "Where are you? You are late! I have asked you to be on time.";
            String hashToDelete = system.getMessageHashByContent(messageToDelete);

            if (hashToDelete == null) {
                System.out.println("Test FAILED - Could not find hash for test message");
                return false;
            }

            int beforeCount = system.getAllMessages().size();
            boolean deleteResult = system.deleteMessageByHash(hashToDelete);
            int afterCount = system.getAllMessages().size();

            boolean countCorrect = beforeCount == 5 && afterCount == 4;
            boolean messageRemoved = system.getMessageByHash(hashToDelete) == null;

            boolean testPassed = deleteResult && countCorrect && messageRemoved;

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));

            if (testPassed) {
                System.out.println("- Successfully deleted message");
                System.out.println("- Message count before: " + beforeCount);
                System.out.println("- Message count after: " + afterCount);
                System.out.println("- Deletion confirmed: Message no longer exists in system");
            } else {
                System.out.println("- Error details:");
                if (!deleteResult) {
                    System.out.println("  Delete operation reported failure");
                }
                if (!countCorrect) {
                    System.out.println("  Message count incorrect. Expected: before=5, after=4");
                    System.out.println("  Got: before=" + beforeCount + ", after=" + afterCount);
                }
                if (!messageRemoved) {
                    System.out.println("  Message still exists in system after deletion");
                }
            }

            return testPassed;
        }

        /**
         * Tests validation for empty recipient input
         *
         * @return true if test passed, false otherwise
         */
        private static boolean testEmptyRecipientValidation() {
            System.out.println("\nTest: Empty recipient validation");

            MessageSystem system = new MessageSystem();
            int beforeCount = system.getAllMessages().size();
            system.addMessage("Test message", "", "Sent");
            int afterCount = system.getAllMessages().size();

            boolean testPassed = beforeCount == afterCount;

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));
            System.out.println("- System " + (testPassed ? "correctly rejected" : "incorrectly accepted") +
                    " message with empty recipient");

            return testPassed;
        }

        /**
         * Tests validation for empty content input
         *
         * @return true if test passed, false otherwise
         */
        private static boolean testEmptyContentValidation() {
            System.out.println("\nTest: Empty content validation");

            MessageSystem system = new MessageSystem();
            int beforeCount = system.getAllMessages().size();
            system.addMessage("", "+27834557896", "Sent");
            int afterCount = system.getAllMessages().size();

            boolean testPassed = beforeCount == afterCount;

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));
            System.out.println("- System " + (testPassed ? "correctly rejected" : "incorrectly accepted") +
                    " message with empty content");

            return testPassed;
        }

        /**
         * Tests validation for invalid flag input
         *
         * @return true if test passed, false otherwise
         */
        private static boolean testInvalidFlagValidation() {
            System.out.println("\nTest: Invalid flag validation");

            MessageSystem system = new MessageSystem();
            int beforeCount = system.getAllMessages().size();
            system.addMessage("Test message", "+27834557896", "Invalid");
            int afterCount = system.getAllMessages().size();

            boolean testPassed = beforeCount == afterCount;

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));
            System.out.println("- System " + (testPassed ? "correctly rejected" : "incorrectly accepted") +
                    " message with invalid flag");

            return testPassed;
        }

        /**
         * Tests validation for empty hash when deleting
         *
         * @return true if test passed, false otherwise
         */
        private static boolean testEmptyHashValidation() {
            System.out.println("\nTest: Empty hash validation");

            MessageSystem system = new MessageSystem();
            system.addMessage("Test message", "+27834557896", "Sent");
            int beforeCount = system.getAllMessages().size();
            boolean deleteResult = system.deleteMessageByHash("");
            int afterCount = system.getAllMessages().size();

            boolean testPassed = !deleteResult && beforeCount == afterCount;

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));
            System.out.println("- System " + (testPassed ? "correctly rejected" : "incorrectly accepted") +
                    " deletion with empty hash");

            return testPassed;
        }

        /**
         * Tests that hash generation is consistent for same content
         *
         * @return true if test passed, false otherwise
         */
        private static boolean testHashConsistency() {
            System.out.println("\nTest: Hash consistency");

            MessageSystem system = new MessageSystem();
            system.addMessage("Same content test", "+27834557896", "Sent");
            system.addMessage("Same content test", "+27838884567", "Stored");

            Message message1 = system.getAllMessages().get(0);
            Message message2 = system.getAllMessages().get(1);

            boolean testPassed = message1.getHash().equals(message2.getHash());

            System.out.println("Test " + (testPassed ? "PASSED" : "FAILED"));
            if (testPassed) {
                System.out.println("- Same content produces consistent hash values");
                System.out.println("- Hash: " + message1.getHash());
            } else {
                System.out.println("- Same content produces different hash values");
                System.out.println("- Hash 1: " + message1.getHash());
                System.out.println("- Hash 2: " + message2.getHash());
            }

            return testPassed;
        }
    }


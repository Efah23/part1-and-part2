import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


    import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

    /**
     * Message class represents a single message in the system
     * Contains message content, recipient information, status flags and metadata
     */
    class Message {
        private final String content;
        private final String recipient;
        private final String flag;
        private final String hash;
        private final String id;
        private final LocalDateTime timestamp;

        /**
         * Constructor for creating a new message
         *
         * @param content   The text content of the message
         * @param recipient The recipient's phone number
         * @param flag      The status flag ("Sent", "Stored", "Disregard")
         */
        public Message(String content, String recipient, String flag) {
            this.content = content;
            this.recipient = recipient;
            this.flag = flag;
            this.hash = generateHash(content);
            this.id = recipient; // Using recipient as ID for simplicity
            this.timestamp = LocalDateTime.now();
        }

        /**
         * Generate a SHA-256 hash of the message content for unique identification
         *
         * @param input The string to hash
         * @return      The hexadecimal representation of the hash
         */
        private String generateHash(String input) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(input.getBytes());

                StringBuilder hexString = new StringBuilder();
                for (byte hashByte : hashBytes) {
                    String hex = Integer.toHexString(0xff & hashByte);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                return "hash-error-" + System.currentTimeMillis();
            }
        }

        /**
         * Get the formatted timestamp of the message
         *
         * @return Formatted date-time string
         */
        public String getFormattedTimestamp() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return timestamp.format(formatter);
        }

        // Getters
        public String getContent() { return content; }
        public String getRecipient() { return recipient; }
        public String getFlag() { return flag; }
        public String getHash() { return hash; }
        public String getId() { return id; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    /**
     * MessageSystem manages all message operations including storage, retrieval,
     * searching, and reporting functions.
     */
    class MessageSystem {
        private final List<Message> allMessages;
        private final List<Message> sentMessages;
        private final List<Message> disregardedMessages;
        private final List<Message> storedMessages;
        private final List<String> messageHashes;
        private final List<String> messageIds;

        /**
         * Constructor initializes all storage collections
         */
        public MessageSystem() {
            allMessages = new ArrayList<>();
            sentMessages = new ArrayList<>();
            disregardedMessages = new ArrayList<>();
            storedMessages = new ArrayList<>();
            messageHashes = new ArrayList<>();
            messageIds = new ArrayList<>();
        }

        /**
         * Add a message to the system and categorize it based on flag
         *
         * @param content   The text content of the message
         * @param recipient The recipient's phone number
         * @param flag      The status flag ("Sent", "Stored", "Disregard")
         */
        public void addMessage(String content, String recipient, String flag) {
            // Input validation
            if (content == null || content.trim().isEmpty()) {
                System.out.println("Error: Message content cannot be empty");
                return;
            }

            if (recipient == null || recipient.trim().isEmpty()) {
                System.out.println("Error: Recipient cannot be empty");
                return;
            }

            if (!"Sent".equals(flag) && !"Stored".equals(flag) && !"Disregard".equals(flag)) {
                System.out.println("Error: Flag must be one of: Sent, Stored, Disregard");
                return;
            }

            Message message = new Message(content, recipient, flag);
            allMessages.add(message);
            messageHashes.add(message.getHash());
            messageIds.add(message.getId());

            // Categorize message based on flag
            switch (flag) {
                case "Sent":
                    sentMessages.add(message);
                    break;
                case "Disregard":
                    disregardedMessages.add(message);
                    break;
                case "Stored":
                    storedMessages.add(message);
                    break;
            }
        }

        /**
         * Display sender and recipient of all sent messages
         */
        public void displaySentMessages() {
            if (sentMessages.isEmpty()) {
                System.out.println("No sent messages found.");
                return;
            }

            System.out.println("Sender and recipient of all sent messages:");
            System.out.println("-------------------------------------------");

            for (Message message : sentMessages) {
                System.out.println("Recipient: " + message.getRecipient());
                System.out.println("Message: \"" + message.getContent() + "\"");
                System.out.println("Time: " + message.getFormattedTimestamp());
                System.out.println("-------------------------------------------");
            }
        }

        /**
         * Display the longest message in the system
         */
        public void displayLongestMessage() {
            if (allMessages.isEmpty()) {
                System.out.println("No messages found in the system.");
                return;
            }

            Message longest = null;
            int maxLength = 0;

            for (Message message : allMessages) {
                if (message.getContent().length() > maxLength) {
                    maxLength = message.getContent().length();
                    longest = message;
                }
            }

            if (longest != null) {
                System.out.println("Longest message details:");
                System.out.println("-------------------------------------------");
                System.out.println("Message: \"" + longest.getContent() + "\"");
                System.out.println("Length: " + longest.getContent().length() + " characters");
                System.out.println("Recipient: " + longest.getRecipient());
                System.out.println("Status: " + longest.getFlag());
                System.out.println("Hash: " + longest.getHash());
                System.out.println("Time: " + longest.getFormattedTimestamp());
                System.out.println("-------------------------------------------");
            }
        }

        /**
         * Search for a message by its ID
         *
         * @param id The message ID to search for
         */
        public void searchByMessageId(String id) {
            if (id == null || id.trim().isEmpty()) {
                System.out.println("Error: Message ID cannot be empty");
                return;
            }

            boolean found = false;
            for (Message message : allMessages) {
                if (message.getId().equals(id)) {
                    System.out.println("Message found with ID " + id + ":");
                    System.out.println("-------------------------------------------");
                    System.out.println("Recipient: " + message.getRecipient());
                    System.out.println("Message: \"" + message.getContent() + "\"");
                    System.out.println("Status: " + message.getFlag());
                    System.out.println("Hash: " + message.getHash());
                    System.out.println("Time: " + message.getFormattedTimestamp());
                    System.out.println("-------------------------------------------");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No message found with ID: " + id);
            }
        }

        /**
         * Search for all messages sent to a particular recipient
         *
         * @param recipient The phone number to search for
         */
        public void searchByRecipient(String recipient) {
            if (recipient == null || recipient.trim().isEmpty()) {
                System.out.println("Error: Recipient cannot be empty");
                return;
            }

            System.out.println("Messages for recipient " + recipient + ":");
            System.out.println("-------------------------------------------");

            boolean found = false;
            for (Message message : allMessages) {
                if (message.getRecipient().equals(recipient)) {
                    System.out.println("Message: \"" + message.getContent() + "\"");
                    System.out.println("Status: " + message.getFlag());
                    System.out.println("Time: " + message.getFormattedTimestamp());
                    System.out.println("-------------------------------------------");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No messages found for recipient: " + recipient);
            }
        }

        /**
         * Delete a message using its hash
         *
         * @param hash The hash of the message to delete
         * @return true if message was deleted, false otherwise
         */
        public boolean deleteMessageByHash(String hash) {
            if (hash == null || hash.trim().isEmpty()) {
                System.out.println("Error: Message hash cannot be empty");
                return false;
            }

            Message toDelete = null;

            for (Message message : allMessages) {
                if (message.getHash().equals(hash)) {
                    toDelete = message;
                    break;
                }
            }

            if (toDelete != null) {
                String content = toDelete.getContent();
                allMessages.remove(toDelete);
                messageHashes.remove(toDelete.getHash());
                messageIds.remove(toDelete.getId());

                // Remove from appropriate category list
                switch (toDelete.getFlag()) {
                    case "Sent":
                        sentMessages.remove(toDelete);
                        break;
                    case "Disregard":
                        disregardedMessages.remove(toDelete);
                        break;
                    case "Stored":
                        storedMessages.remove(toDelete);
                        break;
                }

                System.out.println("Message successfully deleted:");
                System.out.println("-------------------------------------------");
                System.out.println("Content: \"" + content + "\"");
                System.out.println("Recipient: " + toDelete.getRecipient());
                System.out.println("Status: " + toDelete.getFlag());
                System.out.println("Hash: " + hash);
                System.out.println("-------------------------------------------");
                return true;
            } else {
                System.out.println("No message found with hash: " + hash);
                return false;
            }
        }

        /**
         * Display a comprehensive report of all messages
         */
        public void displayReport() {
            System.out.println("===========================================");
            System.out.println("           MESSAGING SYSTEM REPORT        ");
            System.out.println("===========================================");
            System.out.println("Total Messages: " + allMessages.size());
            System.out.println("Sent Messages: " + sentMessages.size());
            System.out.println("Stored Messages: " + storedMessages.size());
            System.out.println("Disregarded Messages: " + disregardedMessages.size());
            System.out.println("===========================================");

            if (!sentMessages.isEmpty()) {
                System.out.println("\nDETAILS OF SENT MESSAGES:");
                System.out.println("-------------------------------------------");

                for (Message message : sentMessages) {
                    System.out.println("Hash: " + message.getHash());
                    System.out.println("Recipient: " + message.getRecipient());
                    System.out.println("Message: \"" + message.getContent() + "\"");
                    System.out.println("Status: " + message.getFlag());
                    System.out.println("Time: " + message.getFormattedTimestamp());
                    System.out.println("-------------------------------------------");
                }
            }
        }

        /**
         * Helper method to get hash by content (for testing)
         *
         * @param content The message content to search for
         * @return The hash of the message or null if not found
         */
        public String getMessageHashByContent(String content) {
            for (Message message : allMessages) {
                if (message.getContent().equals(content)) {
                    return message.getHash();
                }
            }
            return null;
        }

        /**
         * Retrieve message by its hash
         *
         * @param hash The hash to search for
         * @return The message or null if not found
         */
        public Message getMessageByHash(String hash) {
            for (Message message : allMessages) {
                if (message.getHash().equals(hash)) {
                    return message;
                }
            }
            return null;
        }

        // Getters for unit testing access
        public List<Message> getAllMessages() { return allMessages; }
        public List<Message> getSentMessages() { return sentMessages; }
        public List<Message> getDisregardedMessages() { return disregardedMessages; }
        public List<Message> getStoredMessages() { return storedMessages; }
        public List<String> getMessageHashes() { return messageHashes; }
        public List<String> getMessageIds() { return messageIds; }
    }


package com.mycompany.chatapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ChatAppliication {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        String username;
        String password;
        String phone;

        System.out.println("REGISTRATION");

        System.out.print("Enter username: ");
        username = input.nextLine();

        if (login.checkUsername(username)) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not correctly formatted (must contain _ and be max 5 characters)");
            return;
        }

        System.out.print("Enter password: ");
        password = input.nextLine();

        if (login.checkPassword(password)) {
            System.out.println("Password successfully captured");
        } else {
            System.out.println("Password is not correctly formatted (must be at least 8 characters)");
            return;
        }

        System.out.print("Enter phone number (+27): ");
        phone = input.nextLine();

        if (login.checkPhone(phone)) {
            System.out.println("Phone number successfully captured");
        } else {
            System.out.println("Phone number is not correctly formatted");
            return;
        }

        System.out.println("\nLOGIN");

        System.out.print("Enter username: ");
        String loginUser = input.nextLine();

        System.out.print("Enter password: ");
        String loginPass = input.nextLine();

        if (login.loginUser(loginUser, loginPass, username, password)) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Login failed");
            return;
        }

        // Part 2

        List<Message> messages = new ArrayList<Message>();

        ChatApp outer = new ChatApp();

        int choice;

        do {

            System.out.println("\n1. Send Message");
            System.out.println("2. View Messages");
            System.out.println("3. Delete Message");
            System.out.println("4. Total Messages");
            System.out.println("5. Exit");

            System.out.print("Choose: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Receiver: ");
                    String receiver = input.nextLine();

                    System.out.print("Message: ");
                    String text = input.nextLine();

                    Message msg = outer.new Message(loginUser, receiver, text, null);

                    if (msg.isValidLength()) {

                        messages.add(msg);

                        System.out.println("Message sent!");
                        System.out.println("Hash: " + msg.getHash());

                    } else {

                        System.out.println("Message too long (max 250 characters)");
                    }

                    break;

                case 2:

                    if (messages.isEmpty()) {

                        System.out.println("No messages available");

                    } else {

                        for (Message message : messages) {

                            System.out.println("----------------------------");
                            System.out.println(message.getMessageDetails());
                        }
                    }

                    break;

                case 3:

                    System.out.print("Enter hash to delete: ");
                    String hash = input.nextLine();

                    boolean found = false;

                    for (int i = 0; i < messages.size(); i++) {

                        if (messages.get(i).getHash().equals(hash)) {

                            messages.remove(i);

                            System.out.println("Message deleted");
                            found = true;
                            break;
                        }
                    }

                    if (found == false) {
                        System.out.println("Message not found");
                    }

                    break;

                case 4:

                    System.out.println("Total messages: " + Message.getCount());

                    break;

                case 5:

                    System.out.println("Goodbye!");

                    break;

                default:

                    System.out.println("Invalid choice");
            }

        } while (choice != 5);

    }

    // Message class

    class Message {

        private static int messageCount = 0;

        private final String messageID;
        private final int messageNumber;
        private final String sender;
        private final String receiver;
        private final String messageText;
        private String messageHash;

        static int getCount() {
            return messageCount;
        }

        // Constructor
        public Message(String sender, String receiver, String messageText) {

            this.sender = sender;
            this.receiver = receiver;
            this.messageText = messageText;

            messageCount++;

            messageNumber = messageCount;

            messageID = "MSG" + messageNumber;

            createMessageHash();
        }

        // Create hash
        private void createMessageHash() {

            if (messageText.isEmpty()) {

                messageHash = "EMPTY";
                return;
            }

            String first = messageText.substring(0, 1).toUpperCase();
            String last = messageText.substring(messageText.length() - 1).toUpperCase();

            messageHash = messageID + ":" + messageNumber + ":" + first + last;
        }

        public String getHash() {
            return messageHash;
        }

        // Check message length
        public boolean isValidLength() {
            return messageText.length() <= 250;
        }

        // Display message details
        public String getMessageDetails() {

            return "Message ID: " + messageID + "\n" +
                   "Message Hash: " + messageHash + "\n" +
                   "Sender: " + sender + "\n" +
                   "Receiver: " + receiver + "\n" +
                   "Message: " + messageText;
        }

        public static int getTotalMessage() {
            return messageCount;
        }
    }
}
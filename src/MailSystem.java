import java.util.*;

class MailItem {
    private final String sender;
    private final String recipient;
    private final String message;

    public MailItem(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public String getSender() {
        return this.sender;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "From: " + this.sender + "\nTo: " + this.recipient + "\nMessage: " + this.message;
    }
}

class MailServer {
    private final List<MailItem> emails;
    private final List<String> registeredClients;

    public MailServer() {
        this.emails = new ArrayList<>();
        this.registeredClients = new ArrayList<>();
    }

    public boolean registerClient(String userId) {
        if (userId != null && !userId.trim().isEmpty() && !this.registeredClients.contains(userId)) {
            this.registeredClients.add(userId);
            return true;
        }
        return false;
    }

    public boolean isRegistered(String userId) {
        return userId != null && this.registeredClients.contains(userId);
    }

    public boolean sendMail(MailItem mail) {
        if (mail != null && this.isRegistered(mail.getSender()) && this.isRegistered(mail.getRecipient())) {
            this.emails.add(mail);
            return true;
        }
        return false;
    }

    public List<MailItem> getMail(String user) {
        List<MailItem> userEmails = new ArrayList<>();
        if (this.isRegistered(user)) {
            for (MailItem mail : this.emails) {
                if (mail.getRecipient().equals(user)) {
                    userEmails.add(mail);
                }
            }
        }
        return userEmails;
    }
}

class MailClient {
    private final MailServer server;
    private final String userId;

    public MailClient(MailServer server, String userId) {
        this.server = server;
        this.userId = userId;
        this.server.registerClient(userId); // auto-register
    }

    public boolean sendEmail(String recipient, String message) {
        if (recipient == null || message == null || recipient.trim().isEmpty() || message.trim().isEmpty()) {
            return false;
        }
        MailItem mail = new MailItem(this.userId, recipient, message);
        return this.server.sendMail(mail);
    }

    public List<MailItem> checkInbox() {
        return this.server.getMail(this.userId);
    }
}

public class MailSystem {
    public static void main(String[] args) {
        MailServer server = new MailServer();

        MailClient Abdullah = new MailClient(server, "Abdullah");
        MailClient Hashir = new MailClient(server, "Hashir");

        Abdullah.sendEmail("Hashir", "Hello Hashir!");
        Hashir.sendEmail("Abdullah", "Hi Abdullah, got your message!");

        System.out.println("Abdullah's Inbox:");
        for (MailItem mail : Abdullah.checkInbox()) {
            System.out.println(mail);
        }

        System.out.println("\nHashir's Inbox:");
        for (MailItem mail : Hashir.checkInbox()) {
            System.out.println(mail);
        }
    }
}

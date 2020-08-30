import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Enumeration;
import java.util.Scanner;

public class MyQueueBrowser {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = cf.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = (Destination) context.lookup("financeiro");

        // With browser we can iterate through all messages without consuming them
        QueueBrowser browser = session.createBrowser((Queue) queue);

        Enumeration browserEnumeration = browser.getEnumeration();
        while (browserEnumeration.hasMoreElements()) {
            TextMessage msg = (TextMessage) browserEnumeration.nextElement();
            System.out.println("Message: " + msg.getText());
        }


        new Scanner(System.in).nextLine();

        browser.close();
        session.close();
        connection.close();
        context.close();
    }
}

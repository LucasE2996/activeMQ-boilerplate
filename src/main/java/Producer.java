import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class Producer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = cf.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = (Destination) context.lookup("financeiro");

        MessageProducer producer = session.createProducer(queue);

        // Send 1000 messages just for testing purposes
        for (int i = 0; i < 1000; i++) {
            Message message = session.createTextMessage("Message number " + i);
            producer.send(message);
        }

        // new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}

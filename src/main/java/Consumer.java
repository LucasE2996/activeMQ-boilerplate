import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class Consumer {

    public static void main(String[] args) throws Exception {
        // The file jndi.properties could be written as java code as well:
//        Properties properties = new Properties();
//        properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
//
//        properties.setProperty("java.naming.provider.url", "tcp://192.168.0.94:61616");
//        properties.setProperty("queue.financeiro", "fila.financeiro");
//        InitialContext context = new InitialContext(properties);
        InitialContext context = new InitialContext();

        ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = cf.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = (Destination) context.lookup("financeiro");
        MessageConsumer messageConsumer = session.createConsumer(queue);

        messageConsumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}

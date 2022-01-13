package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFila {

	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();

		conexao.start();
		
		Session session = conexao.createSession(true, Session.SESSION_TRANSACTED);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					//message.acknowledge();
					System.out.println(textMessage.getText());
					session.rollback();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	

		new Scanner(System.in).nextLine();

		session.close();
		conexao.close();    
		context.close();
	}

}

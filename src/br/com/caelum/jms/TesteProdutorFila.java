package br.com.caelum.jms;

import java.util.Iterator;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection();

		conexao.start();
		
		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer producer =  session.createProducer(fila);

		Message message = session.createTextMessage("<pedido><id>13</id></pedido>");
		producer.send(message);
		 
		//new Scanner(System.in).nextLine();

		session.close();
		conexao.close();    
		context.close();
	}

}

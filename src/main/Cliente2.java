package main;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Cliente2 {
public static void main(String[] args) {
		
	try {
		
		
		// Mensajes a enviar
		String[] mensajes = {
			    "Hola, ¿cómo estás?",
			    "Este es un nuevo mensaje",
			    "¿Qué opinas de esto?",
			    "Otra frase interesante",
			    "123abc456",
			    "¡Hola mundo!",
			    "La vida es como una bicicleta, para mantener el equilibrio, debes seguir adelante.",
			    "El conocimiento es poder.",
			    "Nunca es tarde para aprender algo nuevo.",
			    "La paciencia es amarga, pero sus frutos son dulces.",
			    "La imaginación es más importante que el conocimiento.",
			    "Si puedes soñarlo, puedes lograrlo.",
			    "La creatividad es contagiosa, pásala.",
			    "No hay atajos para ningún lugar que valga la pena.",
			    "La mejor manera de predecir el futuro es crearlo.",
			    "Cada día es una nueva oportunidad para cambiar tu vida.",
			    "El éxito es la suma de pequeños esfuerzos repetidos día tras día.",
			    "La vida es demasiado corta para no ser feliz.",
			    "Haz hoy lo que otros no quieren, haz mañana lo que otros no pueden.",
			    "No te preocupes por los fracasos, preocúpate por las oportunidades que pierdes cuando ni siquiera lo intentas."
			};

		
		Socket clienteSocket = null;
		
		for (String string : mensajes) {
			System.out.println("Creando socket cliente.");
			clienteSocket = new Socket();
			
			System.out.println("Estableciendo conexion...");
			String servidor = "localhost";
			int puerto = 1111;
			InetSocketAddress address = new InetSocketAddress(servidor,puerto);
			
			System.out.println("Conectando socket con servidor: " + servidor + ":" + puerto);
			clienteSocket.connect(address);
			
			InputStream inputStream = clienteSocket.getInputStream();
			OutputStream outputStream = clienteSocket.getOutputStream();
			
			System.out.println("Enviando mensaje...");
			outputStream.write(string.getBytes());
			
			try {
				System.out.println("Esperando 2 segundos...");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
		System.out.println("Cerrando el socket cliente");
		
		clienteSocket.close();
		
		System.out.println("Terminado");

		
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
}

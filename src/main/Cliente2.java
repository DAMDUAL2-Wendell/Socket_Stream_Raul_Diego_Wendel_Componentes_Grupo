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
				"¿Que te pasa?",
				"Esto es un mensaje de prueba",
				"¿Otro mensaje?",
				"Otro mensaje",
				"s879889asdf"
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
			
		}
		
		
		System.out.println("Cerrando el socket cliente");
		
		clienteSocket.close();
		
		System.out.println("Terminado");

		
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
}

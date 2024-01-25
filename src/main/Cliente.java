package main;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Cliente {
public static void main(String[] args) {
		
	try {
		System.out.println("Creando socket cliente.");
		Socket clienteSocket = new Socket();
		
		System.out.println("Estableciendo conexion...");
		String servidor = "localhost";
		int puerto = 1111;
		InetSocketAddress address = new InetSocketAddress(servidor,puerto);
		
		System.out.println("Conectando socket con servidor: " + servidor + ":" + puerto);
		clienteSocket.connect(address);
		
		InputStream inputStream = clienteSocket.getInputStream();
		OutputStream outputStream = clienteSocket.getOutputStream();
		
		System.out.println("Enviando mensaje...");
		
		// Mensajes a enviar
		String[] mensajes = {
				"¿Cómo te llamas?",
				"¿Cuántas líneas de código tienes?",
				"¿Otro mensaje?",
				"Otro mensaje"
				};
		
		Random rdn = new Random();
		
	
		String mensaje = mensajes[rdn.nextInt(0,4)];
		
		outputStream.write(mensaje.getBytes());
		
		System.out.println("Mensaje enviado: " + mensaje);
		
		System.out.println("Cerrando el socket cliente");
		
		clienteSocket.close();
		
		System.out.println("Terminado");

		
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
}

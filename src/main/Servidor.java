package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Servidor {
	
	public static void main(String[] args) {
		
		String[] mensajes = {
				"¿Cómo te llamas?",
				"¿Cuántas líneas de código tienes?",
				};
		
		try {
			System.out.println("Creando socket servidor");
			
			ServerSocket serverSocket = new ServerSocket();
			
			System.out.println("Realizando bind");
			
			InetSocketAddress addr = new InetSocketAddress("localhost",1111);
			serverSocket.bind(addr);
						
			// Bucle infinito para ejecutar varias veces cliente y ver diferentes respuestas
			while(true) {
				
				System.out.println("Creando nuevo socket");
				System.out.println("Aceptando conexiones");
				Socket nuevoSocket = serverSocket.accept();
				
				System.out.println("Conexion recibida");
				
				InputStream is = nuevoSocket.getInputStream();
				OutputStream os = nuevoSocket.getOutputStream();
				
				byte[] mensaje = new byte[255];
				
				is.read(mensaje);
				
				String mensajeRecibido = new String(mensaje).trim();
				int signoInterrogacion = mensajeRecibido.indexOf("¿");
				
				String mensajeMostrar = "";
				
				if(signoInterrogacion == -1) {
					mensajeMostrar = "No he entendido la pregunta";
				}else {
					mensajeMostrar = mensajeRecibido.substring(signoInterrogacion, mensajeRecibido.length());
					if(mensajeRecibido.equals(mensajes[0])) {
						mensajeMostrar = "Me llamo Práctica Socket Stream";
					}else if (mensajeRecibido.equals(mensajes[1])) {
						mensajeMostrar = "Tengo 82 líneas de código";
					}else {
						mensajeMostrar = "No he entendido la pregunta";
					}
				}
				
				
				System.out.println("Mensaje recibido: " + mensajeRecibido);
				
				System.out.println("Respuesta: " + mensajeMostrar);
				
				System.out.println("Cerrando socket");
				//nuevoSocket.close();
				
				System.out.println("Cerrando socket servidor");
				//serverSocket.close();
				
				System.out.println("Terminado");
			}
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}

}

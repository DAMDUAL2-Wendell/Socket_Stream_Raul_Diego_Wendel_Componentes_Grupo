package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Servidor {

    public static void main(String[] args) {

        // Definición de mensajes válidos que se deben recibir.
        String[] mensajes = {
                "¿Cómo te llamas?",
                "¿Cuántas líneas de código tienes?",
        };

        // Booleano establecido en false mientras no pasen 5 segundos sin recibir ningún mensaje
        boolean limiteTiempo = false;

        // Declaración de sockets
        ServerSocket serverSocket = null;
        final ServerSocket[] finalServerSocket = { null }; // Declarado como final para permitir su uso en la lambda del objeto Future
        Socket nuevoSocket = null;

        try {

            System.out.println("Creando socket servidor");
            finalServerSocket[0] = new ServerSocket();


            System.out.println("Realizando bind");
            InetSocketAddress addr = new InetSocketAddress("localhost", 1111);
            finalServerSocket[0].bind(addr);

            // Inicialización de variables
            int contador = 0;
            int tiempoLimite = 5000;

            // Creación del pool de hilos
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            // Bucle principal
            while (!limiteTiempo) {
                contador++;

                // Espera de conexión
                System.out.println("Esperando conexión");

                // Uso de Future para manejar la espera de conexión con timeout
                Future<Socket> future = executorService.submit(() -> {
                    try {
                        return finalServerSocket[0].accept();
                    } catch (IOException e) {
                        return null;
                    }
                });

                try {
                    // Obtención del nuevo socket con timeout
                    nuevoSocket = future.get(tiempoLimite, java.util.concurrent.TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    // Manejo de excepción si han pasado 5 segundos sin recibir conexión
                    System.out.println("Han pasado 5 segundos sin recibir conexión.");
                    limiteTiempo = true;
                    break;
                }
                
                // Si no hemos sobrepasado el límite de tiempo sin recibir ningún mensaje ejecutamos este código
                if (!limiteTiempo) {
                    System.out.println("Conexion recibida");

                    try {
                        // Configuración de tiempo de espera límite
                        nuevoSocket.setSoTimeout(tiempoLimite);

                        // Obtención de flujos de entrada y salida del socket
                        InputStream is = nuevoSocket.getInputStream();
                        OutputStream os = nuevoSocket.getOutputStream();

                        // Leemos el mensaje enviado por el cliente
                        byte[] mensaje = new byte[255];
                        
                        int bytesRead = 0;
                        try {
                        	bytesRead = is.read(mensaje);
						} catch (Exception e) {
							System.out.println("Mensaje vacio");
						}

                        if (bytesRead > 0) {
                            // Si los bytes leidos son > 0, asignamos al mensaje recibido el contenido desde el byte 0 hasta el byte leido, y hacemos un trim para eliminar posibles espacios.
                            String mensajeRecibido = new String(mensaje, 0, bytesRead).trim();
                            int signoInterrogacion = mensajeRecibido.indexOf("¿");
                            String mensajeMostrar = "";

                            // Evaluar contenido del mensaje y preparar la respuesta
                            if (signoInterrogacion == -1) {
                                mensajeMostrar = "No he entendido la pregunta";
                            } else {
                                mensajeMostrar = mensajeRecibido.substring(signoInterrogacion, mensajeRecibido.length());
                                if (mensajeRecibido.equals(mensajes[0])) {
                                    mensajeMostrar = "Me llamo Práctica Socket Stream";
                                } else if (mensajeRecibido.equals(mensajes[1])) {
                                    mensajeMostrar = "Tengo 82 líneas de código";
                                } else {
                                    mensajeMostrar = "No he entendido la pregunta";
                                }
                            }

                            System.out.println("Mensaje " + contador + " recibido: " + mensajeRecibido);
                            System.out.println("Respuesta: " + mensajeMostrar);
                            System.out.println("*******************************************");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        // Cerrar el socket solo si no hemos sobrepasado el tiempo límite
                        try {
                            if (nuevoSocket != null && !nuevoSocket.isClosed()) {
                                System.out.println("Cerrando nuevoSocket");
                                nuevoSocket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            
            // Apagar el ExecutorService después de salir del bucle principal
            executorService.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierre de sockets al finalizar el programa
            try {

                if (finalServerSocket[0] != null && !finalServerSocket[0].isClosed()) {
                    System.out.println("Cerrando socket servidor");
                    finalServerSocket[0].close();
                }
                System.out.println("Terminado");
            } catch (IOException e) {
                System.out.println("Fallo al finalizar la ejecución del programa.");
            }
        }
    }
}
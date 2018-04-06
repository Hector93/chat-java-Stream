package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {
        String token = "exit";
        String mensaje;
        boolean chatActivo;
        boolean aceptaConeccion;
        int availableTeclado;
        int availableIn;

	    try(ServerSocket server = new ServerSocket(2121)){
	        aceptaConeccion = true;

            InputStream teclado = new BufferedInputStream(System.in);

	        while (aceptaConeccion){
	            try(Socket connection = server.accept()){
                    InputStream in = connection.getInputStream();
                    OutputStream out = connection.getOutputStream();
                    chatActivo = true;

                    while (chatActivo) {

                        if((availableTeclado = teclado.available()) > 0){
                            //System.out.println("leo datos");
                            byte []aux = new byte[availableTeclado];
                            teclado.read(aux,0,availableTeclado);
                            out.write(aux);
                            out.flush();
                            //System.out.println("termino leer: " + new String(aux));
                        }

                        if((availableIn = in.available()) > 0){
                            //System.out.println("llegan datos");
                            byte []aux = new byte[availableIn];
                            in.read(aux,0,availableIn);
                            mensaje = new String(aux);
                            if(mensaje.contains(token)){
                                System.out.println("termina chat");
                                chatActivo = false;
                                aceptaConeccion = false;
                                out.write("exit\r\n".getBytes());
                                out.flush();
                                connection.close();
                            }else{
                                System.out.println(mensaje + "\r\n");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

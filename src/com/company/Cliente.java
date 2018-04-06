package com.company;

import java.io.*;
import java.net.Socket;


public class Cliente {
    public static void main(String[] args){
        String token = "exit";
        boolean chatActivo;
        String mensaje;
        int availableTeclado;
        int availableIn;

        try {
            Socket socket = new Socket("127.0.0.1",2121);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            chatActivo = true;
            InputStream teclado = new BufferedInputStream(System.in);

            while (chatActivo){
                if((availableTeclado = teclado.available()) > 0){
                    //System.out.println("leo datos");
                    byte []aux = new byte[availableTeclado];
                    teclado.read(aux,0,availableTeclado);
                    out.write(aux);

                    out.flush();
                    //System.out.println("termino leer");
                }

                if((availableIn = in.available()) > 0){
                    //System.out.println("llegan datos");
                    byte []aux = new byte[availableIn];
                    in.read(aux,0,availableIn);
                    mensaje = new String(aux);
                    if(mensaje.contains(token)){
                        System.out.println("TERMINA CHAT");
                        chatActivo = false;
                        socket.close();
                    }else{
                        System.out.println(mensaje);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


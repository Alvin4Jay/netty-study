package nia.chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * PlainOioServer
 *
 * @author xuanjian
 */
public class PlainOioServer {

    public void serve(int port) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(port);

        try {
            for (; ; ) {
                final Socket clientSocket = serverSocket.accept();

                System.out.println("Accept connection from " + clientSocket);

                new Thread(() -> {
                    OutputStream outputStream;
                    try {
                        outputStream = clientSocket.getOutputStream();
                        outputStream.write("Hi\r\n".getBytes(Charset.forName("UTF-8")));
                        outputStream.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

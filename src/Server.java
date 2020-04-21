import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

  new Thread(new Runnable() {
      @Override
      public void run() {
          while (true) {
              String str = null;
              try {
                  str = in.readUTF();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              if (str.equals("/end")) {
                  break;
              }
              try {
                  out.writeUTF("Сообщение от Клиента: " + str);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
  }
            ).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    while (true) {
                        String str = null;

                        try {
                            str = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (str.equals("/end")) {
                            break;
                        }
                        try {
                            out.writeUTF("Сообщение от сервера: " + str);
                            int i = 3+5;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            ).start();





        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    }
}
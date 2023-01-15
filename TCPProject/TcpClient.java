package TCPProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) throws IOException {

        OutputStream outputStream = null;
        Socket socket=null;
        while (true) {

//        3.发送数据；
            System.out.println("msg you want to send to server: ");
            Scanner scanner = new Scanner(System.in);
            String context = scanner.nextLine();
            if("666".equals(context)){
                break;
            }
            //        1.创建发送端Socket对象（创建连接）--- three way handshake
            socket = new Socket(InetAddress.getByName("mayikt.server.com"), 8090);
            //        2.获取输出流对象；
            outputStream = socket.getOutputStream();
            outputStream.write(context.getBytes());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            System.out.println("client's response: "+new String(bytes,0,read));

        }

//        4.释放资源；
        outputStream.close();
        socket.close();
    }
}

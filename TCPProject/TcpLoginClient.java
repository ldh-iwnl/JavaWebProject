package TCPProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TcpLoginClient {
    public static void main(String[] args) throws IOException {


        while (true) {

//        3.发送数据；
            Scanner scanner = new Scanner(System.in);
            System.out.println("Your username: ");
            String userName = scanner.nextLine();
            System.out.println("Your password: ");
            String pwd = scanner.nextLine();

            //        1.创建发送端Socket对象（创建连接）--- three way handshake
            Socket socket = new Socket(InetAddress.getByName("mayikt.server.com"), 8090);
            //        2.获取输出流对象；
            OutputStream outputStream = socket.getOutputStream();
            String reqText = "username="+userName+"&userPwd="+pwd;
            outputStream.write(reqText.getBytes());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            String resp = new String(bytes,0,read);
            if("ok".equals(resp)){
                System.out.println("login success!");
            }else{
                System.out.println("fail to login");
            }
            outputStream.close();
            socket.close();
        }

//        4.释放资源；

    }
}

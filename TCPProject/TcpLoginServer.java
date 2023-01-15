package TCPProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class TcpLoginServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8090);
        System.out.println("listening for client's msg...");
        //        2.监听（阻塞:如果建立连接失败，程序会一直阻塞，不往下执行；
        while(true){
            //        1.创建接收端Socket对象；
            Socket accept = serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        //        3.获取输入流对象；
                        InputStream inputStream = accept.getInputStream();
//        4.获取数据；
                        byte[] bytes = new byte[1024];
                        int len = inputStream.read(bytes);
                        //intercept username and pwd:
                        String text =new String(bytes,0,len);
                        String username = text.split("&")[0].split("=")[1];
                        String pwd = text.split("&")[1].split("=")[1];
                        System.out.println("msg from client: "+new String(bytes,0,len));
                        OutputStream outputStream = accept.getOutputStream();
                        if("mayikt".equals(username) && "123456".equals(pwd)){
                            outputStream.write("ok".getBytes());
                        }else{
                            outputStream.write("fail".getBytes());
                        }


//        5.输出数据:
                        inputStream.close();
                        accept.close();
                        outputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();



        }
//        6.释放资源；
//        serverSocket.close();
//        accept.close();
    }
}

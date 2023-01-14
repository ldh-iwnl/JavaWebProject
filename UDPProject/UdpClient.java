package UDPProject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) throws IOException {
        //client send msg to server
        DatagramSocket datagramSocket = new DatagramSocket();

        while (true){
            System.out.println("Client: Please enter msg: ");
            Scanner scanner = new Scanner(System.in);
            String context = scanner.nextLine();
            if("666".equals(context)){
                System.out.println("exiting...");
                break;
            }
            byte[] bytes = context.getBytes();
            //create socket sender
            //encapsulate the data
            DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length,
                    InetAddress.getByName("mayikt.server.com"), 8080);
            datagramSocket.send(datagramPacket);
            System.out.println("sending msg complete...");
        }
        // close the connection
        datagramSocket.close();
    }
}

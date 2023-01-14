package UDPProject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpSever {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8080);

        while(true){
            byte[] result = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(result, result.length);
            datagramSocket.receive(datagramPacket);
            System.out.println("接受信息: "+ new String(datagramPacket.getData(), datagramPacket.getOffset(),
                    datagramPacket.getLength()));
        }
    }
}

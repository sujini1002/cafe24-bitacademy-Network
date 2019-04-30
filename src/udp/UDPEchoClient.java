package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {
	public static final String SERVER_IP = "192.168.1.26";
	public static final int PORT = 8008;
	
	public static void main(String[] args) {
		Scanner scanner = null;
		DatagramSocket socket= null;
		try {
			
			//1. 스캐너 생성(표준입력 연결)
			scanner = new Scanner(System.in);
			//2. 소켓 생성
			socket = new DatagramSocket();
			
			
			while(true) {
				//3.데이터 쓰기
				String line = scanner.nextLine();
				byte[] sendData = line.getBytes("utf-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));
				socket.send(sendPacket);
				//2. 데이터 읽기
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				
				String message = new String(receivePacket.getData(),0,receivePacket.getLength(),"utf-8");
				
				//5.콘솔출력
				System.out.println("<<"+message);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(scanner != null)scanner.close();
			if(socket != null && socket.isClosed()==false)socket.close();
		}
	}//end main
	

}

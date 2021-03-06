package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	
	public static final int PORT = 7770;
	public static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
				//ServerSocket은 따로 없다.
				DatagramSocket socket = null;
				
				try {
					//1. 소캣 생성
					socket = new DatagramSocket(PORT);
					
					while(true) {
						//2. 데이터 수신
						DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
						socket.receive(receivePacket);
						
						byte[] data = receivePacket.getData();
						int length = receivePacket.getLength();
						String message = new String(data,0,length,"utf-8");
						
						System.out.println("[server] received:"+message);
						
						if("".equals(message)) {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
							message = format.format(new Date());
						}
						
						//3.데이터 전송
						byte[] sendData = message.getBytes("utf-8");
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),receivePacket.getPort());
						socket.send(sendPacket);
					}
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if(socket != null && socket.isClosed() == false) {
						socket.close();
					}
				}
	}//end main

}

package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	
	private static final String SERVER_IP = "192.168.1.26";
	private static final int SERVER_PORT = 6000;
	
	public static void main(String[] args) {
		Socket socket= null;
		try {
			
			//1. 소켓 생성
			socket = new Socket();
			//2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			System.out.println("[client[ connected");
			//3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			//4.쓰기
			String data = "Hello World\n";
			os.write(data.getBytes("utf-8"));
			
			//5.읽기
			byte[] buffer = new byte[256];
			int readByteCnt = is.read(buffer);//blocking
			
			if(readByteCnt == -1) {
				System.out.println("[client] closed by Server");
			}
			
			data = new String(buffer,0,readByteCnt,"utf-8");
			System.out.println("[client] recevied : "+ data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(socket != null && socket.isClosed()==false)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerRecevieThread extends Thread {
	private Socket socket;
	
	public EchoServerRecevieThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		
		EchoServer.log("connected bt clien["+remoteHostAddress + "," + remotePort + "]");
		
		try {
		//4. IOStream 받아오기 
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
		//개행이 나타날때 마다 autoflush 한다.
		PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"),true);
		
		
		while(true) {
				//5.데이터읽기
				String data = br.readLine();
				if(data == null) {
					EchoServer.log("closed by client");
					break;
				}
				
				EchoServer.log("received :"+data);
				
				//6.데이터 쓰기
				pr.println(data);
				
			}//end while
		}catch(SocketException e) {
			//갑자기 끊어진 오류
			System.out.println("[server] sudden closed by client");
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(socket != null && !socket.isClosed())
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

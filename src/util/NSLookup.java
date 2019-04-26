package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		
		BufferedReader br = null;
		
		try {
			
			while(true) {
				System.out.print(">");
				
				br = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
				
				/* Scanner sc = new Scanner(System.in); */
				//키보드에서 입력한 내용을 hostname에 저장
				String hostname = null;
				if((hostname=br.readLine())!=null) {
					if(hostname.equals("exit"))
						break;
					
					InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
					for(InetAddress addr : inetAddresses) {
						System.out.println(hostname + ":" + addr.getHostAddress());
					}
				}
				
				
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	

}

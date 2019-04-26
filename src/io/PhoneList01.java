package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PhoneList01 {

	public static void main(String[] args) {
		
		BufferedReader br = null;
		
		try {
			//기반스트림
			//FileInputStream fs = new FileInputStream("phone.txt");
			
			//보조스트림1(byte -> char)
			//InputStreamReader isr = new InputStreamReader(new FileInputStream("phone.txt"),"utf-8");
			br = new BufferedReader(new InputStreamReader(new FileInputStream("phone.txt"),"utf-8"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line,"\t ");
				
				int index = 0;
				while(st.hasMoreElements()) {
					
					String token = st.nextToken();
					
					System.out.print(token);
					//이름
					if(index == 0) {
						System.out.print(":");
					}else if(index == 1){//전화번호1
						System.out.print("-");
					}else if(index == 2){//전화번호2
						System.out.print("-");
					}
					index++;
				}
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if(br != null)
						br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}

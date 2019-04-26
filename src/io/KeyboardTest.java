package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class KeyboardTest {

	public static void main(String[] args) {
		//기반스트림(표준입력,키보드,System.in,주스트림) - JVM이 처리
		
		//보조스트림2
		BufferedReader br = null;
		
		try {
			//보조스트림1
			//byte|byte|byte -> char
			InputStreamReader isr = new InputStreamReader(System.in,"utf-8");
			
			//보조스트림2
			// char|char|char|\n -> "char1char2char3" => String
			br = new BufferedReader(isr);
			
			//read
			String line = null;
			while((line = br.readLine())!=null) {
				if("exit".equals(line))
					break;
				System.out.println(">>"+line);
			}
			
		} catch (IOException e) {
			System.out.println("error:"+e);
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

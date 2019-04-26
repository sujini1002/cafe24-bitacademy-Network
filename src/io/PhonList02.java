package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PhonList02 {

	public static void main(String[] args) {
		
		Scanner scanner = null;
		//File file = new File("phone.txt");
		
		try {
			
			scanner = new Scanner(new File("phone.txt"));
			
			while(scanner.hasNextLine()) {
				String name = scanner.next();
				String phone1 = scanner.next();
				String phone2 = scanner.next();
				String phone3 = scanner.next();
				
				System.out.println(name + ":"+ phone1 + "-" + phone2 +"-" + phone3);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}

}

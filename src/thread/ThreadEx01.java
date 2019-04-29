package thread;

public class ThreadEx01 {

	public static void main(String[] args) {
//		for(int i=0;i<=10;i++) {
//			System.out.print(i);
//		}
		//스레드 객체 생성
		Thread digitThread = new DigitThread();
		digitThread.start();
		
		for(char c='a';c<='z';c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

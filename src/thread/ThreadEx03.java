package thread;

public class ThreadEx03 {

	public static void main(String[] args) {
		Thread thread1 = new DigitThread();
		Thread thread2 = new AlphabetThread();
		//Runnable 인터페이스를 구현한 클래스가 들어옴
		Thread thread3 = new Thread(new UppercaseAlphabetRunnableImpl());
		
		thread1.start();
		thread2.start();
		thread3.start();
	}

}

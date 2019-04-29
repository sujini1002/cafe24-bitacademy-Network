package thread;

public class UppercaseAlphabet extends Thread{
	//쓰레드로 사용할 때 수정말고 확장하기 
	public void print() {
		for(char c = 'A';c<='Z';c++) {
			System.out.print(c);
		}
	}
}

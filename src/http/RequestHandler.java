package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private static final String DOCUMENT_ROOT = "./webapp";
	private Socket socket;
	
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			// get IOStream -- 이미지,파일,텍스트 모두 보내야하기 때문에 bufferedWriter는 사용하지 않음
			//OutputStream outputStream = socket.getOutputStream();

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );
			
			//get IOEStream
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			String request = null;
			while(true) {
				String line = br.readLine();
				//브라우저가 연결을 끊으면
				if(line == null)break;
				
				//request 헤더만 읽음
				if("".equals(line))break;
				
				//헤더의 첫번째 라인만 처리
				if(request==null)request = line;
			}
		
			//header의 첫줄 출력
			//consoleLog("recevied:"+ request);
			
			String[] tokens = request.split(" ");
			
			if("GET".contentEquals(tokens[0])) {
				//GET(SELECT)
				consoleLog("request :"+tokens[1]);
				responseStaticResource(os,tokens[1],tokens[2]);
			}else{
				//POST(INSERT),DELETE(DELETE),PUT(UPDATE),HEAD(헤드정보조회),CONNECT
				//위와 같은 HTTP Method는 무시한다.
				
				//응답예시
				//HTTP/1.1 400 Bad Request\r\n
				//Content-Type:Text/html; charset=utf-8\r\n
				//\r\n
				//HTML 에러 문서 (./webapp/error/400.html)
				response400Error(os);
			}
			
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
//			os.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
//			os.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );//여기까지가 Header이다.
//			os.write( "\r\n".getBytes() );//Header와 Body의 구분자
//			os.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );

		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	

	public void responseStaticResource(OutputStream os, String url, String protocol) throws IOException {
		//welcome 파일 설정
		if("/".equals(url)) {
			url = "/index.html";
		}
		
		File file = new File(DOCUMENT_ROOT+url);
		if(file.exists()==false) {
			response404Error(os,protocol);
			return ;
		}
		//nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		//응답
		os.write((protocol + "200 OK\r\n").getBytes("UTF-8"));
		os.write( ("Content-Type:"+contentType+ "; charset=utf-8\r\n").getBytes( "UTF-8" ) );//여기까지가 Header이다.
		os.write( "\r\n".getBytes() );//Header와 Body의 구분자
		os.write( body);
	}//end responseStaticResource

	
	public void response404Error(OutputStream os, String protocol) throws UnsupportedEncodingException, IOException {
		File file1 = new File(DOCUMENT_ROOT+"/error/404.html");
		String contentType = Files.probeContentType(file1.toPath());
		os.write("HTTP/1.1 404 File Not Found\r\n".getBytes("UTF-8"));
		os.write(("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes("UTF-8"));// 여기까지가 Header이다.
		os.write("\r\n".getBytes());// Header와 Body의 구분자
		//클라이언트(웹브라우저)에게 보낼때는 반드시 byte로 보내야 한다. OutputStream으로 write할 때 문자열,파일,이미지도 모두 Byte로 변환한다.Files.readAllBytes()는 파일을 바이트로 변환 한다.
		byte[] body = Files.readAllBytes(file1.toPath());
		os.write(body);
		
	}
	public void response400Error(OutputStream os) throws IOException {
		File file = new File(DOCUMENT_ROOT+"/error/400.html");
		String contentType = Files.probeContentType(file.toPath());
		//응답
		os.write("HTTP/1.1 400 Bad Request\r\n".getBytes("UTF-8"));
		os.write(("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes("UTF-8"));// 여기까지가 Header이다.
		os.write("\r\n".getBytes());// Header와 Body의 구분자
		byte[] body = Files.readAllBytes(file.toPath());
		os.write(body);
	}

	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
}

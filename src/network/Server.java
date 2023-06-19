package network;

import gui.SearchPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//TODO : 포트당 2명씩 입장하도록 구현완료. ServerSocketThread 에서 name을 자동으로 받을수있는 방법 모색중.
public class Server {
    ServerSocket serverSocket;
    SearchPanel searchPanel;
    Socket socket;
    List<Thread> list;		// ServerSocketThread 객체 저장
    List<Socket> socketList;
    LinkedList<Thread> quelist;

    public Server() {
        list = new ArrayList<Thread>();
        System.out.println("서버가 시작되었습니다.");
    }

    public void giveAndTake(int port) {
        try {
            serverSocket = new ServerSocket(port);		// 소켓 접속 대기
            serverSocket.setReuseAddress(true); 		// ServerSocket이 port를 바로 다시 사용한다 설정(port를 잡고있음)

                while (true) {
                    socket = serverSocket.accept();// accept -> 1. 소켓 접속 대기 2. 소켓 접속 허락
                    //socketList.add(socket);
                    //if(socketList.size() > 2) throw new Exception("채팅방 인원초과");
                    ServerSocketThread thread = new ServerSocketThread(this, socket);    // this -> ChatServer 자신
                    addClient(thread);        // 리스트에 쓰레드 객체 저장
                    thread.start();
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(searchPanel,"이미 문의중인 고객이 있습니다! 잠시 뒤에 다시 시도해주세요.");
            e.printStackTrace();
        }
    }

    public void que(int port) {
        try {
            serverSocket = new ServerSocket(port);		// 소켓 접속 대기
            serverSocket.setReuseAddress(true); 		// ServerSocket이 port를 바로 다시 사용한다 설정(port를 잡고있음)

            while(true) {
                socket = serverSocket.accept();// accept -> 1. 소켓 접속 대기 2. 소켓 접속 허락
                QueSocketThread thread = new QueSocketThread(this, socket);	// this -> ChatServer 자신
                thread.start();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    // synchronized : 쓰레드들이 공유데이터를 함께 사용하지 못하도록 하는 것
    // 클라이언트가 입장 시 호출되며, 리스트에 클라이언트 담당 쓰레드 저장
    private synchronized void addClient(ServerSocketThread thread) {
        // 리스트에 ServerSocketThread 객체 저장
        list.add(thread);
        System.out.println("Client 1명 입장. 총 " + list.size() + "명");
    }
    // 클라이언트가 퇴장 시 호출되며, 리스트에 클라이언트 담당 쓰레드 제거
    public synchronized void removeClient(Thread thread) {
        list.remove(thread);
        System.out.println("Client 1명 퇴장. 총 " + list.size() + "명");
    }
    // 모든 클라이언트에게 채팅 내용 전달
    public synchronized void broadCasting(String str) {
        for(int i = 0; i < list.size(); i++) {
            ServerSocketThread thread = (ServerSocketThread)list.get(i);
            thread.sendMessage(str);
        }
    }

    private synchronized void addQue(QueSocketThread thread){
        quelist.add(thread);
        System.out.println("Client 1명 대기중. 총 " + quelist.size()+"명");
    }

    public synchronized void removeQue(QueSocketThread thread){
        quelist.remove(thread);
        System.out.println("Client 1명 퇴장. 총 " + quelist.size() + "명");
    }

    public synchronized void broadCast(String str){
        for(int i =0; i< quelist.size(); i++){
            QueSocketThread thread = (QueSocketThread) quelist.get(i);
            thread.sendMessage(str);
        }
    }
}
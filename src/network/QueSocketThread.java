package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class QueSocketThread extends Thread{
    Socket socket;
    Server server;
    String threadName;
    PrintWriter out;

    public QueSocketThread(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        threadName = super.getName();
        System.out.println("Thread Name : " + threadName);
    }

    public void sendMessage(String str){out.println(str);}

    @Override
    public void run(){
        try{
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            while(true){
                server.quelist.wait(10000);
                server.quelist.pop();
                server.broadCast("현재 대기번호는 " + server.quelist.indexOf(threadName) + "번 입니다.");
            }
        }catch (IOException | InterruptedException e){
            server.removeQue(this);
        }
    }
}

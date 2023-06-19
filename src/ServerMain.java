import gui.LoginPanel;
import network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain{
    public static void main(String[] args){
        Server chatServer = new Server();
        Server queServer = new Server();

        chatServer.giveAndTake(5000);
    }
}

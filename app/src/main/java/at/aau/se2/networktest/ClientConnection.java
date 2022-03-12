package at.aau.se2.networktest;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;

public class ClientConnection extends Observable implements Runnable {
    private String toServer = "";
    private String fromServer = "null";

    @Override
    public void run() {
        try {
            Socket socket = new Socket("se2-isys.aau.at", 53212);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dos.writeBytes(toServer+"\n");
            fromServer = bf.readLine();
            socket.close();
            setChanged();
            notifyObservers();
        } catch (Exception e) {
            fromServer = "connection error: " + e;
            setChanged();
            notifyObservers();
        }
    }

    public void setToServer(String text) {
        toServer = text;
    }

    public String getFromServer() {
        return fromServer;
    }
}

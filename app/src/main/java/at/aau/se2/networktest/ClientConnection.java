package at.aau.se2.networktest;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
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

    public static String modify(String toModify) {
        int[] digits = new int[toModify.length()];
        for (int i = 0; i < toModify.length(); i++) {
            digits[i] = toModify.charAt(i) -48;
        }
        Arrays.sort(digits);
        StringBuilder modified = new StringBuilder();
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 2 && digits[i] != 3 && digits[i] != 5 && digits[i] != 7) modified.append(digits[i]);
        }
        return modified.toString();
    }
}

package com.myefood.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class RequestFromServer<T> extends Thread {

    private final Handler handler;
    private final ArrayList<T> resultList;
    private final Object requestData;

    public RequestFromServer(Handler handler, ArrayList<T> resultList, Object requestData) {
        this.handler = handler;
        this.resultList = resultList;
        this.requestData = requestData;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("192.168.68.113", 5012);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Στείλε την εντολή ή δεδομένα
            out.writeObject("Lat::38.01");
            out.flush();
            out.writeObject("Lon::23.76");
            out.flush();
            out.writeObject(requestData);
            out.flush();

            // Δέξου απάντηση: μια λίστα από αντικείμενα τύπου T
            Object received = in.readObject();

            if (received instanceof ArrayList) {
                resultList.clear();
                resultList.addAll((ArrayList<T>) received);

                Log.d("SERVER", "Λήφθηκαν " + resultList.size() + " αντικείμενα");
                handler.sendEmptyMessage(1); // επιτυχία
            } else {
                throw new IOException("Μη αναμενόμενη απάντηση από τον server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            Log.e("SERVER", "Σφάλμα επικοινωνίας", e);
            Message msg = handler.obtainMessage(0, e.getMessage());
            handler.sendMessage(msg); // στείλε σφάλμα πίσω στο UI thread
        }
    }
}

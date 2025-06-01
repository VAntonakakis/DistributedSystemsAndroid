package com.myefood.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestFromServer<T> extends Thread {

    private final Object requestObject;
    private final Handler handler;
    private String lat = null;
    private String lon = null;

    public RequestFromServer(Handler handler, Object requestObject) {
        this.handler = handler;
        this.requestObject = requestObject;
    }

    public  RequestFromServer(Handler handler, Object requestObject, String lat, String lon){
        this.handler = handler;
        this.requestObject = requestObject;
        this.lat = lat;
        this.lon = lon;
    }


    @Override
    public void run() {
        try (Socket socket = new Socket("192.168.1.122", 5012);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            if(this.lat != null) {
                out.writeObject("Lat::" + lat);
                out.flush();
                out.writeObject("Lon::" + lon);
                out.flush();
                out.writeObject(requestObject);
                out.flush();
            }else if(requestObject.equals("admin")){
                out.writeObject(requestObject);
                out.flush();
                out.writeObject("send");
                out.flush();
            }else{
                out.writeObject(requestObject);
                out.flush();
            }


            Log.d("vaggelis", "request object: " + requestObject);
            Thread.sleep(100);

            Object response = in.readObject();
            Log.d("vaggelis", "response: " + response.getClass().getSimpleName());

            Message msg = Message.obtain();
            msg.what = 1; // success
            msg.obj = response;
            handler.sendMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
            Message msg = Message.obtain();
            msg.what = -1; // failure
            handler.sendMessage(msg);
        }
    }
}

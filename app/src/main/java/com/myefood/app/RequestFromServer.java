package com.myefood.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.example.Product;
import org.example.Store;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class RequestFromServer<T> extends Thread {

    private String requestObject;
    private Handler handler;
    private Store myStore;


    private Map<Product, Integer> cart;
    private String lat = null;
    private String lon = null;

    public RequestFromServer(Handler handler, String requestObject) {
        this.handler = handler;
        this.requestObject = requestObject;
    }

    public RequestFromServer(Handler handler, String requestObject, Map<Product, Integer> cart) {
        this.handler = handler;
        this.requestObject = requestObject;
        this.cart = cart;
    }

    public  RequestFromServer(Handler handler, String requestObject, String lat, String lon){
        this.handler = handler;
        this.requestObject = requestObject;
        this.lat = lat;
        this.lon = lon;
    }

    public RequestFromServer(Handler handler, Store myStore) {
        this.handler = handler;
        this.myStore = myStore;
    }


    @Override
    public void run() {
        try (Socket socket = new Socket("192.168.1.122", 5012);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            if (myStore != null) {
                out.writeObject(myStore);
                out.flush();
            }
            if(this.lat != null) {
                out.writeObject("Lat::" + lat);
                out.flush();
                out.writeObject("Lon::" + lon);
                out.flush();
                out.writeObject(requestObject);
                out.flush();
            }else if(requestObject.equals("admin")) {
                out.writeObject(requestObject);
                out.flush();
                out.writeObject("send");
                out.flush();
            }else if(requestObject.startsWith("neworder::")){

                StringBuilder sb = new StringBuilder(requestObject);
                for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                    sb.append("::").append(entry.getKey().getName())
                            .append("::").append(entry.getValue());
                }
                out.writeObject(sb.toString());
                Log.d("vaggelis", "request object: " + sb.toString());
                out.flush();
            }else{
                out.writeObject(requestObject);
                out.flush();
            }

            Thread.sleep(100);
            Object response = in.readObject();

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

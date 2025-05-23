//package com.myefood.app;
//
//import android.os.Handler;
//import android.util.Log;
//import android.utils.MyObject;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class MyThread extends Thread{
//
//    Handler handler;
//
//    ArrayList<MyObject> items;
//
//    public MyThread(Handler handler, ArrayList<MyObject> items){
//        this.handler = handler;
//        this.items = items;
//    }
//
//    @Override
//    public void run() {
//        try {
//            Socket socket = new Socket("192.168.1.7",1234);
//
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//
//            ArrayList<MyObject> objects = (ArrayList<MyObject>) ois.readObject();
//
//            for(MyObject o : objects){
//                items.add(o);
//            }
//
//            Log.d("ARRAYLIST","Items:"+items.size());
//
//            ois.close();
//
//            socket.close();
//
//            handler.sendEmptyMessage(1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}

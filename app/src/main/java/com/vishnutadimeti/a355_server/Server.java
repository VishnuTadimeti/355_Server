package com.vishnutadimeti.a355_server;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Created by vishnutadimeti on 12/14/16.
 */

public class Server {
    private MainActivity activity;
    private ServerSocket serverSocket;
    private static final int socketServerPORT = 8080;
    private String input, title, message, tasks;
    int month, day, year, event;

    Server(MainActivity activity) {
        this.activity = activity;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }

    int getPort() {
        return socketServerPORT;
    }

    void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(socketServerPORT);
                while (true) {
                    Socket socket = serverSocket.accept();
                    InputStream inStream = socket.getInputStream();
                    Scanner in = new Scanner(inStream);

                    if (in.hasNextLine()) {
                        input = in.nextLine();
                        Log.d("1", input);
                        String firstThree = input.substring(0,3).trim();
                        boolean isTsk, isCal, isCht;
                        isTsk = firstThree.matches("tsk");
                        isCal = firstThree.matches("cal");
                        isCht = firstThree.matches("cht");

                        if (isTsk) {
                            tasks = input.substring(3, input.length());
                            Log.d("2", tasks);
                            if (input.equals("request")) {} else {
                                Database db = new Database(activity.getApplicationContext());
                                db.addTaskRecord(tasks);
                                try (OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))
                                {
                                    out.write(db.tasksResults().toString());
                                    out.flush();
                                }
                                Log.d("3: ", String.valueOf(db.tasksResults()));
                            }
                        }

                        if (isCal) {
                            month = Integer.parseInt(input.substring(3,5).trim());
                            day = Integer.parseInt(input.substring(5,7).trim());
                            year = Integer.parseInt(input.substring(7,11).trim());
                            title = input.substring(11, input.length());
                            Log.d("Month", String.valueOf(month));
                            Log.d("Day", String.valueOf(day));
                            Log.d("Year", String.valueOf(year));
                            Log.d("Title", title);
                            if (input.equals("request")) {} else {
                                Database db = new Database(activity.getApplicationContext());
                                db.addCalRecord(title, month, day, year);
                                try (OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))
                                {
                                    out.write(db.calResults().toString());
                                    out.flush();
                                }
                                Log.d("Chat: ", String.valueOf(db.chatResults()));
                            }
                        }

                        if (isCht) {
                            message = input.substring(3, input.length());
                            Log.d("Chat", message);
                            if (message.equals("request")) {} else {
                                Database db = new Database(activity.getApplicationContext());
                                db.addChatRecord(message);
                                try (OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))
                                {
                                    out.write(db.chatResults().toString());
                                    out.flush();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}
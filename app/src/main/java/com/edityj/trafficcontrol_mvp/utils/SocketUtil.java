package com.edityj.trafficcontrol_mvp.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/2/8
 * description: socket通信工具类
 */
public class SocketUtil {
    private static SocketUtil instance;
    private static final String TAG = "SocketUtil";
//    Socket
    private Socket socket;
//    IP地址
    private String ipAddress;
//    端口号
    private int port;
    private Thread thread;
//    Socket输出流
    private OutputStream outputStream;
//    Socket输入流
    private InputStream inputStream;
    //回调方法
    private TcpCallback tcpCallback;
//    构造函数私有化
    private SocketUtil() {
        super();
    }
//    提供一个全局的静态方法
    public static SocketUtil sharedCenter() {
        if (instance == null) {
            synchronized (SocketUtil.class) {
                if (instance == null) {
                    instance = new SocketUtil();
                }
            }
        }
        return instance;
    }
    /**
     * 通过IP地址(域名)和端口进行连接
     *
     * @param ipAddress  IP地址(域名)
     * @param port       端口
     */
    public void connect(final String ipAddress, final int port) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ipAddress, port);
                    socket.setSoTimeout ( 2 * 2000 );//设置超时时间
                    if (isConnected()) {
                        SocketUtil.sharedCenter().ipAddress = ipAddress;
                        SocketUtil.sharedCenter().port = port;
                        if (tcpCallback != null) {
                            tcpCallback.connected();
                        }
                        outputStream = socket.getOutputStream();
                        inputStream = socket.getInputStream();
                        receive();
                        Log.i(TAG,"连接成功");
                    }else {
                        Log.i(TAG,"连接失败");
                        if (tcpCallback != null) {
                            tcpCallback.disConnect(new IOException("连接失败"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,"连接异常");
                    if (tcpCallback != null) {
                        tcpCallback.disConnect(e);
                    }
                }
            }
        });
        thread.start();
    }
    /**
     * 判断是否连接
     */
    public boolean isConnected() {
        return socket.isConnected();
    }
    /**
     * 连接
     */
    public void connect() {
        connect(ipAddress,port);
    }
    /**
     * 断开连接
     */
    public void disconnect() {
        if (isConnected()) {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                socket.close();
                if (socket.isClosed()) {
                    if (tcpCallback != null) {
                        tcpCallback.disConnect(new IOException("断开连接"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 接收数据
     */
    public void receive() {
        while (isConnected()) {
            try {
                /**得到的是16进制数，需要进行解析*/
                byte[] bt = new byte[1024];
//                获取接收到的字节和字节数
                int length = inputStream.read(bt);
//                获取正确的字节
                byte[] bs = new byte[length];
                System.arraycopy(bt, 0, bs, 0, length);

                String str = new String(bs, "UTF-8");
                if (str != null) {
                    if (tcpCallback != null) {
                        tcpCallback.receive(str);
                    }
                }
                Log.i(TAG,"接收成功");
            } catch (IOException e) {
                Log.i(TAG,"接收失败");
            }
        }
    }
    /**
     * 发送数据
     *
     * @param data  数据
     */
    public void send(final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (socket != null) {
                    try {
                        outputStream.write(data);
                        outputStream.flush();
                        Log.i(TAG,"发送成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(TAG,"发送失败");
                    }
                } else {
                    connect();
                }
            }
        }).start();
    }

    public interface TcpCallback{
        //创建链接
        void connected();
        //连接异常
        void disConnect(IOException e);
        //接受服务器返回数据
        void receive(String receicedMessage);
    }

    public void setTcpCallback(TcpCallback tcpCallback){
        this.tcpCallback = tcpCallback;
    }

    /**
     * 移除回调
     */
    private void removeCallback() {
        tcpCallback = null;
    }
}

package com.jndv.jnudplibrary;

import android.content.Context;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangguodong
 * Date: 2022/8/8
 * QQ: 1772889689@qq.com
 * WX: gdihh8180
 * Description: udp通讯管理工具
 */
public class JNUdpManager {
    public static JNUdpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder{
        private static final JNUdpManager INSTANCE = new JNUdpManager();
    }
    public Context mContext;

    private String netIP = "192.168.1.104";//目标ip
    private int netPort = 34580;//目标端口
    private int myPort = 20020;//端口

    private DatagramSocket socket;

    public interface ReceiveMsgListener{
        void receiveMsg(String content);
    }

    /**
     * 设置目标IP
     * @param netIP
     * @return
     */
    public JNUdpManager setNetIP(String netIP) {
        this.netIP = netIP;
        if (null!=mContext)JNPreferenceUtils.saveString("netIP", netIP);
        return getInstance();
    }

    /**
     * 设置目标端口号
     * @param netPort
     * @return
     */
    public JNUdpManager setNetPort(int netPort) {
        this.netPort = netPort;
        if (null!=mContext)JNPreferenceUtils.saveInt("netPort", netPort);
        return getInstance();
    }

    /**
     * 设置本地端口号
     * @param myPort
     * @return
     */
    public JNUdpManager setMyPort(int myPort) {
        this.myPort = myPort;
        if (null!=mContext)JNPreferenceUtils.saveInt("myPort", myPort);
        return getInstance();
    }

    /**
     * 初始化
     * 主要初始化文件存储功能
     * 初始化后设置的ip和端口等信息会存储到文件中，每次发消息之前获取存储中的信息使用
     * @param context
     */
    public void init(Context context){
        mContext = context;
        if (null!=mContext){
            JNPreferenceUtils.saveString("netIP", netIP);
            JNPreferenceUtils.saveInt("netPort", netPort);
            JNPreferenceUtils.saveInt("myPort", myPort);
        }
    }

    private void initData(){
        if (null!=mContext){
            netIP = JNPreferenceUtils.getString("netIP");
            netPort = JNPreferenceUtils.getInt("netPort");
            myPort = JNPreferenceUtils.getInt("myPort");
        }
    }

    /**
     * 在线程中发送消息
     */
    public void sendMsgThread(byte[] msg){
        JNThreadManager.getIO().execute(new Runnable() {
            @Override
            public void run() {
                sendMsg(msg);
            }
        });
    }

    /**
     * 发送消息的函数
     */
    private void sendMsg(byte[] data) {
        initData();
        Log.v("v", "开始发送");
        try {
            if (null==data || data.length<=0){
                Log.v("f", "发送失败！");
                return;
            }
            if (socket == null) {
                // 本机的端口号
                socket = new DatagramSocket(myPort);
            }
            // 对方的IP和端口号
            DatagramPacket send = new DatagramPacket(data, data.length
                    ,new InetSocketAddress(netIP, netPort));
            socket.send(send);
            Log.v("f", "发送成功！");
        } catch (Exception e) {
            Log.v("f", "发送失败！");
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     */
    private void initReceiveMsg(ReceiveMsgListener receiveMsgListener){
        JNThreadManager.getIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket == null) {
                        socket = new DatagramSocket(myPort);
                    }
                    String str;
//                    List<Byte> bytes = new ArrayList<>();
//                    List list = new ArrayList();
                    while (true) {
                        // 创建一个空的字节数组
                        byte[] data = new byte[1024];
                        // 将字节数组和数组的长度传进DatagramPacket 创建的对象里面
                        DatagramPacket pack2 = new DatagramPacket(data, data.length);
//                        Log.v("s", "pack2");
                        Log.v("s", "开始 接收");
                        try {
                            // socket对象接收pack包，程序启动的时候，socket会一直处于阻塞状态，直到有信息传输进来
                            socket.receive(pack2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 获取发送数据的IP地址
                        // String ip = pack2.getAddress().getHostAddress();
                        // 获取发送数据端的端口号
                        // int port = pack2.getPort();
                        str = new String(pack2.getData(), 0, pack2.getLength()); // 将字节数组转化成字符串表现出来
                        // 开启接收端口后会持续接收数据，只有页面可见的时候才将收到的数据写入
//                        if (isText) {
//                            tv.setText(str);
//                        }
                        if (null!=receiveMsgListener)receiveMsgListener.receiveMsg(str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}

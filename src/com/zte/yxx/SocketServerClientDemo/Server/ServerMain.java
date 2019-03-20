package com.zte.yxx.SocketServerClientDemo.Server;

/**
 * Created by Administrator on 2019/3/16.
 */
public class ServerMain {
    public static void main(String[] args) {
        ServerUtil serverUtil = ServerUtil.GetInstance();
        Integer serverPort = ServerConfig.getServerConfigPort();
        serverUtil.startServer(serverPort);
    }
}

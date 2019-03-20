package com.zte.yxx.SocketServerClientDemo.Server;

import com.zte.yxx.SocketServerClientDemo.Util.Protocol.CommonProtocolParse;
import com.zte.yxx.SocketServerClientDemo.Execptions.ParseProtocolException;
import com.zte.yxx.SocketServerClientDemo.Util.Sleep;
import com.zte.yxx.SocketServerClientDemo.Execptions.StreamParseException;
import com.zte.yxx.SocketServerClientDemo.Util.SocketUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2019/3/16.
 */
public class ServerUtil {

    static Logger logger = Logger.getLogger(ServerUtil.class.toString());

    static {
        logger.info("INFO=starting socket service.");
        serviceRequestCount = ServerConfig.getMaxServeNum();
    }

    private static ExecutorService socketRequestExecutorService;
    private static int serviceRequestCount;
    private static Queue<Socket> requestSocketQueue;


    static class InnerSingleton{
        public static ServerUtil serverUtil = new ServerUtil();
    }

    public static ServerUtil GetInstance(){
        return InnerSingleton.serverUtil;
    }

    private ServerUtil()
    {
    }

    public void startServer(int serverPort)
    {
        try{
            ServerSocket serverSocket = new ServerSocket(serverPort);
            startSocketHandlerThreadPool();
            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("new connection accepted: " + socket);

                synchronized (requestSocketQueue)
                {
                    requestSocketQueue.offer(socket);
                }
            }
        }catch (IOException ex) {
            System.out.println("ERR=start server failed: " + ex.getMessage());
        }
    }

    private void startSocketHandlerThreadPool()
    {
        socketRequestExecutorService = Executors.newFixedThreadPool(serviceRequestCount);
        for (int i = 0; i < serviceRequestCount; i++) {
            Runnable runnable = new SocketRequestParserRunnable();
            socketRequestExecutorService.submit(runnable);
        }
        requestSocketQueue = new LinkedList<Socket>();
    }



    class SocketRequestParserRunnable implements Runnable
    {
        @Override
        public void run() {
            while (true)
            {
                Socket socket;
                synchronized (requestSocketQueue) {
                    socket = requestSocketQueue.poll();
                }
                if(socket == null)
                {
                    Sleep.sleepMilliSeconds(100);
                    continue;
                }

                logger.info("INFO=start to serve socket: " + socket);

                if(!SocketUtil.checkSocketStateOK(socket))
                {
                    logger.info("INFO=socket state is checked as abnormal, exit service: " + socket);
                    try {
                        socket.close();
                    }
                    catch (IOException e)
                    { }
                    continue;
                }

                ServeSocketRequest(socket);
            }
        }

        void ServeSocketRequest(Socket socket)
        {
            try
            {
                InputStream inputStream = socket.getInputStream();
                while (true)
                {
                    CommonProtocolParse commonProtocolParse = new CommonProtocolParse(inputStream);
                    try{
                        String string = commonProtocolParse.parseInputStream();
                        logger.info("INFO=get a new request: " + string);
                    }catch (ParseProtocolException pex){
                        inputStream.close();
                        logger.warning("WARN=parse request failed: " + pex.getMessage());
                        return;
                    }catch (StreamParseException stmEx){
                        inputStream.close();
                        logger.warning("WARN=read stream failed: " + stmEx.getMessage());
                        return;
                    }
                }
            }catch (IOException ex)
            {
                logger.warning("WARN=socket get inputStream failed: " + ex.getMessage());
            }

        }


    }

}

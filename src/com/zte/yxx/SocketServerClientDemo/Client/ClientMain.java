package com.zte.yxx.SocketServerClientDemo.Client;

import com.zte.yxx.SocketServerClientDemo.Execptions.StreamWriteException;
import com.zte.yxx.SocketServerClientDemo.Util.Protocol.CommonProtocolWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2019/3/17.
 */
public class ClientMain {
    static Logger logger = Logger.getLogger(ClientMain.class.getName());

    String serverHost;
    Integer serverPort;

    public static void main(String[] args) {
        new ClientMain().testClient();
    }

    public void testClient()
    {
        initServerInfo();

        try{
            Socket socket = new Socket(serverHost, serverPort);
            while (true)
            {
                if(!sendTerminalMsgToServer(socket))
                {
                    logger.warning("WARN=send msg to server failed, exit.");
                    return;
                }
            }
        }catch (UnknownHostException e){
            logger.warning("WARN=connect to server failed, please make sure server is started and the port is right: " + e.getMessage());
        }catch (IOException ex){
            logger.warning("WARN=communicate with server failed : " + ex.getMessage());
        }
    }

    protected boolean sendTerminalMsgToServer(Socket socket)
    {
        String strTerminal = readTerminalInput();
        if(strTerminal == null)
        {
            System.out.println("null read, exit now.");
            return false;
        }

        try {
            new CommonProtocolWriter().writeString(socket.getOutputStream(), strTerminal);
            System.out.println("deliver msg to server succ: " + strTerminal);
            return true;
        }catch (IOException ioex){
            logger.warning("WARN=send msg to server failed : " + ioex.getMessage());
        }
        catch (StreamWriteException ex){
            logger.warning("WARN=send msg to server failed : " + ex.getMessage());
        }
        return false;
    }

    void initServerInfo()
    {
        serverHost = ClientConfig.getClientConfigIp();
        serverPort = ClientConfig.getClientConfigPort();
    }

    String readTerminalInput()
    {
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String stringIn = bufferedReader.readLine();
            return stringIn;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

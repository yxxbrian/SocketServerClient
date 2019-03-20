package com.zte.yxx.SocketServerClientDemo.Util;

import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2019/3/17.
 */
public class SocketUtil {
    private static Logger logger = Logger.getLogger(SocketUtil.class.getName());
    public static boolean checkSocketStateOK(Socket socket)
    {
        if(socket.isClosed())
            return false;
        return true;
    }

}

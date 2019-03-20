package com.zte.yxx.SocketServerClientDemo.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2019/3/17.
 */
public class ServerConfig {
    protected static Logger logger = Logger.getLogger(ServerConfig.class.getName());
    protected static Properties properties;

    static {
        try{
            FileInputStream fileInputStream = new FileInputStream("src/com/zte/yxx/SocketServerClientDemo/serverConfig.property");
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        }catch (IOException ioex){
            logger.warning("WARN=load server config failed: " + ioex.getMessage());
        }
    }

    protected static String getConfig(String key)
    {
        Object obj = properties.get(key);
        if(obj == null)
            return null;
        else
            return (String)obj;
    }

    public static final String SERVER_CONFIG_IP = "server.ip";
    public static final String SERVER_CONFIG_PORT = "server.port";
    public static final String SERVER_MAX_SERVE_NUM = "server.max.serve.num";


    private static final String DEFAULT_SERVER_CONFIG_IP = "localhost";
    public static String getServerConfigIp()
    {
        String stringServerIp = getConfig(SERVER_CONFIG_IP);
        if(stringServerIp == null || stringServerIp.length() == 0)
            return DEFAULT_SERVER_CONFIG_IP;
        return stringServerIp;
    }

    public static Integer getServerConfigPort()
    {
        String portStr = getConfig(SERVER_CONFIG_PORT);
        if(portStr == null)
        {
            throw new RuntimeException("query server config failed.");
        }
        int serverPort = Integer.valueOf(portStr);
        if(serverPort <= 0)
        {
            throw new IllegalArgumentException("server port should be 1~65535.");
        }
        return serverPort;
    }

    private static final Integer DEFAULT_SERVER_MAX_SERVE_NUM = 20;

    public static Integer getMaxServeNum()
    {
        String strMaxServeNum = getConfig(SERVER_MAX_SERVE_NUM);
        if(strMaxServeNum == null)
        {
            return DEFAULT_SERVER_MAX_SERVE_NUM;
        }
        Integer maxServeNum = Integer.valueOf(strMaxServeNum);
        if(maxServeNum <= 0)
            return DEFAULT_SERVER_MAX_SERVE_NUM;
        return maxServeNum;
    }

}

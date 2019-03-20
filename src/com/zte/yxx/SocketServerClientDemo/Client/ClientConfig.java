package com.zte.yxx.SocketServerClientDemo.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2019/3/17.
 */
public class ClientConfig {
    protected static Logger logger = Logger.getLogger(ClientConfig.class.getName());
    protected static Properties properties;

    private static final String defaultClientConfigFile = "src/com/zte/yxx/SocketServerClientDemo/clientConfig.property";

    static {
        try{
            FileInputStream fileInputStream = new FileInputStream(defaultClientConfigFile);
            try {
                properties = new Properties();
                properties.load(fileInputStream);
            }finally {
                fileInputStream.close();
            }
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

    public static final String CLIENT_CONFIG_IP = "server.ip";
    public static final String CLIENT_CONFIG_PORT = "server.port";


    private static final String DEFAULT_CLIENT_CONFIG_IP = "localhost";

    public static String getClientConfigIp(){
        String stringServerIp = getConfig(CLIENT_CONFIG_IP);
        if(stringServerIp == null || stringServerIp.length() == 0)
            return DEFAULT_CLIENT_CONFIG_IP;
        return stringServerIp;
    }



    public static Integer getClientConfigPort(){
        String portStr = getConfig(CLIENT_CONFIG_PORT);
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


}

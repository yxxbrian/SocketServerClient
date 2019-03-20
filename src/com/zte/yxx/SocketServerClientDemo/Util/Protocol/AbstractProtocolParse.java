package com.zte.yxx.SocketServerClientDemo.Util.Protocol;

import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2019/3/16.
 */
public abstract class AbstractProtocolParse implements ProtocolParse {
    static Logger logger = Logger.getLogger(AbstractProtocolParse.class.getName());
    InputStream inputStream;

    protected AbstractProtocolParse(InputStream inputStream){
        this.inputStream = inputStream;
    }

}

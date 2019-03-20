package com.zte.yxx.SocketServerClientDemo.Util.Protocol;

import com.zte.yxx.SocketServerClientDemo.Execptions.ParseProtocolException;
import com.zte.yxx.SocketServerClientDemo.Execptions.StreamParseException;

/**
 * Created by Administrator on 2019/3/16.
 */
public interface ProtocolParse {
    /**
     * handle the input stream with current protocal, and return a request result
     * */
    String parseInputStream() throws ParseProtocolException, StreamParseException;
}

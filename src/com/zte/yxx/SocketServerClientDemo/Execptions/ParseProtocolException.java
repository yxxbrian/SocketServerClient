package com.zte.yxx.SocketServerClientDemo.Execptions;

/**
 * Created by Administrator on 2019/3/16.
 */
public class ParseProtocolException extends Exception {
    public ParseProtocolException(String desc)
    {
        super("Parse protocol of current stream error: " + desc);
    }
}

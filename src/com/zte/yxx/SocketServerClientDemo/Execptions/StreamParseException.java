package com.zte.yxx.SocketServerClientDemo.Execptions;

/**
 * Created by Administrator on 2019/3/17.
 */
public class StreamParseException  extends Exception{

    public StreamParseException(String desc)
    {
        super("Parse protocal of current stream error: " + desc);
    }

}

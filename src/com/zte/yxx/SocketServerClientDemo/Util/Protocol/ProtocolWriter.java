package com.zte.yxx.SocketServerClientDemo.Util.Protocol;

import com.zte.yxx.SocketServerClientDemo.Execptions.StreamWriteException;

import java.io.OutputStream;

/**
 * Created by Administrator on 2019/3/17.
 */
public interface ProtocolWriter {

    void writeString(OutputStream outputStream, String strData) throws StreamWriteException;

}

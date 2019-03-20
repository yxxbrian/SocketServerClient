package com.zte.yxx.SocketServerClientDemo.Util.Protocol;

import com.zte.yxx.SocketServerClientDemo.Execptions.StreamWriteException;
import com.zte.yxx.SocketServerClientDemo.Util.StreamUtil;

import java.io.OutputStream;

/**
 * Created by Administrator on 2019/3/17.
 */
public class CommonProtocolWriter extends AbstractProtocolWriter{

    public void writeString(OutputStream outputStream, String strData) throws StreamWriteException
    {
        writeHeader(outputStream);
        StreamUtil.writeStringWithLength(outputStream, strData);
    }

    protected void writeHeader(OutputStream outputStream)  throws StreamWriteException
    {
        StreamUtil.writeString(outputStream, ProtocolDefine.protocal_name);
        StreamUtil.writeString(outputStream, ProtocolDefine.protocal_reserve_0);
        StreamUtil.writeString(outputStream, ProtocolDefine.protocal_reserve_1);
    }

}

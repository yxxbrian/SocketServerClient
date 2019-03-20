package com.zte.yxx.SocketServerClientDemo.Util.Protocol;

import com.zte.yxx.SocketServerClientDemo.Execptions.ParseProtocolException;
import com.zte.yxx.SocketServerClientDemo.Execptions.StreamParseException;

import java.io.InputStream;

import static com.zte.yxx.SocketServerClientDemo.Util.StreamUtil.readInt32;
import static com.zte.yxx.SocketServerClientDemo.Util.StreamUtil.readStringN;

/**
 * Created by Administrator on 2019/3/16.
 */
public class CommonProtocolParse extends AbstractProtocolParse {

    public CommonProtocolParse(InputStream inputStream)
    {
        super(inputStream);
    }

    @Override
    public synchronized String parseInputStream() throws ParseProtocolException, StreamParseException {
        checkHeader0();
        checkReserve0();
        checkReserve1();
        int contentLength = readInt32(this.inputStream);
        String content = readStringN(this.inputStream, contentLength);

        return content;
    }

    private String checkHeader0() throws ParseProtocolException, StreamParseException
    {
        return checkString(ProtocolDefine.protocal_name);
    }

    private String checkReserve0() throws ParseProtocolException, StreamParseException
    {
        return checkString(ProtocolDefine.protocal_reserve_0);
    }

    private String checkReserve1() throws ParseProtocolException,StreamParseException
    {
        return checkString(ProtocolDefine.protocal_reserve_1);
    }

    private String checkString(String strNeed) throws ParseProtocolException, StreamParseException
    {
        int length = strNeed.getBytes().length;
        String stringProtocolName = readStringN(this.inputStream, length);
        if(!stringProtocolName.equals(strNeed))
        {
            logger.info("INFO=wrong header detected: " + stringProtocolName);
            throw new ParseProtocolException("protocol check fail");
        }
        return stringProtocolName;
    }

}

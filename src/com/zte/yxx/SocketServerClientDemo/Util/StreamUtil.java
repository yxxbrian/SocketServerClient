package com.zte.yxx.SocketServerClientDemo.Util;

import com.zte.yxx.SocketServerClientDemo.Execptions.ParseProtocolException;
import com.zte.yxx.SocketServerClientDemo.Execptions.StreamParseException;
import com.zte.yxx.SocketServerClientDemo.Execptions.StreamWriteException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2019/3/17.
 */
public class StreamUtil {

    public static int readInt32(InputStream inputStream) throws  StreamParseException
    {
        int result = 0;
        int byteInt = 0;
        for (int i = 0; i < 4; i++) {
            try{
                byteInt = inputStream.read();
                if(byteInt == -1)
                {
                    throw new StreamParseException("read int need 4 bytes, actually read " + i);
                }
                result += byteInt << i*8;
            }catch (IOException ex){
                throw new StreamParseException("read int failed: " + ex.getMessage());
            }
        }
        return result;
    }

    public static void writeInt32(OutputStream outputStream, int data) throws StreamWriteException
    {
        int mask = 0xff;
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            int curByte = data & mask;
            bytes[i] = (byte)curByte;
            data = data >> 8;
        }

        try{
            outputStream.write(bytes);
        }catch (IOException ex){
            throw new StreamWriteException("write int failed: " + ex.getMessage());
        }
    }

    public static String readStringN(InputStream inputStream) throws StreamParseException, ParseProtocolException
    {
        int length = readInt32(inputStream);
        return readStringN(inputStream, length);
    }

    public static String readStringN(InputStream inputStream, int length) throws StreamParseException
    {
        if(length < 0)
        {
            throw new RuntimeException(new IllegalArgumentException("length should never smaller than 0"));
        }

        if(length == 0)
        {
            return "";
        }

        byte[] bytesRead = new byte[length];
        int currentIndex = 0;
        int readCountThisTime = 0;
        try{
            while (currentIndex < length && (readCountThisTime =
                    inputStream.read(bytesRead, currentIndex, length-currentIndex)) != -1)
            {
                currentIndex += readCountThisTime;
            }

            String s = new String(bytesRead, Charset.forName("UTF-8"));

            if(currentIndex != length)
            {
                StringBuilder stringBuilder = new StringBuilder("read string failed, ");
                stringBuilder.append("need to read length ").append(length).append(", ");
                stringBuilder.append("actually read length ").append(currentIndex).append(", content:").append(s);
                throw new StreamParseException(stringBuilder.toString());
            }
            return s;
        }catch (IOException e){
            throw new StreamParseException("read int failed： " + e.getMessage());
        }
    }

    public static void writeStringWithLength(OutputStream outputStream, String stringData) throws StreamWriteException
    {
        byte[] bytes = stringData.getBytes(Charset.forName("UTF-8"));
        try{
            writeInt32(outputStream, bytes.length);
            outputStream.write(bytes);
        }catch (IOException ex){
            throw new StreamWriteException("write string failed: " + ex.getMessage());
        }
    }

    public static void writeString(OutputStream outputStream, String stringData) throws StreamWriteException
    {
        byte[] bytes = stringData.getBytes(Charset.forName("UTF-8"));
        try{
            outputStream.write(bytes);
        }catch (IOException ex){
            throw new StreamWriteException("write string failed: " + ex.getMessage());
        }
    }

}

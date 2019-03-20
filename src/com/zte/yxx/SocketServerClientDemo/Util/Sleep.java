package com.zte.yxx.SocketServerClientDemo.Util;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/3/17.
 */
public class Sleep {

    public static void sleepMilliSeconds(int time)
    {
        try{
            TimeUnit.MILLISECONDS.sleep(time);
        }catch (InterruptedException e){

        }
    }
}

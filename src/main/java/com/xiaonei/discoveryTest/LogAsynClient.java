/**
 * $Id$
 * Copyright 2009-2012 Oak Pacific Interactive. All rights reserved.
 */
package com.xiaonei.discoveryTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import com.xiaonei.discoveryTest.Log.AsyncClient.log_call;

/**
 *
 *
 * @author <a href="mailto:qiao.wang@renren-inc.com">王侨</a>
 * @version 2013-1-6下午2:51:05
 */
public class LogAsynClient {

    public static final String SERVER_IP = "localhost";

    public static final int SERVER_PORT = 8090;

    public static final int TIMEOUT = 30000;

    /**
     *
     * @param category
     * @param message
     */
    public void startClient(String category, String message) {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT,
                    TIMEOUT);

            TProtocolFactory tprotocol = new TCompactProtocol.Factory();
            Log.AsyncClient asyncClient = new Log.AsyncClient(tprotocol, clientManager, transport);
            System.out.println("Client start .....");

            CountDownLatch latch = new CountDownLatch(1);
            AsynCallback callBack = new AsynCallback(latch);
            System.out.println("call method sayHello start ...");


            long currentTime = System.currentTimeMillis();
            asyncClient.log(category, currentTime + message, callBack);
            System.out.println("call method sayHello .... end");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("startClient end.");
    }

    public class AsynCallback implements AsyncMethodCallback<log_call> {

        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        public void onComplete(log_call response) {
            System.out.println("onComplete");
            try {
                // Thread.sleep(1000L * 1);
                System.out.println("AsynCall result =:" + response.getResult().toString());
            } catch (TException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LogAsynClient client = new LogAsynClient();
        client.startClient("Mcs", "ugc");

    }

}

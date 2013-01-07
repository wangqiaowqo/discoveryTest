/**
 * $Id$
 * Copyright 2009-2012 Oak Pacific Interactive. All rights reserved.
 */
package com.xiaonei.discoveryTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 *
 * @author <a href="mailto:qiao.wang@renren-inc.com">王侨</a>
 * @version 2013-1-6下午2:22:39
 */
public class LogClient {

    public static final String SERVER_IP = "localhost";

    public static final int SERVER_PORT = 8090;

    public static final int TIMEOUT = 30000;

    /**
     *
     * @param category
     * @param message
     * @throws IOException
     */
    public void startClient(String category, String message) throws IOException {
        TTransport transport = null;
        try {
           transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            //TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            Log.Client client = new Log.Client(protocol);
            transport.open();
            // 调用服务的 log 方法
            File file = new File("//home/qiaowang/thrift/1.txt");
            BufferedReader reader = null;

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while((tempString = reader.readLine()) != null) {
                System.out.println("line "+line+": "+tempString);
                line++;
                long currentTime = System.currentTimeMillis();
                String result = client.log(category, currentTime + tempString);

                System.out.println("Thrify client result =: " + result);
            }
            reader.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        LogClient client = new LogClient();
        client.startClient("mcs", "ugc");

    }

}

/**
 * $Id$
 * Copyright 2009-2012 Oak Pacific Interactive. All rights reserved.
 */
package com.xiaonei.discoveryTest;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 *
 *
 * @author <a href="mailto:qiao.wang@renren-inc.com">王侨</a>
 * @version 2013-1-6下午2:04:27
 */
public class LogServer {

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloWorld THsHaServer start ....");

            TProcessor tprocessor = new Log.Processor<Log.Iface>(new LogImpl());
            //实现服务处理接口impl
            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
            THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
            thhsArgs.processor(tprocessor);
            //创建TProcessor
            thhsArgs.transportFactory(new TFramedTransport.Factory());
            //创建TServerTransport
            //使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
            thhsArgs.protocolFactory(new TBinaryProtocol.Factory());
            //创建TProtocol,TBinaryProtocol:二进制格式

            //半同步半异步的服务模型
            TServer server = new THsHaServer(thhsArgs);
            //创建TServer
            server.serve();
            //启动Server

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LogServer server = new LogServer();
        server.startServer();
    }

}

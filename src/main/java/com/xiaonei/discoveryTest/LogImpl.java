/**
 * $Id$
 * Copyright 2009-2012 Oak Pacific Interactive. All rights reserved.
 */
package com.xiaonei.discoveryTest;

import org.apache.thrift.TException;

import com.xiaonei.discoveryTest.Log.Iface;


/**
 *
 *
 * @author <a href="mailto:qiao.wang@renren-inc.com">王侨</a>
 * @version 2013-1-6下午2:00:11
 */
public class LogImpl implements Iface{

    public LogImpl(){

    }

    public String log(String category, String message) throws TException {
        return "Hi, " + category + message + " log is here !!!";
    }

}


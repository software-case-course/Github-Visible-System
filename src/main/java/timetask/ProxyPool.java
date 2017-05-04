package timetask;


import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import po.Proxy;
import util.RequestUtil;

@Component
public class ProxyPool{
    static final int DEFAULT_MAX = 1000;
    static final int DEFAULT_LIMIT = 10;
    Queue<Proxy> pool;
    int maxSize;
    int limit;

    @Autowired
    RequestUtil requestUtil;

    public ProxyPool(){
        pool = new LinkedBlockingQueue<Proxy>();
        this.maxSize = DEFAULT_MAX;
        this.limit = DEFAULT_LIMIT;
    }

    public ProxyPool(int limit, int maxSize){
        pool = new LinkedBlockingQueue<Proxy>();
        this.maxSize = maxSize;
        this.limit = limit;
    }

    public synchronized Proxy useProxy(){
        if(!pool.isEmpty()){
            Proxy proxy = pool.peek();
            if(--limit == 0) popProxy();;
            return proxy;
        }
        return null;
    }

    public void offerProxy(Proxy proxy){
        if(!overload()) pool.offer(proxy);
    }

    public synchronized void popProxy(){
        pool.poll();
        limit = DEFAULT_LIMIT;
    }

    public synchronized void deleteProxy(Proxy proxy){
        pool.remove(proxy);
    }

    public boolean checkProxy(Proxy proxy){
        try{
            requestUtil.request(
                "http://city.ip138.com/ip2city.asp",
                "GET", null, null, requestUtil.getProxy(proxy.getIp(), proxy.getPort()));
        }catch(Exception e){
            System.out.println("wrong proxy");
            return false;
        }
        System.out.println("clear proxy");
        return true;
    }

    public int size(){
        return pool.size();
    }

    public int max(){
        return maxSize;
    }

    public boolean overload(){
        return pool.size()>maxSize;
    }

    public Iterator<Proxy> iterator(){
        return pool.iterator();
    }
}
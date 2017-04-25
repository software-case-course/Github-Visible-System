package timetask;


import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import po.Proxy;
import util.RequestUtil;

@Component
public class ProxyPool{
    static final int DEFAULT_MAX = 30;
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
        Proxy proxy = pool.peek();
        try{
            requestUtil.request(
                "https://www.baidu.com",
                "GET", null, null, requestUtil.getProxy(proxy.getIp(), proxy.getPort()));
        }catch(Exception e){
            pool.remove();
            return useProxy();
        }
        if(--limit == 0){
            pool.remove();
            limit = DEFAULT_LIMIT;
        }
        return proxy;
    }

    public synchronized void offerProxy(Proxy proxy){
        pool.add(proxy);
    }

    public synchronized void clearProxy(int num){
        for(int i=0;i<num;i++){
            if(!pool.isEmpty()){
                pool.remove();
            }
        }
        limit = DEFAULT_LIMIT;
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
}
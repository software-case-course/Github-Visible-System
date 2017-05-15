package timetask;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class ThreadPool{
    ThreadPoolExecutor pool;

    public ThreadPool(){
        this(10, 10);
    }

    public ThreadPool(int core, int max){
        pool = new ThreadPoolExecutor(core, max, 1000, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public void excute(Runnable command){
        pool.execute(command);
    }

    public int getCoreSize(){
        return pool.getCorePoolSize();
    }

    public int getMaxSize(){
        return pool.getMaximumPoolSize();
    }
}
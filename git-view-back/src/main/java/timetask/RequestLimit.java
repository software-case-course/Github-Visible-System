package timetask;

import org.springframework.stereotype.Component;

@Component
public class RequestLimit{
    final static int LIMIT = 10;
    int request_limit = LIMIT;

    public synchronized void consume() throws Exception{
        if(request_limit == 0){
            this.wait();
            consume();
        }
        else{
            request_limit--;
            //System.out.println(request_limit);
        }
    }

    public synchronized void refresh(){
        request_limit = LIMIT;
        for(int i=0;i<LIMIT;i++){
            this.notify();
        }
    }
}
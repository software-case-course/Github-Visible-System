package timetask;

public class RefreshThread extends Thread{
    RequestLimit limit;

    public RefreshThread(RequestLimit limit){
        this.limit = limit;
    }

    @Override
    public void run(){
        limit.refresh();
    }
}
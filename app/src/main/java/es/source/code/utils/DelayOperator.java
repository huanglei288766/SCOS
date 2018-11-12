package es.source.code.utils;

public class DelayOperator {
    public void delay()
    {
        try {
            Thread.sleep(600);
        }catch (InterruptedException e){
            e.printStackTrace();;
        }
    }
}

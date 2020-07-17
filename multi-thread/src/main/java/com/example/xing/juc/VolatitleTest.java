package com.example.xing.juc;

/**
 * @author xiexingxing
 * @Created by 2020-05-31 13:35.
 */
public class VolatitleTest {


    /**
     * 由于没加 volatitle 没有内存可见性
     */
    int number=1;
    //volatile int number=1;

    public void setToZero(){
        this.number= 0 ;
    }

    public static void main(String[] args) {
        VolatitleTest test = new VolatitleTest();

        new Thread(()->{
            try {

                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.setToZero();
            System.out.println(" setTo0 "+System.currentTimeMillis());
        }).start();

        //模拟阻塞
        while (test.number==1){

        }
        System.out.println("num == 0 "+System.currentTimeMillis());
    }

}
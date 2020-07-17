package com.example.xing.Volatitle;

/**
 * @author xiexingxing
 * @Created by 2020-05-14 16:57.
 */
public class Test {

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        innerClass.start();

        for(;;){
            synchronized (innerClass){
                if(innerClass.isFlag()){
                    System.out.println(" 有点东西");
                }
            }

        }

    }

    static class  InnerClass extends  Thread{
        private  Boolean isFlag =false;

        public Boolean isFlag(){
            return isFlag;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isFlag = true;
            System.out.println("isFlag "+isFlag);
        }
    }
}
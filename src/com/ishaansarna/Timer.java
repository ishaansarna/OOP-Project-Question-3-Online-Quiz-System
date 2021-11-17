//package com.ishaansarna;
//
//public class Timer implements Runnable {
//
//    long time;
//    Thread parentThread
//
//    public Timer(long time, Thread parentThread) {
//        this.time = time;
//        this.parentThread = parentThread;
//    }
//
//    @Override
//    public void run() {
//        long startTime = System.currentTimeMillis();
//        while (System.currentTimeMillis() - startTime < time*1000) {
//            if(userAnswered){
//                break;
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                break;
//            }
//        }
//    }
//}

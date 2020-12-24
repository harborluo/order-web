package com.harbor.config;


public class TestDaemon {

    public class DaemonThread extends Thread{
        public void run()
        {
            try {
                //Thread.sleep(20);
                while(true)
                {
                    Thread.sleep(897);
                    System.out.println(System.currentTimeMillis() + ": run");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void test01()
    {
        try {
            DaemonThread[] dts = new DaemonThread[2];
            for(int i=0; i<dts.length; i++)
            {
                dts[i] = new DaemonThread();
                //http://hi.baidu.com/zolxl/blog/item/0cbfbfb570bda0c236d3ca8d.html
                //当JVM中所有的线程都是守护线程的时候，JVM就可以退出了；
                //如果还有一个或以上的非守护线程则不会退出。

                //下面这一句，用于测试如上内容。 注释掉将一直运行。不注释，将只运行一会儿。
                dts[i].setDaemon(true);
                dts[i].start();
            }

            Thread.sleep(897*3-1);
            System.out.println(System.currentTimeMillis() +":" + "Thread.sleep(***)");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TestDaemon td = new TestDaemon();
        td.test01();
    }

}
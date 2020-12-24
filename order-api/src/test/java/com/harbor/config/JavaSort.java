package com.harbor.config;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Description:
 * 作者：gu.weidong(Jack)
 * date:2018年9月3日
 * ProjectName:test
 */
public class JavaSort {
    public static List<Integer> tempList = new ArrayList<Integer>();//ArrayList有序，按照输入顺序显示
    public static int count;
    public static long start;
    public static long end;

    public static void main(final String[] args) {
        Random random = new Random();
        List<Integer> firstNum = new ArrayList<Integer>();
        List<Integer> secondNum = new ArrayList<Integer>();
        List<Integer> thirdNum = new ArrayList<Integer>();

        for (int i = 1; i <= 10000; i++) {
            firstNum.add(i);
            secondNum.add(i);
            firstNum.add(random.nextInt(i + 1));//随机生成100000以内数字
            secondNum.add(random.nextInt(i + 1));
            thirdNum.add(random.nextInt(i + 1));
        }
        Collections.shuffle(firstNum); //洗牌，打乱数据
        Collections.shuffle(secondNum);
        Collections.shuffle(thirdNum);

        getStartTime();
        Collections.sort(firstNum);
        getEndTime("java sort run time  ");

        getStartTime();
        secondNum = uniqueSort(secondNum);
        getEndTime("uniqueSort run time ");

        getStartTime();
        mysort(thirdNum);
        getEndTime("mysort run time     ");
    }

    /**
     * 内存足够
     * Description:
     * 作者：gu.weidong(Jack)
     * date:2018年9月3日
     * @param uniqueList
     * @return List<Integer>
     */
    public static List<Integer> uniqueSort(final List<Integer> uniqueList) {
        JavaSort.tempList.clear();
        int[] temp = new int[50001];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = 0;     //各位置0
        }
        for (int i = 0; i < uniqueList.size(); i++) {
            temp[uniqueList.get(i)]++;//将对应的uniqueList中的值在数组中找到，加1（可统计重复次数），如果不想统计重复次数也可直接置1
        }
        for (int i = 0; i < temp.length; i++) {
            for (int j = temp[i]; j > 0; j--) {
                JavaSort.tempList.add(i);//将重复数据放入
            }
        }
        return JavaSort.tempList;
    }

    /**
     * 内存不足的时候可以采用分割的方法，将10000/2=5000，先 处理1-5000的数据，再处理5001-10000的数据
     * Description:
     * 作者：gu.weidong(Jack)
     * date:2018年9月3日
     * @param uniqueList
     * @return List<Integer>
     */
    public static List<Integer> mysort(final List<Integer> uniqueList){
        JavaSort.tempList.clear();

        int[] temp = new int[50001];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = 0;     //各位置0
        }
        for (int i = 0; i < uniqueList.size(); i++) {
            if(uniqueList.get(i)<=5000) {
                temp[uniqueList.get(i)]++;//将对应的uniqueList中的值在数组中找到，加1（可统计重复次数），如果不想统计重复次数也可直接置1
            }
        }
        for (int i = 0; i < temp.length; i++) {
            for (int j = temp[i]; j > 0; j--) {
                JavaSort.tempList.add(i);//将重复数据放入
            }
        }
        for (int i = 0; i < temp.length; i++) {
            temp[i] = 0;     //各位置0
        }
        for (int i = 0; i < uniqueList.size(); i++) {
            if(uniqueList.get(i)>5000) {
                temp[uniqueList.get(i)-5000]++;//将对应的uniqueList中的值在数组中找到，加1（可统计重复次数），如果不想统计重复次数也可直接置1
            }
        }
        for (int i = 0; i < temp.length; i++) {
            for (int j = temp[i]; j > 0; j--) {
                JavaSort.tempList.add(i+5000);//将重复数据放入
            }
        }
        return JavaSort.tempList;
    }

    public static void getStartTime() {
        start= System.nanoTime();
    }

    public static void getEndTime(final String s) {
        long end = System.nanoTime();
        System.out.println(s + ": " + (end - start) + "ns");
    }
}


package com.zheng;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/16.
 */
public class SortTest {
    
    private Integer[] arr = new Integer[10];
    private SortUtils sortUtils;
    
    @Before
    public void init() {
        List<Integer> nums = Lists.newArrayList();
        int num;
        for(int i = 0; i < 10; i++) {
            num = new Random().nextInt(10);
            while(nums.contains(num)) {
                num = new Random().nextInt(10);
            }
            nums.add(num);
        }
        arr = nums.toArray(new Integer[nums.size()]);
        
        sortUtils = new SortUtils();
    }
    
    @After
    public void after() {
        List<Integer> list =  Arrays.asList(arr);
        System.out.println(list);
    }
    
    @Test
    public void insertSort() {
        sortUtils.insertSort(arr);
    }
    
    @Test
    public void shellSort() {
        sortUtils.shellSort(arr);
    }
    
    @Test
    public void stackSort() {
        sortUtils.stackSort(arr);
    }
    
}

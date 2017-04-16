package com.zheng;

/**
 * 包含几种常用的排序算法
 * 插入排序
 * 希尔排序
 * 
 */
public class SortUtils {

    /**
     * 插入排序
     * 从index=1位置的元素开始，将后面的元素当做要插入的元素列表
     * 分别与数组中已经存在的元素进行比较。
     * 如果数组中元素大于当前待插入的元素，那么将数组中的元素向后移动一位
     * 继续与前一个数组中的元素比较，直到找到一个合适的位置，将元素插入进去
     * 
     * @param arr
     */
    public void insertSort(Integer[] arr) {
        if(null == arr || arr.length <= 1) {
            return;
        }
        
        int tmp, j;
        for(int i = 1; i < arr.length; i++) {
            tmp = arr[i];
            j = i - 1;
            for(;j >= 0; j--) {
                if(arr[j] > tmp) {
                    arr[j+1] = arr[j];
                }else {
                    break;
                }
            }
            arr[j+1] = tmp;
        }
    }

    /**
     * 希尔排序，
     * 将待排序的数组按照步长gap分成几组，每组采用插入排序,完成一次排序操作
     * gap减小,继续上面操作，直到gap=0
     * 建议gap = N / 2; gap /= 2;
     * @param arr
     */
    public void shellSort(Integer[] arr) {
        if(null == arr || arr.length <= 1) {
            return;
        }
        
        int tmp, j;
        for(int gap = arr.length / 2; gap > 0; gap /= 2) { //步长
            //----------下面是插入排序
            for(int i = gap; i < arr.length; i += gap) { //待插入元素
                tmp = arr[i];
                j = i - gap;
                for(; j >= 0; j -= gap) { //已插入元素
                    if(arr[j] > tmp) {
                        arr[j+gap] = arr[j];
                    }else {
                        break;
                    }
                }
                arr[j+gap] = tmp;
            }
            //-----------
        }
    }

    /**
     * 堆排序
     * 对于堆排序，如果是逆序，则建立最大堆，然后取出根元素，递归
     * 如果是顺序，则建立最小堆，然后取出根元素，递归
     * @param arr
     */
    public void stackSort(Integer[] arr) {
        // 留出第一个位置，
        // 便于根据索引下标计算堆节点的父节点、左右儿子节点
        Integer[] newarr = new Integer[arr.length+1];
        newarr[0] = 0;
        for(int i = 0; i < arr.length; i++) {
            newarr[i+1] = arr[i];
        }
        
        for(int i = newarr.length - 1; i >= 1; i--) {
            buildStack(newarr, 1, i); //建堆
            deleteNode(newarr, i);
        }
    }

    /**
     * 删除节点，这里需要根据排序算法决定是删除最大还是最小
     * @param newarr
     * @param i
     */
    private void deleteNode(Integer[] newarr, int i) {
        //TODO
    }


    /**
     * 对给定数组范围的元素建堆
     * @param arr
     * @param start
     * @param end
     */
    private void buildStack(Integer[] arr, int start, int end) {
        int parent;
        for(int i = start; i <= end; i++) {
            parent = i / 2;
            while(parent >= 1 && compare(arr[i], arr[parent])) { //比较当前元素与父元素的大小，将最大的放父元素中
                arr[i] = arr[i] ^ arr[parent];
                arr[parent] = arr[i] ^ arr[parent];
                arr[i] = arr[i] ^ arr[parent]; //采用位运算交换两个元素的位置
                parent = i / 2;
            }
        }
    }

    /**
     * 这里改变比较策略，转换排序方式：正序、逆序
     * @param a
     * @param b
     * @return
     */
    private boolean compare(int a, int b) {
        return a > b;
    }
}

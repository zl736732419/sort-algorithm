package com.zheng;

import java.util.*;

class Solution {
    public int[] sortArray(int[] nums) {
        if (null == nums || nums.length <= 1) {
            return nums;
        }
        int left = 0;
        int right = nums.length - 1;
        quickSort(nums, left, right);
        return nums;
    }

    // 插入排序
    public void insertSort(int[] nums) {
        for(int i = 1; i < nums.length; i++) {
            int k = nums[i];
            for(int j = i - 1; j >= 0; j--) {
                if(k < nums[j]) {
                    nums[j+1] = nums[j];
                } else {
                    nums[j+1] = k;
                    break;
                }
            }
        }
    }

    // 归并排序，分治思想
    public void combineSort(int[] nums) {
        int[] temp = new int[nums.length];
        combineSort(nums, temp, 0, nums.length - 1);
    }

    public void combineSort(int[] nums, int[] temp, int left, int right) {
        if(left < right) {
            int center = (left + right) / 2;
            combineSort(nums, temp, left, center);
            combineSort(nums, temp, center+1, right);
            combine(nums, temp, left, center+1, right);
        }
    }

    public void stackSort(int[] nums) {
        int[] temp = new int[nums.length + 1];
        System.arraycopy(nums, 0, temp, 1, nums.length);
        for(int i = temp.length - 1; i >= 1; i--) {
            buildStack(temp, i);
            moveToEnd(temp, i);
        }
        for(int i = 1; i < temp.length; i++) {
            nums[nums.length - i] = temp[i];
        }
    }

    public void moveToEnd(int[] nums, int end) {
        if (end == 1) {
            return;
        }
        swap(nums, 1, end);
    }


    public void buildStack(int[] nums, int end) {
        for(int i = 1; i <= end; i++) {
            int parent = i / 2;
            int cur = i;
            while(parent >= 1 && lessThan(nums, cur, parent)) {
                swap(nums, cur, parent);
                cur = parent;
                parent /= 2;
            }
        }
    }

    // 交换元素
    void swap(int[] nums, int fromIndex, int toIndex) {
        int length = nums.length;
        if(fromIndex < 0 || fromIndex >= length) {
            return;
        }
        if(toIndex < 0 || toIndex >= length) {
            return;
        }
        nums[fromIndex] ^= nums[toIndex];
        nums[toIndex] ^= nums[fromIndex];
        nums[fromIndex] ^= nums[toIndex];
    }

    public void combine(int[] nums, int[] temp, int leftStart, int rightStart, int rightEnd) {
        int tempIndex = 0;
        int leftEnd = rightStart - 1;
        int num = rightEnd - leftStart + 1;
        while(leftStart <= leftEnd && rightStart <= rightEnd) {
            if(lessThan(nums, leftStart, rightStart)) {
                temp[tempIndex++] = nums[leftStart++];
            } else {
                temp[tempIndex++] = nums[rightStart++];
            }
        }
        while(leftStart <= leftEnd) {
            temp[tempIndex++] = nums[leftStart++];
        }
        while(rightStart <= rightEnd) {
            temp[tempIndex++] = nums[rightStart++];
        }
        for(int i = 0; i < num; i++) {
            nums[rightEnd--] = temp[--tempIndex];
        }
    }

    public boolean lessThan(int[] nums, int left, int right) {
        return nums[left] <= nums[right];
    }

    public void quickSort(int[] nums, int left, int right) {
        if(left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int x = nums[i];
        while(i < j) {
            while(i < j && nums[j] >= x) {
                j--;
            }
            if(i < j) {
                nums[i++] = nums[j];
            }
            while(i < j && nums[i] <= x) {
                i++;
            }
            if(i < j) {
                nums[j--] = nums[i];
            }
        }
        nums[i] = x;
        quickSort(nums, left, i-1);
        quickSort(nums, i+1, right);
    }

    // 基数排序，根据个十百千位分桶，并依次进行排序
    public void radixSort(int[] nums) {
        // 求得最大位数
        int maxLength = 0;
        for(Integer num : nums) {
            int length = num.toString().length();
            if(length > maxLength) {
                maxLength = length;
            }
        }

        Map<Integer, List<Integer>> buckets = new HashMap<>();
        for(int i = 0; i < maxLength; i++) { // 个、十、百、千的顺序按照各个位置的0-9分桶
            buckets.clear();
            TreeSet<Integer> sortedBuckets = new TreeSet<>();
            for(int num : nums) {
                int bucket = (int)(num / (Math.pow(10, i)) % 10);
                List<Integer> result = buckets.get(bucket);
                if(null == result) {
                    result = new ArrayList<>();
                    buckets.put(bucket, result);
                }
                result.add(bucket);
                sortedBuckets.add(bucket);
            }
            int index = 0;
            for(Integer bucket : sortedBuckets) {
                List<Integer> list = buckets.get(bucket);
                for(Integer num : list) {
                    nums[index++] = num;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5,2,3,1};
        new Solution().stackSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
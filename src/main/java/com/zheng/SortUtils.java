package com.zheng;

/**
 * 包含几种常用的排序算法
 * 插入排序
 * 希尔排序
 * 堆排序
 * 归并排序
 * 快速排序
 */
public class SortUtils {
	
	/**
	 * 插入排序
	 * 从index=1位置的元素开始，将后面的元素当做要插入的元素列表
	 * 分别与数组中已经存在的元素进行比较。
	 * 如果数组中元素大于当前待插入的元素，那么将数组中的元素向后移动一位
	 * 继续与前一个数组中的元素比较，直到找到一个合适的位置，将元素插入进去
	 *
	 */
	public void insertSort(Integer[] arr) {
		if (null == arr || arr.length <= 1) {
			return;
		}
		
		int tmp, j;
		for (int i = 1; i < arr.length; i++) {
			tmp = arr[i];
			j = i - 1;
			for (; j >= 0; j--) {
				if (compare(arr[j], tmp)) {
					arr[j + 1] = arr[j];
				} else {
					break;
				}
			}
			arr[j + 1] = tmp;
		}
	}
	
	/**
	 * 希尔排序，
	 * 将待排序的数组按照步长gap分成几组，每组采用插入排序,完成一次排序操作
	 * gap减小,继续上面操作，直到gap=0
	 * 建议gap = N / 2; gap /= 2;
	 *
	 */
	public void shellSort(Integer[] arr) {
		if (null == arr || arr.length <= 1) {
			return;
		}
		
		int tmp, j;
		for (int gap = arr.length / 2; gap > 0; gap /= 2) { //步长
			//----------下面是插入排序
			for (int i = gap; i < arr.length; i += gap) { //待插入元素
				tmp = arr[i];
				j = i - gap;
				for (; j >= 0; j -= gap) { //已插入元素
					if (arr[j] > tmp) {
						arr[j + gap] = arr[j];
					} else {
						break;
					}
				}
				arr[j + gap] = tmp;
			}
			//-----------
		}
	}
	
	/**
	 * 堆排序
	 * 对于堆排序，如果是顺序，则建立最大堆，然后取出根元素，递归
	 * 如果是逆序，则建立最小堆，然后取出根元素，递归
	 *
	 */
	public void stackSort(Integer[] arr) {
		if(null == arr || arr.length <= 1) {
			return;
		}
		// 留出第一个位置，
		// 便于根据索引下标计算堆节点的父节点、左右儿子节点
		Integer[] newarr = new Integer[arr.length + 1];
		newarr[0] = 0;
		for (int i = 0; i < arr.length; i++) {
			newarr[i + 1] = arr[i];
		}
		
		for (int i = newarr.length - 1; i >= 1; i--) { //要建堆的元素个数
			buildStack(newarr, i); //建堆
			moveToEnd(newarr, i); //移除最大的那个元素
		}
		
		for (int i = 1; i < newarr.length; i++) {
			arr[i - 1] = newarr[i];
		}
	}
	
	/**
	 * 删除节点，其实是将首尾位元素相互换位置
	 *
	 * @param newarr 堆数组
	 * @param i 当前最大最小值需要放置的下标位置
	 */
	private void moveToEnd(Integer[] newarr, int i) {
		int tmp = newarr[i];
		newarr[i] = newarr[1];
		newarr[1] = tmp;
	}
	
	
	/**
	 * 对给定数组范围的元素建堆，这里是构建最大堆(根节点上的值大于其儿子的值)
	 *
	 * @param arr 原始数组
	 * @param end 建堆元素从下标1到end之间的元素，这里主要是作为建堆元素的个数限制
	 */
	private void buildStack(Integer[] arr, int end) {
		int tmp, parent, cur;
		for (int i = 1; i <= end; i++) { //针对每一个元素都需要与其父节点比较，将最大的值放在父节点上
			cur = i;
			tmp = arr[cur];
			parent = i / 2;
			while (parent >= 1 && compare(tmp, arr[parent])) {
				arr[cur] = arr[parent];
				arr[parent] = tmp;
				cur = parent;
				parent /= 2;
			}
		}
	}
	
	/**
	 * 这里改变比较策略，转换排序方式：正序、逆序
	 */
	private boolean compare(int a, int b) {
		return a < b;
	}

	/**
	 * 归并排序
	 * 将排序进行分治,分而治之：分将元素分成几部分进行排序，治：将排序好的几部分组装起来
	 * 将对所有元素的排序分成两部分进行排序，然后合并，每一部分又进行划分，如此递归
	 * 对每一部分先进行排序，完成后再将两部分合并起来
	 */
	public void mergeSort(Integer[] arr) {
		if(null == arr || arr.length <= 1) {
			return;
		}
		Integer[] tmpArr = new Integer[arr.length];
		mergeSort(arr, tmpArr, 0, arr.length - 1);
	}
	
	private void mergeSort(Integer[] arr, Integer[] tmpArr, int left, int right) {
		if(left < right) {
			int center = (left + right) / 2;
			mergeSort(arr, tmpArr, left, center);
			mergeSort(arr, tmpArr, center+1, right);
			merge(arr, tmpArr, left, center+1, right);
		}
	}

	/**
	 * 合并两部分
	 * @param arr 原始排序数组
	 * @param tmpArr 临时表用于缓存中间排序结果
	 * @param leftStart 左边部分开始索引
	 * @param rightStart 右边部分开始索引
	 * @param rightEnd 右边部分结束索引
	 */
	private void merge(Integer[] arr, Integer[] tmpArr, int leftStart, int rightStart, int rightEnd) {
		if(null == arr || arr.length <= 1) {
			return;
		}
		int leftEnd = rightStart - 1;
		int tmpIndex = leftStart;
		int num = rightEnd - leftStart + 1;
		
		while(leftStart <= leftEnd && rightStart <= rightEnd) {
			if(compare(arr[leftStart], arr[rightStart])) {
				tmpArr[tmpIndex++] = arr[leftStart++];
			}else {
				tmpArr[tmpIndex++] = arr[rightStart++];
			}
		}
	
		//处理多余的部分
		while(leftStart <= leftEnd) {
			tmpArr[tmpIndex++] = arr[leftStart++];
		}
		
		while(rightStart <= rightEnd) {
			tmpArr[tmpIndex++] = arr[rightStart++];
		}
		
		//最后将tmp中的值拷贝回原来表,只有rightEnd没有变，所以用它来进行拷贝
		int index;
		for(int i = 0; i <num; i++) {
			index = rightEnd--;
			arr[index] = tmpArr[index];
		}
	}

	/**
	 * 快速排序
	 * 找到一个元素，以该元素为基准，将比他小的放在一起
	 * 比他大的放在一起，形成两部分，递归
	 * @param arr
	 */
	public void quickSort(Integer[] arr) {
		if(null == arr || arr.length <= 1) {
			return;
		}
		
		quickSort(arr, 0, arr.length - 1);
	}

	private void quickSort(Integer[] arr, int start, int end) {
		if(start < end) {
			int i = start;
			int j = end;
			int x = arr[i]; //将第一个元素作为驱动元，查找到<x的放左边，>x的放右边
			while(i < j) {
				while(i < j && arr[j] >= x) {
					j--;
				}
				if(i < j) {
					arr[i++] = arr[j];
				}

				while(i < j && arr[i] <= x) {
					i++;
				}
				if(i < j) {
					arr[j--] = arr[i];
				}
			}
			arr[i] = x; //将驱动元放入中值位置
			quickSort(arr, start, i-1); //继续排序左边部分
			quickSort(arr, i+1, end); //继续排序右边部分
		}
	}

	/**
	 * 冒泡排序,升序排列
	 * @param arr
	 */
	public void bubbleSort(Integer[] arr) {
		if (null == arr || arr.length <= 1) {
			return;
		}
		
		for(int i = 0; i < arr.length; i++) {
			for(int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
				    swap(arr, i, j);
				}					
			}
		}
	}

	/**
	 * 位算法交换两者位置
	 * @param arr
	 * @param from
	 * @param to
	 */
	private void swap(Integer[] arr, int from, int to) {
		arr[from] = arr[from] ^ arr[to];
		arr[to] = arr[from] ^ arr[to];
		arr[from] = arr[from] ^ arr[to];
	}
	
}

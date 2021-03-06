import java.io.*;
import java.lang.*;
import java.util.*;

class SortingAlgorithm {

	/**
	 * @brief       mergeSort   함수 축약형
	 *
	 * @param arr
	 */
	public static void mergeSort(int[] arr) {
		int N = arr.length;
		int[] buffer = new int[N];
		mergeSort(arr, 0, N - 1, buffer);
	}

	/**
	 * @brief           arr[left] ~ arr[right] 범위를 오름 차순으로 정렬해주는 함수
	 *
	 * @param arr
	 * @param left
	 * @param right
	 * @param buffer    병합을 위해 사용 될 임시 공간
	 */
	public static void mergeSort(int[] arr, int left, int right, int[] buffer) {
		if (left >= right) {
			// 범위에 데이터가 하나 이하라면 정렬이 불필요하다
			return;
		}

		// 영역을 반으로 나눌 중간 인덱스를 계산한다
		int mid = (left + right) / 2;

		// 좌/우 절반을 별도로 정렬한다
		mergeSort(arr, left, mid, buffer);
		mergeSort(arr, mid + 1, right, buffer);

		// 독립적으로 정렬된 두 배열을 병합한다
		concatOrderedArrays(arr, left, mid, right, buffer);
	}


	/**
	 * @brief           독립적으로 정렬 된 두 범위 arr[left]~arr[mid], arr[mid+1] ~ arr[right] 범위를 하나의 정렬된 배열로 병합하는 함수
	 *
	 * @param arr
	 * @param left
	 * @param mid
	 * @param right
	 * @param buffer    병합을 위해 사용 될 임시 공간
	 */
	public static void concatOrderedArrays(int[] arr, int left, int mid, int right, int[] buffer) {
		int leftIndex = left;       // 왼쪽 배열의 인덱스 반복자
		int rightIndex = mid + 1;    // 오른쪽 배열의 인덱스 반복자
		int bufferIndex = left;       // 버퍼 영역의 인덱스 반복자

		// 두 범위 모두 하나 이상의 원소가 남은 경우
		while (leftIndex <= mid && rightIndex <= right) {
			// 두 영역의 남은 원소들 중 더 작은 원소를 차례로 버퍼에 추가한다
			if (arr[leftIndex] <= arr[rightIndex]) {
				buffer[bufferIndex] = arr[leftIndex];
				bufferIndex++;
				leftIndex++;
			} else {
				buffer[bufferIndex] = arr[rightIndex];
				bufferIndex++;
				rightIndex++;
			}
		}

		// 왼쪽 영역만 원소가 남은 경우
		while (leftIndex <= mid) {
			buffer[bufferIndex++] = arr[leftIndex++];
		}

		// 오른쪽 영역만 원소가 남은 경우
		while (rightIndex <= right) {
			buffer[bufferIndex++] = arr[rightIndex++];
		}

		// 버퍼에 병합 된 배열을 다시 원본 배열에 복사한다
		for (int index = left; index <= right; index += 1) {
			arr[index] = buffer[index];
		}
	}
}

public class Main {
	public static final Scanner scanner = new Scanner(System.in);
	public static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		int N = scanner.nextInt();

		int[] arr = new int[N];
		for (int i = 0; i < N; i += 1) {
			arr[i] = scanner.nextInt();
		}

		SortingAlgorithm.mergeSort(arr);

		for (int i = 0; i < N; i += 1) {
			if (i > 0) {
				writer.write(" ");
			}
			writer.write(String.valueOf(arr[i]));
		}

		writer.flush();
		writer.close();
	}

}

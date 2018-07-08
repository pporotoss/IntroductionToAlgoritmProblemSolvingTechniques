package Chapter12.Problem12C;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {
	public static final Scanner scanner = new Scanner(System.in);
	public static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		String P = scanner.next();
		int M = P.length();

		int[] PI = getFailureFunctionTable(P);

		for (int i = 0; i < M; i += 1) {
			writer.write(String.format("%d\n", PI[i]));
		}

		writer.close();
	}

	/**
	 * 문자열 P에 대한 부분 매칭 테이블을 계산하여 반환해주는 함수
	 *
	 * @param P 테이블을 계산할 패턴 문자열
	 * @return 부분 매칭 테이블
	 */
	public static int[] getFailureFunctionTable(String P) {
		String S = P;
		int N = S.length(); // 탐색 대상 문자열 (자기자신)
		int M = P.length();    // 패턴 문자열
		int[] PI = new int[M];

		int begin = 1;        // 자기 자신을 찾는 경우를 무시하기 위해 1부터 시작
		int matched = 0;
		
//		for (int i = 0; i < M; i++) {
//			String subStr = P.substring(0, i);
//			for (int j = 0; j < subStr.length(); j++) {
//				String prefix = subStr.substring(0, j);
//				if(subStr.endsWith(prefix)) { // 문자열이 접두사로 끝나면
//											   // prefix는 접두사이자 접미사가 된다.
//					PI[i] = Math.max(PI[i], prefix.length());
//				}
//			}
//		}
		
		while (begin + matched < N) {
			if (S.charAt(begin + matched) == P.charAt(matched)) { // 일치할경우
				matched += 1;
				// P[0..(matched-1)] == P[begin..(begin+matched-1)] 이다.
				// 즉, 부분 문자열 P[0..(begin-matched-1)]은 좌우 matched글자 만큼 공통 접두/접미사가 있다.
				PI[begin + matched - 1] = matched;
			} else { // 일치하지 않을 경우
				// KMP와 똑같이 수행한다.
				if (matched == 0) {
					begin += 1;
				} else {
				    // int i = begin + matched
                    // PI[i] 는 계산이 되고 있다. PI[0] ~ PI[i-1]은 모두 계산 완료되었다. (i는 항상 증가하므로)
                    // matched - i =< i
					begin = begin + matched - PI[matched - 1];
					matched = PI[matched - 1];
				}
			}
		}
		return PI;
	}
}

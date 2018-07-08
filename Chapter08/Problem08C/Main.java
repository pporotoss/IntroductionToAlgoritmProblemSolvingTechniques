package Chapter08.Problem08C;

import java.io.*;
import java.lang.*;
import java.util.*;


public class Main {
	public static final int NO_PARENT = -1;

	public static final Scanner scanner = new Scanner(System.in);


	/**
	 * 두 노드 current부터 goal까지의 최장 경로의 길이를 반환하는 함수
	 *
	 * @param current
	 * @param goal
	 * @param adj
	 * @return
	 */
	public static int getLongestPathLength(int current, int goal, ArrayList<Integer>[] adj){
		int N = adj.length;

		boolean[] visited = new boolean[N];

		return getLongestPathLength(current, goal, 1, adj, visited);
	}
	
	/**
	 *
	 * @param current   현재 탐색중인 노드번호
	 * @param goal      찾아야할 노드 번호
	 * @param depth     현재 탐색 깊이 == 열린 함수의 수 == 거쳐온 간선의 수
	 * @param adj       인접리스트
	 * @param visited   방문여부
	 * @return
	 */
	public static int getLongestPathLength(int current, int goal, int depth, ArrayList<Integer>[] adj, boolean[] visited){
		
		if(current <= 0 || current >= adj.length) return 0; // 예외케이스
		
		if(visited[current] == true || current == goal){ // 기저케이스
			return 0;
		}

		int maxLength = 0;
		
		// 아직 목적지를 찾지 못한경우.
		// 나와 인접했지만, 아직 방문하지 않은 노드들을 추가로 방문 ==> 재귀탐색
//		visited[current] = true;
		for(int nextNode : adj[current]){
			
			if(visited[nextNode]) {
				continue;
			}
			
			// 현재 탐색중인 경로에 포함되었으니까 true
			visited[nextNode] = true;
			
			int length = 1 + getLongestPathLength(nextNode, goal, depth+1, adj, visited);
			maxLength = Math.max(maxLength , length);
			
			// 현재 경로에서 제거되었으니까 false
			visited[nextNode] = false;
		}
//		visited[current] = false;
		return maxLength;

	}


	public static void main(String[] args) throws Exception {
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		ArrayList<Integer>[] adj = new ArrayList[N+1];

		int origin = scanner.nextInt();
		int dest = scanner.nextInt();

		for(int i = 1; i <= N; i += 1){
			adj[i] = new ArrayList<>();
		}

		for(int i = 0 ; i < M; i += 1){
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			adj[u].add(v);
			adj[v].add(u);
		}

		int answer = getLongestPathLength(origin, dest, adj);

		System.out.println(answer);
	}

}

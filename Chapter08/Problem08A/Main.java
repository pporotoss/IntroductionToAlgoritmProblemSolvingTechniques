package Chapter08.Problem08A;

import java.lang.*;
import java.util.*;

public class Main {
	public static final Scanner scanner = new Scanner(System.in);

	/**
	 * @param N     그래프의 정점의 수
	 * @param adj   2차원 인접 행렬
	 * @return      깊이 우선 탐색으로 탐색된 노드들의 번호 리스트
	 */
	public static ArrayList<Integer> getDfsOrder(int N, boolean[][] adj){
		ArrayList<Integer> visitedNodes = new ArrayList<>();

		Stack<State> dfsStack = new Stack<>();
		State initialState = new State(1, 1);
		dfsStack.add(initialState); // 루트노드 삽입.
		
		// 노드가 방문한 적 있으면 true;
		boolean visited[] = new boolean[N+1];

		while(dfsStack.isEmpty() == false){
			State current = dfsStack.pop(); // 탐색해야 할 노드.

			if(visited[current.nodeIndex] == true){ // 이미 다른 경로에서 방문한 노드이면,
				continue;
			}

			visited[current.nodeIndex] = true; // 방문여부 설정. 한번만 방문하도록 하기 위해서.
			visitedNodes.add( current.nodeIndex );

			// 인접한 노드들을 고려대상에 추가.
			for(int next = N; next >= 1; next -= 1){ // 0번째부터 반복이 돌면, 스택구조의 특성상 역순으로 탐색하게된다.
				// 모든 노드 next 에 대해서
				if(adj[current.nodeIndex][next] == true && visited[next] == false){ // 현재 노드에서 next로 가는 간선이 존재하면,
					State nextState = new State(next, current.depth + 1); // 현재 깊이보다 1깊은 노드 삽입.
					dfsStack.push(nextState);
				}
			}
		}

		return visitedNodes;
	}

	/**
	 * @param N     그래프의 정점의 수
	 * @param adj   2차원 인접 행렬
	 * @return      너비 우선 탐색으로 탐색된 노드들의 번호 리스트
	 */
	public static ArrayList<Integer> getBfsOrder(int N, boolean[][] adj){
		ArrayList<Integer> visitedNodes = new ArrayList<>();

		Queue<State> bfsQueue = new LinkedList<>();
		State initialState = new State(1, 1);
		bfsQueue.add(initialState);

		boolean visited[] = new boolean[N+1];

		while(bfsQueue.isEmpty() == false){
			State current = bfsQueue.poll(); // 탐색 대상 노드

			if(visited[current.nodeIndex] == true){
				continue;
			}

			visited[current.nodeIndex] = true;
			visitedNodes.add( current.nodeIndex );

			for(int next = 1; next <= N; next += 1){
				if(adj[current.nodeIndex][next] == true && visited[next] == false){
					State nextState = new State(next, current.depth + 1);
					bfsQueue.add(nextState);
				}
			}
		}

		return visitedNodes;
	}

	public static void main(String[] args){
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		boolean[][] adj = new boolean[N+1][N+1];
		
		// u -> v로 가는 간선이 있으면 adj[u][v] = true;
		for(int i = 0 ; i < M; i += 1){
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			adj[u][v] = true;
			adj[v][u] = true; // 양방향이므로 반대쪽도 true.
		}

		ArrayList<Integer> dfsOrders = getDfsOrder(N, adj);
		ArrayList<Integer> bfsOrders = getBfsOrder(N, adj);

		printArrayList(dfsOrders);
		printArrayList(bfsOrders);
	}

	public static void printArrayList(ArrayList<Integer> arr){
		for(int i = 0 ; i < arr.size(); i+= 1){
			if( i > 0 ){
				System.out.print("-");
			}

			int node = arr.get(i);
			System.out.print(node);
		}
		System.out.println();
	}
}

/**
 * 현재 탐색을 하고있는 상황을 표현하는 클래스
 */
class State{
	public final int nodeIndex;     // 현재 정점의 수
	public final int depth;         // 탐색의 깊이
	public State(int nodeIndex, int depth){
		this.nodeIndex = nodeIndex;
		this.depth = depth;
	}
}
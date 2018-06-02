import java.lang.*;
import java.util.*;


public class Main {
	public static final Scanner scanner = new Scanner(System.in);

	/**
	 * 그래프의 두 노드 org에서 dest로 이동하는 가장 짧은 경로의 길이를 반환하는 함수
	 *
	 * @param org
	 * @param dest
	 * @param adj
	 * @return
	 */
	public static int getShortestPathLength(int org, int dest, ArrayList<Integer>[] adj) {
		int N = adj.length;

		boolean[] visited = new boolean[N];
		int[] distance = new int[N];
		Arrays.fill(distance, -1);

		State initialState = new State(org, 1);
		Queue<State> bfsQueue = new LinkedList<>();
		bfsQueue.add(initialState);

		while (!bfsQueue.isEmpty()) {
			State current = bfsQueue.poll();

			if (visited[current.nodeIndex] == true) {
				continue;
			}

			// 모든 노드 nodeIndex가 한번씩 (== 루트로부터의 최단거리 == current.depth -1)
			// 루트로부터의 거리가 짧은 순서대로 순환.
			visited[current.nodeIndex] = true;
			
			// 이때의 depth가 곧 거리가 된다.
			distance[current.nodeIndex] = current.depth - 1;

			for (int next : adj[current.nodeIndex]) {
				if (visited[next] == false) {
					State nextState = new State(next, current.depth + 1);
					bfsQueue.add(nextState);
				}
			}

		}
		
		// distance[dest] == false 이면, 루트로부터 탐색해서 dest로 도달한적이 없다 ==> 경로가 없다.
			return distance[dest];
	}

	public static void main(String[] args) throws Exception {
		int N = scanner.nextInt();
		int M = scanner.nextInt();

		int origin = scanner.nextInt();
		int dest = scanner.nextInt();

		ArrayList<Integer>[] adj = new ArrayList[N + 1];


		for (int i = 1; i <= N; i += 1) {
			adj[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i += 1) {
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			adj[u].add(v);
			adj[v].add(u);
		}

		int answer = getShortestPathLength(origin, dest, adj);

		System.out.println(answer);
	}

}

class State {
	public final int nodeIndex;
	public final int depth;

	public State(int nodeIndex, int depth) {
		this.nodeIndex = nodeIndex;
		this.depth = depth;
	}
}
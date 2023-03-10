import java.util.*;

public class LongestPathDAG {
    static class Edge {
        int to;
        public Edge(int to) {
            this.to = to;
        }
    }

    static void DFS(List<Edge>[] graph, int from, boolean[] visited, int[] dist, Stack<Integer> stack) {
        visited[from] = true;

        for (Edge e : graph[from]) {
            int to = e.to;

            if (!visited[to]) {
                DFS(graph, to, visited, dist, stack);
            }

            if (dist[from] == Integer.MIN_VALUE) {
                continue;
            }

            if (dist[from] + 1 > dist[to]) {
                dist[to] = dist[from] + 1;
            }
        }

        stack.push(from);
    }

    static int[] longestPath(List<Edge>[] graph, int source) {
        int vertixCount = graph.length;
        boolean[] visited = new boolean[vertixCount];
        int[] dist = new int[vertixCount];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[source] = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < vertixCount; i++) {
            if (!visited[i]) {
                DFS(graph, i, visited, dist, stack);
            }
        }

        while (!stack.isEmpty()) {
            int from = stack.pop();

            if (dist[from] == Integer.MIN_VALUE) {
                continue;
            }

            for (Edge e : graph[from]) {
                int v = e.to;

                if (dist[v] < dist[from] + 1) {
                    dist[v] = dist[from] + 1;
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        int vertixCount = 6;
        List<Edge>[] graph = new List[vertixCount];
        for (int i = 0; i < vertixCount; i++) {
            graph[i] = new ArrayList<>();
        }
        graph[0].add(new Edge(1));
        graph[0].add(new Edge(2));
        graph[1].add(new Edge(2));
        graph[1].add(new Edge(3));
        graph[2].add(new Edge(3));
        graph[2].add(new Edge(4));
        graph[2].add(new Edge(5));
        graph[3].add(new Edge(5));
        graph[3].add(new Edge(4));
        graph[4].add(new Edge(5));
        int startVertex = 1;
        int[] longestPath = longestPath(graph, startVertex);

        for (int i = 0; i < vertixCount; i++) {
            if (longestPath[i] == Integer.MIN_VALUE) {
                System.out.println("There is no path from " + startVertex + " to " + i);
            } else {
                System.out.println("Longest path from " + startVertex + " to " + i + " is " + longestPath[i]);
            }
        }
    }
}
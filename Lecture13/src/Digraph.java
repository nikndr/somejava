import edu.princeton.cs.algs4.*;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Digraph {
    private final int            V;
    private       int            E;
    private       Bag<Integer>[] adj;
    private int[] indegree;


    public Digraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.V = V;
        this.E = 0;
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    public Digraph(Digraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < V; v++)
            this.indegree[v] = G.indegree(v);
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V-1));
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        indegree[w]++;
        E++;
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    public Iterable<Integer> sinks() {
        List<Integer> sinks = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (outdegree(i) == 0) {
                sinks.add(i);
            }
        }
        return sinks;
    }

    public Iterable<Integer> sources() {
        List<Integer> sources = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                sources.add(i);
            }
        }
        return sources;
    }

    public boolean isMap() {
        for (int i = 0; i < V; i++) {
            if (adj[i].size() != 1) {
                return false;
            }
        }
        return true;
    }

    public boolean connected() {
        DepthFirstPaths dfs = new DepthFirstPaths(this, 0);
        for (int i = 1; i < V; i++) {
            if (!dfs.has_path_to(i)) {
                return false;
            }
        }
        return true;
    }

    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V+" vertices, "+E+" edges\n");
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int[] getIndegree() {
        return indegree;
    }

}
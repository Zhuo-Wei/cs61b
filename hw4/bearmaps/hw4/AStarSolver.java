package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    // timeout passed in is in seconds.
    ArrayHeapMinPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo; // distance to the start
    private HashMap<Vertex, Double> h;//heuristic // distance to the target
    private HashMap<Vertex, Vertex> edgeTo;
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private double timeSpent;
    private int numStatesExplored;
    private final double inf = Double.POSITIVE_INFINITY;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        numStatesExplored = 0;
        pq = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        h = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new LinkedList<>();
        solutionWeight = 0;
        // algrithem starts
        Stopwatch sw = new Stopwatch();
        distTo.put(start, 0.0);
        pq.add(start,distTo.get(start));

        while (pq.size() != 0) {
            // check whether get the end
            if (pq.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solutionWeight = distTo.get(end);
                Vertex current = end;
                //input vertexes in shortest path into solution
                while (!current.equals(start)) {
                    solution.addFirst(current);
                    current = edgeTo.get(current);
                }
                solution.addFirst(start);
                timeSpent = sw.elapsedTime();
                return;
            }

            //check the time
            timeSpent = sw.elapsedTime();
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }

            //start traverse
            Vertex p = pq.removeSmallest();
            if (!h.containsKey(p)) {
                h.put(p, input.estimatedDistanceToGoal(p, end));
            }
            numStatesExplored += 1;

            List<WeightedEdge<Vertex>> edges = input.neighbors(p);
            for (WeightedEdge<Vertex> edge : edges) {
                if (!h.containsKey(edge.to())) {
                    h.put(edge.to(), input.estimatedDistanceToGoal(edge.to(), end));
                }
                relax(edge);
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
        //return;
    }
    private double getPiority(Vertex v) {
        double p = distTo.get(v) + h.get(v);
        return p;
    }

    private void relax(WeightedEdge edge) {
        Vertex p = (Vertex)edge.from();
        Vertex q = (Vertex)edge.to();
        double w = edge.weight();

        //initialize disTo
        if (!distTo.containsKey(q)) {
            distTo.put(q, inf);
        }
        double from = distTo.get(p) + w;
        double to = distTo.get(q);
        if (from < to) {
            distTo.put(q, from);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, getPiority(q));
            }
            else pq.add(q, getPiority(q));
        }



    }
    public SolverOutcome outcome() {
        return this.outcome;
    }
    public List<Vertex> solution() {
        return this.solution;
    }
    public double solutionWeight() {
        return this.solutionWeight;
    }
    public int numStatesExplored() {
        return this.numStatesExplored;
    }
    public double explorationTime() {
        return this.timeSpent;
    }
}

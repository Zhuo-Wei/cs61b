package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    // timeout passed in is in seconds.
    ArrayHeapMinPQ<Vertex> priorityQ;
    private HashMap<Vertex, Double> disTo;
    private HashMap<Vertex, Double> h;//heuristic
    private HashMap<Vertex, Vertex> edgeTo;
    private SolverOutcome outcome;
    private double solutionWeight;
    private ArrayList<Vertex> solution;
    private double timeSpent;
    private int numStatesExplored;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        numStatesExplored = 0;
        ArrayHeapMinPQ<Vertex> priorityQ = new ArrayHeapMinPQ<>();
        HashMap<Vertex, Double> disTo = new HashMap<>();
        HashMap<Vertex, Double> h = new HashMap<>();
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        priorityQ.add(start,1);

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
        return this. numStatesExplored;
    }
    public double explorationTime() {
        return this.timeSpent;
    }
}

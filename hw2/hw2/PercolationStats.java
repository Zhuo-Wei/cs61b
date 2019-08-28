package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] fractions;
    private int times;
    private Percolation[] percolation;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        times = T;
        fractions = new double[T];
        int totalNum = N * N;
        for (int i = 0; i < T; i += 1) {
            this.percolation[i] = pf.make(N);
            while (!percolation[i].percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                percolation[i].open(row, col);

            }
            fractions[i] = (double) percolation[i].numberOfOpenSites() / totalNum;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double u = mean();
        double sigma = stddev();
        return u - (1.96 * sigma / Math.sqrt(times));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double mu = mean();
        double sigma = stddev();
        return mu + (1.96 * sigma / Math.sqrt(times));
    }
}

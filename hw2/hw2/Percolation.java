package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int size;
    private int top;
    private int bottom;
    private int numOfOpenSites;
    private WeightedQuickUnionUF wquf;
    private WeightedQuickUnionUF wqufNoBottom;
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        grid = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = false;
            }
        }
        //the last nod is N*N-1 in the right bottom
        this.top = N * N;
        this.bottom =  N * N + 1;
        this.numOfOpenSites = 0;
        wquf = new WeightedQuickUnionUF(N * N + 2);
        wqufNoBottom = new WeightedQuickUnionUF(N * N + 1);
    }
    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int xyTo1D(int r, int c) {
        return r * size + c;
    }

    private int[] up(int r, int c) {
        int[] pos = new int[2];
        if (r > 0) {
            r--;
        }
        validate(r, c);
        pos[0] = r;
        pos[1] = c;
        return pos;
    }

    private int[] down(int r, int c) {
        int[] pos = new int[2];
        if (r < size - 1) {
            r++;
        }
        validate(r, c);
        pos[0] = r;
        pos[1] = c;
        return pos;
    }

    private int[] left(int r, int c) {
        int[] pos = new int[2];
        if (c > 0) {
            c--;
        }
        validate(r, c);
        pos[0] = r;
        pos[1] = c;
        return pos;
    }

    private int[] right(int r, int c) {
        int[] pos = new int[2];
        if (c < size - 1) {
            c++;
        }
        validate(r, c);
        pos[0] = r;
        pos[1] = c;
        return pos;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numOfOpenSites++;
        }
        if (row == 0) {
            wquf.union(xyTo1D(row, col), top);
            wqufNoBottom.union(xyTo1D(row, col), top);
        }
        if (row == size - 1) {
            wquf.union(xyTo1D(row, col), bottom);
        }
        if (row > 0 && isOpen(up(row, col)[0], up(row, col)[1])) {
            wquf.union(xyTo1D(row, col), xyTo1D(up(row, col)[0], up(row, col)[1]));
            wqufNoBottom.union(xyTo1D(row, col), xyTo1D(up(row, col)[0], up(row, col)[1]));
        }
        if (row < size - 1 && isOpen(down(row, col)[0], down(row, col)[1])) {
            wquf.union(xyTo1D(row, col), xyTo1D(down(row, col)[0], down(row, col)[1]));
            wqufNoBottom.union(xyTo1D(row, col), xyTo1D(down(row, col)[0], down(row, col)[1]));
        }
        if (col > 0 && isOpen(left(row, col)[0], left(row, col)[1])) {
            wquf.union(xyTo1D(row, col), xyTo1D(left(row, col)[0], left(row, col)[1]));
            wqufNoBottom.union(xyTo1D(row, col), xyTo1D(left(row, col)[0], left(row, col)[1]));
        }
        if (col < size - 1 && isOpen(right(row, col)[0], right(row, col)[1])) {
            wquf.union(xyTo1D(row, col), xyTo1D(right(row, col)[0], right(row, col)[1]));
            wqufNoBottom.union(xyTo1D(row, col), xyTo1D(right(row, col)[0], right(row, col)[1]));
        }
    }
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];

    }  // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        validate(row, col);
        return wqufNoBottom.connected(xyTo1D(row, col), top);

    }
    // is the site (row, col) full?
    public int numberOfOpenSites() {
        return numOfOpenSites;

    }
    // number of open sites
    public boolean percolates() {
        return wquf.connected(bottom, top);
    }
    // does the system percolate?
    public static void main(String[] args) {


    }  // use for unit testing (not required)

}

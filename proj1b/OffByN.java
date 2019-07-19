public class OffByN implements CharacterComparator{
    private int n;

    public OffByN(int N){
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y){
        int diff = x - y;
        if(Math.abs(diff) == this.n){
            return true;
        }

        return false;
    }

}

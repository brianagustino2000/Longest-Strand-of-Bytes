import java.util.ArrayList;

public class Nodes {
    int len;
    int offset1;
    int offset2;
    String resultString;
    ArrayList<String> commonFiles;
    ArrayList<Integer> offsetArrays;

    Nodes(int l, int o1, int o2, String rs) {
        len = l;
        offset1 = o1;
        offset2 = o2;
        resultString = rs;
    }

    Nodes(int l ,ArrayList<Integer> offsetA, ArrayList<String> cf) {
        len = l;
        offsetArrays = offsetA;
        commonFiles = cf;
    }

    public int getLength() {
        return len;
    }

    public int getOffset1() {
        return offset1;
    }

    public int getOffset2() {
        return offset2;
    }

    public String getResultString() {
        return resultString;
    }

    public ArrayList<String> getCommonFiles() {
        return commonFiles;
    }

    public ArrayList<Integer> getOffsetArrays() {
        return offsetArrays;
    }

    public void setLength(int newLength) {
        len = newLength;
    }

    public void setOffset(int newOffset) {
        offset1 = newOffset;
    }

    public void setResultString(String newString) {
        resultString = newString;
    }

    public void setCommonFiles(ArrayList<String> newFiles) {
        commonFiles = newFiles;
    }
}

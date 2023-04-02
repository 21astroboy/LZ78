import java.io.UnsupportedEncodingException;


public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
    LZ78 lz = new LZ78();
    String input = "aabacdab";
        System.out.println(input);
        String encoded = Huffman.encode(input);
        System.out.println("Encoded string: " + encoded);
    }
}
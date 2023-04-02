import java.util.*;
public class LZ78 {
    private String input;
    private String coded;
    public  String compress(String input) {
        this.input=input;
        Map<Integer, String> dictionary = new HashMap<>();
        int counter = 1;
        HashMap<Integer,String> list = new HashMap<>();
        String current = "";
        for (char c : input.toCharArray()) {
            String temp = current + c;
            if (list.containsValue(temp)) {
                current = temp;
            } else {
                list.put(counter,temp);
                char[] prev = temp.toCharArray();
                String prevSeq = "";
                for(int i = 0; i < prev.length-1;i++){
                    prevSeq+=prev[i];
                }
                if(prevSeq.toCharArray().length==0)
                    dictionary.put(counter++,"0"+temp);
                else{
                    int index = 0;
                    Set<Map.Entry<Integer,String>> entrySet =
                            list.entrySet();
                    for(Map.Entry<Integer,String> pair: entrySet){
                        if(prevSeq.equals(pair.getValue()))
                            index = pair.getKey();
                    }
                    dictionary.put(counter++,String.valueOf(index)+String.valueOf(c));
                }
                current = "";
            }
        }

        String result = "";
        Collection<String> collection = dictionary.values();
        for(var el: collection){
            result+= el;
        }
        return result;
    }
    public String decompress(String coded) {
        this.coded = coded;
        List<String> dictionary = new ArrayList<>();
        List<String> decompressed = new ArrayList<>();
        char[] Text = coded.toCharArray();
        for(int i = 0; i< coded.length(); i+=2){
            String extra = String.valueOf(Text[i])+String.valueOf(Text[i+1]);
            dictionary.add(extra);
        }
        for (int i = 0; i < dictionary.size(); i++) {
            String el = dictionary.get(i);
            char[] extra = el.toCharArray();
            int link = Integer.parseInt(String.valueOf(extra[0]));
            while (link!=0){
                el = dictionary.get(link-1) + el;
                char [] extra2 =el.toCharArray();
                link = Integer.parseInt(String.valueOf(extra2[0]));
            }

            decompressed.add(el);
        }
        String result ="";

        for(var el : decompressed){
            char [] extra = el.toCharArray();
            for(int i = 1; i < el.length(); i+=2){
                result+=String.valueOf(extra[i]);
            }
        }
        return result;
    }
}


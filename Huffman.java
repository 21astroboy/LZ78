import java.util.HashMap;
import java.util.PriorityQueue;


public class Huffman {
        // Класс, который представляет узел дерева Хаффмана
        private static class HuffmanNode implements Comparable<HuffmanNode> {
            private final char ch;
            private final int freq;
            private final HuffmanNode left;
            private final HuffmanNode right;

            private HuffmanNode(char ch, int freq, HuffmanNode left, HuffmanNode right) {
                this.ch = ch;
                this.freq = freq;
                this.left = left;
                this.right = right;
            }

            // Сравнение узлов по частоте
            @Override
            public int compareTo(HuffmanNode other) {
                return this.freq - other.freq;
            }

            // Проверка, является ли узел листом
            private boolean isLeaf() {
                return this.left == null && this.right == null;
            }
        }

        // Создание таблицы частот для каждого символа в строке
        private static HashMap<Character, Integer> buildFrequencyTable(String input) {
            HashMap<Character, Integer> freqTable = new HashMap<>();

            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                freqTable.put(ch, freqTable.getOrDefault(ch, 0) + 1);
            }

            return freqTable;
        }

        // Создание дерева Хаффмана на основе таблицы частот
        private static HuffmanNode buildHuffmanTree(HashMap<Character, Integer> freqTable) {
            PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();

            // Добавление листьев в очередь приоритетов
            for (char ch : freqTable.keySet()) {
                pq.offer(new HuffmanNode(ch, freqTable.get(ch), null, null));
            }

            // Объединение узлов, пока не останется только один корень
            while (pq.size() > 1) {
                HuffmanNode left = pq.poll();
                HuffmanNode right = pq.poll();
                pq.offer(new HuffmanNode('\0', left.freq + right.freq, left, right));
            }

            return pq.poll();
        }

        // Создание таблицы кодов Хаффмана для каждого символа в дереве
        private static HashMap<Character, String> buildHuffmanTable(HuffmanNode root) {
            HashMap<Character, String> huffmanTable = new HashMap<>();
            buildHuffmanTableHelper(root, "", huffmanTable);
            return huffmanTable;
        }

        // Вспомогательный метод для создания таблицы кодов Хаффмана
        private static void buildHuffmanTableHelper(HuffmanNode node, String code, HashMap<Character, String> table) {
            if (node.isLeaf()) {
                table.put(node.ch, code);
            } else {
                buildHuffmanTableHelper(node.left, code + "0", table);
                buildHuffmanTableHelper(node.right, code + "1", table);
            }
        }

        // Кодирование строки с использованием таблицы кодов Хаффмана
        public static String encode(String input) {
            HashMap<Character, Integer> freqTable = buildFrequencyTable(input);
            HuffmanNode root = buildHuffmanTree(freqTable);
            HashMap<Character, String> huffmanTable = buildHuffmanTable(root);

            StringBuilder encoded = new StringBuilder();
            // Проход по строке и добавление соответствующих кодов в выходную строку
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                encoded.append(huffmanTable.get(ch));
            }

            return encoded.toString();
        }

    // Декодирование закодированной строки с использованием дерева Хаффмана
    public static String decode(String encoded, HuffmanNode root) {
        StringBuilder decoded = new StringBuilder();
        HuffmanNode current = root;

        // Проход по закодированной строке и перемещение по дереву Хаффмана
        for (int i = 0; i < encoded.length(); i++) {
            char bit = encoded.charAt(i);
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }

            // Если достигнут лист, то добавить соответствующий символ в выходную строку
            if (current.isLeaf()) {
                decoded.append(current.ch);
                current = root;
            }
        }

        return decoded.toString();
    }

}


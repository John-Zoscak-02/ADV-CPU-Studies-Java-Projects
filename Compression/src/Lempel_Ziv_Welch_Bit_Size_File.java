import com.sun.tools.javac.util.StringUtils;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Lempel_Ziv_Welch_Bit_Size_File {

    private static Map<String, Integer> dictionary;
    private static int binaryCounter;
    private static PrintWriter printWriter;
    private static int bitLevel;

    public static void main(String[] args) {

        dictionary = new HashMap<>();
        try {
            File f = new File("C:\\Users\\jmz00\\Git\\ADV-CPU-Studies-Java-Projects\\Compression\\src\\thingy.txt");
            Scanner scan = new Scanner(f);

            binaryCounter = 128;
            double lengthBefore = 0d;
            int lengthAfter = 0;
            bitLevel = 9;
            printWriter = new PrintWriter("thingycompressed.txt", "UTF-8");

            StringBuilder stringBuilder = new StringBuilder();
            while (scan.hasNext()) {
                String str = scan.nextLine();
                lengthBefore += str.length() * 8;
                ArrayList<String> binaryVals = compress(str);
                System.out.println( binaryVals );
                lengthAfter += binaryVals.size() * bitLevel;
                stringBuilder.append( print( binaryVals ) ).append("\n");
            }

            printWriter.print( stringBuilder.toString() );
            System.out.println("Compression Ratio: " + lengthBefore/lengthAfter + " :1");
            System.out.println("Bit Level: " + bitLevel );
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static ArrayList<String> compress( String str ) {
            int binaryCounter = 128;
            int i = 0;
            ArrayList<String> binaryVals = new ArrayList<>();

            if (str.length() > 1) {
                while (i < str.length()) {
                    StringBuilder current = new StringBuilder();
                    current.append(str.charAt(i));
                    char next = '\u0000';
                    if (i < str.length() - 1) {
                        next = str.charAt(i + 1);
                    }
                    boolean atEnd = false;

                    while (dictionary.containsKey(current.toString() + next) && i < str.length() - 1) {
                        current.append(next);
                        if (i < str.length() - 2) {
                            i++;
                            next = str.charAt(i + 1);
                        } else {
                            next = '\u0000';
                            i++;
                            atEnd = true;
                        }
                    }
                    if (i >= str.length() - 1) {
                        atEnd = true;
                    }

                    if (!atEnd) {
                        //System.out.println( i + ": " + current.toString() );
                        binaryVals.add(current.toString());
                        dictionary.put(current.append(next).toString(), binaryCounter++);
                        if ( Integer.toBinaryString( binaryCounter ).length() == bitLevel) {
                            bitLevel++;
                        }
                    } else {
                        //System.out.println( "Last: " + current.toString() );
                        binaryVals.add(current.toString());
                    }
                    i++;
                }
                return binaryVals;
            }
            return binaryVals;
        }

        public static String print (ArrayList < String > binaryVals) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder decimal = new StringBuilder();
            for (String s : binaryVals) {
                if (dictionary.containsKey(s)) {
                    stringBuilder.append(resizeBinaryToBitLevel(dictionary.get(s), bitLevel));

                    decimal.append(dictionary.get(s) + " ");
                } else {
                    if ((int) (s.charAt(0)) < 128) {
                        stringBuilder.append(resizeBinaryToBitLevel((int) (s.charAt(0)), bitLevel));
                    } else {
                        stringBuilder.append(String.format(" [%s] ", s));
                    }
                    decimal.append((int) s.charAt(0) + " ");
                }
            }
            //System.out.println( stringBuilder.toString() );
            //System.out.println( decimal.toString());
            //printWriter.append(stringBuilder.toString());

            return stringBuilder.toString();
        }

        private static String resizeBinaryToBitLevel (Integer val,int bitLevel ){
            StringBuilder stringBuilder = new StringBuilder();
            String binary = Integer.toBinaryString(val);
            for (int i = 0; i < bitLevel; i -= -1) {
                if (binary.length() > i) {
                    stringBuilder.append(binary.charAt(i));
                } else {
                    stringBuilder.append(0);
                }
            }
            return stringBuilder.toString();
        }

}
import javax.print.DocFlavor;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Lempel_Ziv_Welch_Bit_Size_File {

    private static Map<String, Integer> dictionary;
    private static int binaryCounter;
    private static PrintWriter printWriter;
    private static int bitLevel;

    public static void main(String[] args) {

        dictionary = new TreeMap<>();
        try {
            File f = new File("C:\\Users\\jmz00\\Git\\ADV-CPU-Studies-Java-Projects\\Compression\\src\\uncompressed.txt");
            Scanner scan = new Scanner(f);

            binaryCounter = 128;
            double lengthBefore = 0d;
            int lengthAfter = 0;
            bitLevel = 8;
            printWriter = new PrintWriter("compressed.txt", "UTF-8");

            StringBuilder stringBuilder = new StringBuilder();
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                //System.out.println( str );
                lengthBefore += str.length() * 8;
                ArrayList<String> binaryVals = compress(str);
                //System.out.println( binaryVals );
                lengthAfter += binaryVals.size();
                stringBuilder.append( print( binaryVals ) );
            }

            printWriter.print( stringBuilder.toString() );
            printCompressionRatios( lengthBefore, lengthAfter );
            printWriter.close();
            System.out.println( bitLevel );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printCompressionRatios( double lengthBefore, int lengthAfter ) {
        for ( int i = 9; i <= bitLevel; i-=-1 ) {
            System.out.println( "Bit Level: " + i + "  Compression Ratio: " + (lengthBefore/(lengthAfter*i)) );
        }
    }

    public static ArrayList<String> compress( String str ) {
        int i = 0;
        ArrayList<String> binaryVals = new ArrayList<>();

        if ( str.length() > 1 ) {
            while ( i < str.length() ) {
                StringBuilder current = new StringBuilder( );
                current.append( str.charAt( i ) );
                char next = '\u0000';
                if ( i < str.length() - 1 ) {
                    next = str.charAt(i + 1);
                }
                boolean atEnd = false;

                while ( dictionary.containsKey( current.toString() + next ) && i < str.length() - 1 ) {
                    current.append( next );
                    if ( i < str.length() - 2) {
                        i++;
                        next = str.charAt( i + 1 );
                    }
                    else {
                        next = '\u0000';
                        i++;
                        atEnd = true;
                    }
                }
                if ( i >= str.length() ) {
                    atEnd = true;
                }

                if ( !atEnd ) {
                    //System.out.println( i + ": " + current.toString() );
                    binaryVals.add( current.toString() );
                    if ( Integer.toBinaryString( binaryCounter ).length() == bitLevel + 1) {
                        bitLevel++;
                    }
                    dictionary.put( current.append(next).toString(),  binaryCounter++ );
                }
                else {
                   // System.out.println( "Last: " + current.toString() );
                    binaryVals.add( current.toString() );
                    binaryVals.add( "\n" );
                    dictionary.put(current.toString() + "\n", binaryCounter++ );
                }
                i++;
            }
            return binaryVals;
        }
        binaryVals.add( str.charAt( i ) + "\n" );
        return binaryVals;
    }

        public static String print (ArrayList < String > binaryVals) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder decimal = new StringBuilder();
            for (String s : binaryVals) {
                if (dictionary.containsKey(s)) {
                    stringBuilder.append(resizeBinaryToBitLevel(dictionary.get(s), bitLevel));
                    //decimal.append(dictionary.get(s) + " ");
                } else if ( s.endsWith("\n")) {
                    stringBuilder.append( "\n" );
                } else {
                    if ((int) (s.charAt(0)) < 128) {
                        stringBuilder.append(resizeBinaryToBitLevel((int) (s.charAt(0)), bitLevel));
                    } else {
                        stringBuilder.append(String.format(" [%s] ", s));
                    }
                    //decimal.append((int) s.charAt(0) + " ");
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
                    stringBuilder.insert(0, 0);;
                }
            }
            return stringBuilder.toString();
        }

}
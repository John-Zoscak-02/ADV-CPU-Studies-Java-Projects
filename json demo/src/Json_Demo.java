import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.util.Arrays;

public class Json_Demo {
    public static void main( String args[] ) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Book[] books = null;
        try {
            books = gson.fromJson( new FileReader( "./src/books.json" ), Book[].class );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        Arrays.sort( books );

        for  ( Book b : books ) {
            System.out.println( b.toString() );
        }
    }

}

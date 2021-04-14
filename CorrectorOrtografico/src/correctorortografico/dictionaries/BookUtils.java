
package correctorortografico.dictionaries;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookUtils {
    
    static Properties parseRule( String wikiText, String tempalateName, int fromIndex ) {
        int start = findTemplate( wikiText, tempalateName, fromIndex );
        if( start > 0 ) {
            final int length = wikiText.length();
            int braces = 2;
            for( int end = start; end < length; end++ ) {
    
                switch( wikiText.charAt( end ) ) {
                    case '{':
                        braces++;
                        break;
                    case '}':
                        if( --braces == 0 ) {
                            return BookUtils.parseRule( wikiText, start, end - 1 );
                        }
                        break;
                }
            }
        }
        return null;
    }


    static Properties parseRule( String wikiText, int idxStart, int idxEnd ) {
        Properties props = new Properties();
        int idxName = 1;
        
        int bracket = 0;
        int valueStart = idxStart;
        for( ; idxStart < idxEnd; idxStart++ ) {
            char ch = wikiText.charAt( idxStart );
            switch( ch ) {
                case '[':
                    bracket++;
                    break;
                case ']':
                    bracket--;
                    break;
                case '|':
                    if( bracket == 0 ) {
                        String value = wikiText.substring( valueStart, idxStart ).trim();
                        int idx = value.indexOf( '=' );
                        if( idx > 0 ) {
                            String name = value.substring( 0, idx );
                            value = value.substring( idx + 1 );
                            props.setProperty( name.trim(), value.trim() );
                        } else {
                            props.setProperty( Integer.toString( idxName++ ), value.trim() );
                        }
                        valueStart = idxStart + 1;
                    }
            }
        }
        String value = wikiText.substring( valueStart, idxEnd ).trim();
        int idx = value.indexOf( '=' );
        if( idx > 0 ) {
            String name = value.substring( 0, idx );
            value = value.substring( idx + 1 );
            props.setProperty( name.trim(), value.trim() );
        } else {
            props.setProperty( Integer.toString( idxName++ ), value.trim() );
        }
        return props;
    }


    static int findTemplate( String wikiText, String tempalateName, int fromIndex ) {
        Pattern pattern = Pattern.compile( "\\{\\{\\s*\\Q" + tempalateName.replace( " ", "\\E\\s+\\Q" ) + "\\E\\s*\\|" );
        Matcher matcher = pattern.matcher( wikiText );
    
        if( matcher.find( fromIndex ) ) {
            return matcher.end();
        }
    
        return -1;
    }
    
 
    public static int calcDiff(String word1, String word2){
        int diff = 0;
        int j=0;
        for(int i=0; i<word1.length(); i++){
            char ch1 = word1.charAt( i );
            if(word2.length()<=j){
                diff++;
                continue;
            }
            char ch2 = word2.charAt( j++ );
            if(ch1 == ch2){
                continue;
            }
            diff++;
            if(word2.length() > j && word2.charAt( j ) == ch1){
                j++;
                continue;
            }
            if(word1.length() > (i+1) && word1.charAt( i+1 ) == ch2){
                i++;
                continue;
            }
        }
        diff += word2.length()-j;
        return diff;
    }

}


package correctorortografico.dictionaries;

import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import correctorortografico.elements.Utils;

public class Parser extends DefaultHandler{

    
    private final BookGenerator generator;
    
    private final int NONE  = 0;
    private final int TITLE = 1;
    private final int TEXT = 2;
    
    
    private int currentTag;
    private final StringBuilder data = new StringBuilder();
    private String word;
    private String text;
    
    Parser(BookGenerator generator, InputStream stream) throws Exception{
        this.generator = generator;
        
        System.setProperty("entityExpansionLimit", "100000000");
        InputSource input = new InputSource( new InputStreamReader(stream, "utf8") ); 
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        ParserAdapter pa = new ParserAdapter(sp.getParser());
        pa.setContentHandler(this);
        pa.parse(input);
    }
    
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        switch(currentTag){
            case TITLE:
            case TEXT:
                data.append( ch, start, length );
                break;
        }
    }

    
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        data.setLength( 0 );
        if("title".equals(localName)){
            currentTag = TITLE;
        }else
        if("text".equals(localName)){
            currentTag = TEXT;
        }
    }
    
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        switch(currentTag){
            case TITLE:
                word = data.toString();
                word = Utils.replaceUnicodeQuotation( word );
                if(!generator.isValidWord(word)){
                    word = null;
                }
                break;
            case TEXT:
                text = data.toString();
                break;
            default:
                if("page".equals(localName)){
                    if(word != null){
                        try{
                            generator.getBook().incTitleCount();
                            text = Utils.replaceUnicodeQuotation( text );
                            if(generator.isValidLanguage(word, text)){
                                generator.getBook().incLanguageTitleCount();
                                generator.addWord(word);
                            }
                        }catch(Throwable th){
                            th.printStackTrace();
                        }
                    }
                }
        }
        currentTag = NONE;
    }

}

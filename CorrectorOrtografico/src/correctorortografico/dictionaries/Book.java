
package correctorortografico.dictionaries;

import java.util.HashSet;

class Book {

    private int charCount;
    private HashSet list = new HashSet();
    private int titleCount;
    private int titleCountLanguage;

    final protected void addWord(String word){
        if(list.add( word )){            
            if(list.size() % 1000 == 0){
                System.out.println("Word count:"+list.size());
            }
            charCount += word.length();
        }
    }
    

    void incTitleCount(){
        titleCount++;
    }
    

    void incLanguageTitleCount(){
        titleCountLanguage++;
    }
    

    int getTitleCount(){
        return titleCount;
    }
    

    int getLanguageTitleCount(){
        return titleCountLanguage;
    }
    

    int getWordCount(){
        return list.size();
    }


    int getCharCount(){
        return charCount;
    }
    

    String[] getWords(){
        return (String[])list.toArray(new String[list.size()]);
    }
}

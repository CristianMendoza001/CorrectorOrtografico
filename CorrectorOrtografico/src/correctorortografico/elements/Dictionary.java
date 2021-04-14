
package correctorortografico.elements;

import java.io.*;
import java.util.zip.*;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

final class Dictionary extends DictionaryBase{

    public Dictionary(){
        tree = new char[10000];
        tree[size++] = LAST_CHAR;
    }

    public Dictionary(char[] tree){
        super(tree);
    }
    

    public long save(String filename) throws IOException{
        File file = new File(filename);
        FileOutputStream fos = new FileOutputStream(file);
        save(fos);
        return file.length();
    }

    public void save(OutputStream stream) throws IOException{
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        DeflaterOutputStream zip = new DeflaterOutputStream(stream, deflater);
        for(int i=0; i<size; i++){
            zip.write(tree[i]);
            zip.write(tree[i] >> 8);
        }
        
        zip.flush();
        zip.close();
    }

    public void load(String filename) throws IOException{
        FileInputStream fos = new FileInputStream(filename);
        load(fos);
    }

    public void load(InputStream stream)  throws IOException{
        InputStream zip = new InflaterInputStream(stream);
        zip = new BufferedInputStream(zip);
        size = 0;
        while(zip.available() > 0){
            char c = (char)(zip.read() + (zip.read() << 8));
            checkSize(size+1);
            tree[size++] = c;
        }
        zip.close();
        trimToSize();
    }
    

    void trimToSize(){
        char[] temp = new char[size];
        System.arraycopy( tree, 0, temp, 0, size );
        tree = temp;
    }


    public void add(String word){
        idx = 0;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            searchCharOrAdd( c );
            if(i == word.length()-1){
                tree[idx+1] |= 0x8000;
                return;
            }
            int nextIdx = readIndex();
            if(nextIdx == 0){
                idx = createNewNode();
            }else{
                idx = nextIdx;
            }
        }
    }

    public char[] toArray(){
        char[] puffer = new char[size];
        System.arraycopy(tree, 0, puffer, 0, size);
        return puffer; 
    }
    
    public int getDataSize(){
        return size;
    }
    
    
    private void searchCharOrAdd(char c){
        if(c == LAST_CHAR)
            throw new RuntimeException("Invalid Character");
        while(idx<size && tree[idx] < c){
            idx += 3;
        }
        if(idx>=size)
            throw new RuntimeException("Internal Error");
        if(tree[idx] == c){
            return;
        }
        insertChar(c);
        return;
    }

    private void insertChar(char c) {
        checkSize(size+3);
        System.arraycopy(tree, idx, tree, idx+3, size-idx);
        tree[idx] = c;
        tree[idx+1] = 0;
        tree[idx+2] = 0;
        size += 3;
        for(int i=0; i<size; ){
            if(tree[i] == LAST_CHAR){
                i++;
            }else{
                int index = (tree[i+1]<<16) + tree[i+2];
                int indexValue = index & 0x7fffffff;
                if(indexValue > idx){
                    index += 3;
                    tree[i+1] = (char)(index >> 16);
                    tree[i+2] = (char)(index);
                }
                i += 3;
            }
        }
    }

    private final int createNewNode() {
        checkSize(size+1);
        tree[idx+1] |= (char)(size >> 16);
        tree[idx+2] |= (char)(size);
        idx = size;
        tree[idx  ] = LAST_CHAR;
        size += 1;
        return idx;
    }

    private final void checkSize(int newSize){
        if(newSize > tree.length){
            char[] puffer = new char[Math.max(newSize, 2*tree.length)];
            System.arraycopy(tree, 0, puffer, 0, size);
            tree = puffer;
        }
    }
}

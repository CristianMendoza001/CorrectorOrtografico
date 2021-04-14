
package correctorortografico.elements;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

class LowMemoryArrayList<E> {

    private transient E[] elementData;


    public int size() {
        return elementData == null ? 0 : elementData.length;
    }

    public E get( int index ) {
        return elementData[index];
    }

    public void add( E o ) {
        int size = size();
        Object tempData[] = new Object[size + 1];
        if( size > 0 ) {
            System.arraycopy( elementData, 0, tempData, 0, size );
        }
        elementData = (E[])tempData;
        elementData[size] = o;
    }

    public void add( int index, E element ) {
        int size = size();
        Object tempData[] = new Object[size + 1];
        if( size > 0 ) {
            System.arraycopy( elementData, 0, tempData, 0, index );
            System.arraycopy( elementData, index, tempData, index + 1, size - index );
        }
        elementData = (E[])tempData;
        elementData[index] = element;
    }

}

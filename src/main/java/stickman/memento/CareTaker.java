package stickman.memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

    private List<Memento> mementoList = new ArrayList<Memento>();

    /**
     * add Memento as stored in the list.
     *
     */
    public void add(Memento state){
        mementoList.add(state);
    }

    /**
     * get Memento based on the index value in the stored Memento list
     *
     * @return the Memento of the given index.
     */

    public Memento get(int index){
        return mementoList.get(index);
    }
    /**
     * get the size of Memento list
     *
     * @return the size of Memento list.
     */
    public int getListSize(){
        return mementoList.size();
    }
}


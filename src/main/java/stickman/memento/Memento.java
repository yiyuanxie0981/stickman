package stickman.memento;

import stickman.model.engine.GameEngine;
import stickman.model.entity.Entity;

import java.util.List;
import java.util.Map;

public class Memento {
    private Map<String,List> data;
    /**
     * constructor to setup a new Memento
     *
     */
    public Memento(Map<String,List> data){
        this.data = data;
    }
    /**
     * add Memento as stored in the list.
     *
     * @return the stored state map.
     */
    public Map<String,List> getState(){
        return data;
    }


}


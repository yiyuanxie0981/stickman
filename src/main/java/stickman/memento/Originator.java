package stickman.memento;

import stickman.model.engine.GameEngine;
import stickman.model.entity.Entity;

import java.util.List;
import java.util.Map;

public class Originator {
    private Map<String,List> data;

    public void setState(Map<String,List> data){
        this.data = data;
    }

    public Map<String,List> getState(){
        return data;
    }

    public Memento saveStateToMemento(){
        return new Memento(data);
    }

    public void getStateFromMemento(Memento memento){
        data = memento.getState();
    }

}

package it.unibo.oop.lab.mvc;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ControllerImpl implements Controller {

    private final List<String> history = new ArrayList<>();
    private Optional<String> nextString = Optional.empty();
    
    public void setNextStringToPrint(final String next) {
        if(!next.isEmpty()) {
            nextString = Optional.of(next);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public String getNextStringToPrint() {
        if(nextString.isPresent()) {
            return nextString.get();
        } else {
            return null;
        }
    }

    public List<String> getHistory() {
        return this.history;
    }

    
    public void printCurrentString() {
        if(nextString.isPresent()) {
            System.out.println(nextString.get());
            history.add(nextString.get());
        }
        else {
            throw new IllegalStateException();
        }
    }

}

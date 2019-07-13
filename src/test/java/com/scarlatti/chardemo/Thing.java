package com.scarlatti.chardemo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 7/13/2019
 */
public class Thing {

    private String prop1;
    private String prop2;
    private List<StuffThing> stuffThings = new ArrayList<>();
    private List<StuffThing2> stuffThing2 = new ArrayList<>();

    public Thing() {
    }

    public Thing(Consumer<Thing> config) {
        config.accept(this);
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public List<StuffThing> getStuffThings() {
        return stuffThings;
    }

    public void setStuffThings(List<StuffThing> stuffThings) {
        this.stuffThings = stuffThings;
    }

    public static class StuffThing {
        private String prop1;

        public String getProp1() {
            return prop1;
        }

        public void setProp1(String prop1) {
            this.prop1 = prop1;
        }
    }

    public static class StuffThing2 {
        private String prop1;

        public String getProp1() {
            return prop1;
        }

        public void setProp1(String prop1) {
            this.prop1 = prop1;
        }
    }
}

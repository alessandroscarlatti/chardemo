package com.scarlatti.chardemo;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.scarlatti.beanutils.ScarlattiBeanUtils.GeneralBean.TypeReference;
import com.scarlatti.chardemo.Thing.StuffThing;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.scarlatti.beanutils.ScarlattiBeanUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 7/13/2019
 */
public class ScarlattiBeanUtilsDemo {

    @Test
    public void intraClassFluent() {
        new Thing(thing -> {
           thing.setProp1("asdf1");
           thing.setProp2("asdf2");
           thing.getStuffThings();
        });
    }

    @Test
    public void beanUtilsFluent() {
        Thing thingBean = bean(Thing.class, thing -> {
            setProperty(thing, "prop1", "asdf1");
            setProperty(thing, "prop2", "asdf1");
            withProperty(thing, "stuffThings", (List<StuffThing> stuffThings) -> {
                stuffThings.add(new StuffThing());
            });

            withProperty(thing, "stuffThings", stuffThings -> {
                //
            });
            newListItem(thing.getStuffThings(), stuffThing -> {
                stuffThing.setProp1("asd");
            });
        });

        assertEquals("asd", thingBean.getStuffThings().get(0).getProp1());
    }

    @Test
    public void beanUtilsFluentWithGeneralBean() {
        generalBean(Thing.class, thing -> {
            thing.set("prop1", "asdf1");
            thing.set("prop2", "qwer");
            thing.with("stuffThings", stuffThings -> {

            });
            thing.get().getStuffThings();
        });
    }

    @Test
    public void listTest() {
        List<String> list = new ArrayList<>();

        List list2 = list;

        boolean result = list2.add(123);
        assertTrue(result);  // this actually passes, even though the list is now inconsistent.

        // String item0 = list.get(0);  // this will now fail with class-cast exception
    }

    @Test
    public void typeReferenceTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructType(new com.fasterxml.jackson.core.type.TypeReference<Thing>(){});

        assertEquals("Thing", javaType.getTypeName());
    }
}


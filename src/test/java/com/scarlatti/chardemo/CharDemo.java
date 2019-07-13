package com.scarlatti.chardemo;

import org.junit.Test;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 7/11/2019
 */
public class CharDemo {

    @Test
    public void charDemo() {
        char c = '\u0253';
        System.out.println(c);

        System.out.println("a\u0022.length() + \u0022b".length());
    }
}

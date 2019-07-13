package com.scarlatti.chardemo;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.junit.Test;

import static javax.lang.model.element.Modifier.*;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 7/13/2019
 */
public class JavaPoetTest {

    @Test
    public void javaPoet() throws Exception {
        MethodSpec main = MethodSpec.methodBuilder("main")
            .addModifiers(PUBLIC, STATIC)
            .returns(void.class)
            .addParameter(String[].class, "args")
            .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
            .build();

        MethodSpec stuffMethod = MethodSpec.methodBuilder("stuff")
            .returns(void.class)
            .addParameter(String.class, "stuff")
            .beginControlFlow("switch (stuff)")
            .addCode("case default:")
            .addStatement("break")
            .build();

        TypeSpec helloWorld2 = TypeSpec.classBuilder("HelloWorld2")
            .addModifiers(PUBLIC, STATIC, FINAL)
            .addMethod(main)
            .addMethod(stuffMethod)
            .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
            .addModifiers(PUBLIC, FINAL)
            .addMethod(main)
            .addType(helloWorld2)
            .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
            .addFileComment("asdfsdalkdjf")
            .build();

        javaFile.writeTo(System.out);
    }
}

package com.ascential.impl;

import com.ascential.CustomNumberEntity;
import com.ascential.MockitoExtension;
import com.ascential.NumberFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class NumberFinderImplTest {

    NumberFinder numberFinder;

    String pathJson = "C:\\workspace\\numbers.json";

    List<CustomNumberEntity> customNumberEntities;

    @BeforeEach
    public void setUp(){
        this.numberFinder = new NumberFinderImpl();
        this.customNumberEntities = this.numberFinder.readFromFile("C:\\workspace\\numbers.json");
    }

    @Test
    public void readJson(){
        List<CustomNumberEntity> customNumberEntities = this.numberFinder.readFromFile("C:\\workspace\\numbers.json");

        Assertions.assertEquals(customNumberEntities.size(), 7);
    }

    @Test
    public void find(){
        Assertions.assertTrue(this.numberFinder.contains(100, this.customNumberEntities));
    }
}

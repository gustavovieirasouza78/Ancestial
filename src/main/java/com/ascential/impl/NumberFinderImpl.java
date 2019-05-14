package com.ascential.impl;

import com.ascential.CustomNumberEntity;
import com.ascential.FastestComparator;
import com.ascential.NumberFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFinderImpl implements NumberFinder {
    @Override
    public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
        boolean contains = false;

        FastestComparator fastestComparator = new FastestComparator();

        for (CustomNumberEntity customNumberEntity : list)
        {
            if(fastestComparator.compare(valueToFind, customNumberEntity)==0){
                contains = true;
                break;
            }
        }

        return contains;
    }

    @Override
    public List<CustomNumberEntity> readFromFile(String filePath) {
        List<CustomNumberEntity> customNumberEntities = new LinkedList<>();
        JSONParser jsonParser = new JSONParser();

        String REGEX = "^(\\+|-)?\\d+$";
        Pattern p = Pattern.compile(REGEX);

        try (Reader reader = new FileReader(filePath)) {
            //parse file to array
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                //get object from array
                JSONObject jsonObject = iterator.next();
                String number = (String)jsonObject.get("number");

                if(number !=null){
                    Matcher m = p.matcher(number);
                    if(m.matches()) {
                        //create CustomNumberEntity thought Reflaction
                        Constructor constructor = CustomNumberEntity.class.getDeclaredConstructor(String.class);
                        constructor.setAccessible(true);
                        CustomNumberEntity customNumberEntity = (CustomNumberEntity) constructor.newInstance(number);

                        //add customNumberEntity to customNumberEntities
                        customNumberEntities.add(customNumberEntity);
                    }
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return customNumberEntities;
    }
}

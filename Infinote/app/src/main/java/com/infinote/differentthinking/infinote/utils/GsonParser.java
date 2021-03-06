package com.infinote.differentthinking.infinote.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infinote.differentthinking.infinote.utils.base.GsonParserContract;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonParser implements GsonParserContract {

    @Override
    public String toJson(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);

    }

    @Override
    public <T> T fromJson(String json, Type classOfT) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, classOfT);
    }

    @Override
    public String getDirectMember(String json, String memberName) {
        JsonParser jsonParser = new JsonParser();
        JsonObject parent = jsonParser
                .parse(json)
                .getAsJsonObject();

        JsonElement memberElement = parent.get(memberName);
        if (memberElement instanceof JsonNull) {
            return null;
        } else {
            return parent.getAsJsonObject(memberName).toString();
        }
    }

    @Override
    public <T> List<T> getDirectArray(String json, String memberName, Class<T> elementType) {
        JsonParser jsonParser = new JsonParser();
        JsonObject parent = jsonParser
                .parse(json)
                .getAsJsonObject();

        JsonElement memberElement = parent.get(memberName);
        if (memberElement instanceof JsonNull) {
            return null;
        }

        JsonArray memberArray = memberElement.getAsJsonArray();

        Gson gson = new Gson();

        List<T> list = new ArrayList<>();
        for (final JsonElement element : memberArray) {
            T entity = gson.fromJson(element, elementType);
            list.add(entity);
        }

        return list;
    }
}
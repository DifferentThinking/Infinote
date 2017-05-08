package com.infinote.differentthinking.infinote.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
        String json = gson.toJson(src);

        return json;
    }

    @Override
    public <T> T fromJson(String json, Type classOfT) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        T result = gson.fromJson(json, classOfT);

        return result;
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
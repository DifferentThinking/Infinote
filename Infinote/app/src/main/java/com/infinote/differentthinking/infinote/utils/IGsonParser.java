package com.infinote.differentthinking.infinote.utils;

import java.lang.reflect.Type;
import java.util.List;

public interface IGsonParser {
    String toJson(Object src);

    <T> T fromJson(String json, Type classOfT);

    String getDirectMember(String json, String memberName);

    <T> List<T> getDirectArray(String json, String memberName, Class<T> elementType);
}

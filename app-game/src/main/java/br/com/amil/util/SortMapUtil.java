package br.com.amil.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SortMapUtil {

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
    Map<K, V> result = new LinkedHashMap<>();
    Stream<Map.Entry<K, V>> st = map.entrySet().stream();

    st.sorted(Map.Entry.comparingByValue())
        .forEachOrdered( e -> result.put(e.getKey(), e.getValue()) );

    return result;
	}
}

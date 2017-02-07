package org.ivt.agregator.utils;

import java.util.*;

public class VologdaVkSearchStringFactory implements VkSearchStringFactory {

    private List<String> strings = new ArrayList<String>();
    private Iterator<String> iterator;

    public VologdaVkSearchStringFactory() {
        strings.add("Вологда");
        for (int i = 1; i < 32; ++i) {
            strings.add(String.valueOf(i));
        }
        strings.add("party");
        strings.add(String.valueOf(new GregorianCalendar().get(Calendar.YEAR)));

        iterator = strings.iterator();
    }

    public String getNext() {
        if (!iterator.hasNext()) {
            iterator = strings.iterator();
        }
        return iterator.next();
    }
}

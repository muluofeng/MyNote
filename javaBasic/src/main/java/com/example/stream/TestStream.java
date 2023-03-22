package com.example.stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xiexingxing
 * @date 2022/7/9 9:04 PM
 */

public class TestStream {
    public static void main(String[] args) {
        List<Tag> list = new ArrayList<>();
        HashSet<Long> secenIds1 = new HashSet<>();
        HashSet<Long> secenIds2 = new HashSet<>();
        HashSet<Long> secenIds3 = new HashSet<>();
        secenIds1.add(1L);
        secenIds1.add(2L);
        secenIds1.add(4L);
        secenIds1.add(7L);

        secenIds2.add(1L);
        secenIds2.add(2L);
        secenIds2.add(4L);
        secenIds2.add(5L);
        secenIds2.add(6L);



        secenIds3.add(1L);

        list.add(new Tag("aa", secenIds1));
        list.add(new Tag("aa", secenIds2));
        list.add(new Tag("b", secenIds3));

        Map<String, Set> collect1 = list.stream().collect(Collectors.groupingBy(Tag::getName,
                Collector.of(HashSet::new, (Set set, Tag right) -> {
                    set.addAll(right.getSecenIds());
                }, (Set s1, Set s2) -> {
                     s1.addAll(s2);
                    return s1;
                })
        ));

        Map<String, Set> collect2 = list.stream().collect(Collectors.groupingBy(Tag::getName,
                Collector.of(HashSet::new, (Set set, Tag right) -> {
                    set.addAll(right.getSecenIds());
                }, (Set s1, Set s2) -> s1)
        ));


        System.out.println();

    }
    public static class Tag{
        private String name;
        private Set<Long> secenIds;

        public Tag(String name, Set<Long> secenIds) {
            this.name = name;
            this.secenIds = secenIds;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<Long> getSecenIds() {
            return secenIds;
        }

        public void setSecenIds(Set<Long> secenIds) {
            this.secenIds = secenIds;
        }
    }
}

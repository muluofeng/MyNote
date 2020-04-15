package com.example.xing.stream;


import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xiexingxing
 * @Created by 2019-06-10 11:25.
 */
@Getter
@Setter
public class Streamtest {


    public static void main(String[] args) {
        String[] words = {"hello", "world"};
        List<String[]> collect = Arrays.stream(words).map((i) -> i.split("")).collect(Collectors.toList());
        collect.stream().forEach((b) -> {
            System.out.println(b[0] + b[1]);
        });
        //使用flatmap  将多个流转为1个流
        List<String> collect1 = Arrays.stream(words).map((i) -> i.split("")).flatMap((n) -> Arrays.stream(n)).collect(Collectors.toList());
        System.out.println(collect1.toString());


        //使用flatmap 偏平化 感觉就是2维转为1维
        Person person = new Person();
        person.setName("lisi");
        Address address = new Address();
        address.setCity("杭州1");
        address.setProvince("浙江省1");

        Address address2 = new Address();
        address2.setCity("杭州2");
        address2.setProvince("浙江省2");

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        addressList.add(address2);
        person.setAddresses(addressList);


        Person person2 = new Person();
        person2.setName("zhangshan");
        Address address3 = new Address();
        address3.setCity("杭州3");
        address3.setProvince("浙江省3");

        Address address4 = new Address();
        address4.setCity("杭州4");
        address4.setProvince("浙江省4");

        List<Address> addressList2 = new ArrayList<>();
        addressList.add(address3);
        addressList.add(address4);
        person2.setAddresses(addressList2);


        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person2);

        List<Address> collect2 = personList.stream().map((p) -> p.getAddresses()).flatMap((n) -> n.stream()).collect(Collectors.toList());

        for (Address a : collect2) {
            System.out.println(a.getCity());
        }

        Collections.sort(collect2, (o1, o2) -> 0);


        IntSummaryStatistics statistics = IntStream.range(0, 100).summaryStatistics();
        System.out.println(statistics.getSum());
        System.out.println(statistics.getMin());
        System.out.println(statistics.getMax());
        System.out.println(statistics.getCount());

        IntStream.range(0, 100).mapToObj(i -> (Integer) i).filter(i -> i > 90).distinct().forEach((i) -> System.out.println(i));

    }


}

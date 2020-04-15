package com.example.xing.jdk8option;

import java.util.Optional;

/**
 * @author xiexingxing
 * @Created by 2019-12-24 16:04.
 */
public class TestOption {
    public static void main(String[] args) {
        User user = new User();
        Address address = new Address();
        address.setAddressName("测试地址");
        user.setAddress(address);
        user.setUsername("张三");

        String adreeName = "";

        if (user != null) {
            Address userAddress = user.getAddress();
            if (userAddress != null) {
                adreeName = userAddress.getAddressName();
            }
        }

        String optionAdreeName = Optional.ofNullable(user)
                .map((i) -> i.getAddress()).map((i) -> i.getAddressName())
                .orElseGet(() -> createStr());


        System.out.println(adreeName);
        System.out.println(optionAdreeName);


        Optional.ofNullable(user).ifPresent(u -> {
            // TODO: do something
            System.out.println(" do something");
        });

//        User user2 =null;

    }

    private static String createStr() {
        System.out.println("createStr");
        return "123";
    }
}
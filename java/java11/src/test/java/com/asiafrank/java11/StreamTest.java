package com.asiafrank.java11;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流处理教程
 * https://zhuanlan.zhihu.com/p/92321454
 */
public class StreamTest {

    /**
     * 筛选出长度为4的名字,用逗号分隔
     */
    @Test
    public void joinLength4Names() {
        List<String> names = Arrays.asList("Jack", "Jill", "Nate", "Kara", "Kim", "Jullie", "Paul", "Peter");

        String nameString = names.stream()
                                 .filter(num -> num.length() == 4)
                                 .collect(Collectors.joining(", "));
        System.out.println(nameString);
    }

    /**
     * 生成自己的 Stream 流
     */
    @Test
    public void generateTest(){
        // 生成自己的随机数流
        Random random = new Random();
        Stream<Integer> generateRandom = Stream.generate(random::nextInt);
        generateRandom.limit(5).forEach(System.out::println);
        // 生成自己的 UUID 流
        Stream<UUID> generate = Stream.generate(UUID::randomUUID);
        generate.limit(5).forEach(System.out::println);
    }
}

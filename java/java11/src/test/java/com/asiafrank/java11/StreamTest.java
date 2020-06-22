package com.asiafrank.java11;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流处理教程
 * https://zhuanlan.zhihu.com/p/92321454
 */
public class StreamTest {

    @Test
    public void flatMapTest() {
        Set<Integer> s1 = Set.of(1, 2, 3, 4, 5);
        Set<Integer> s2 = Set.of(2, 3, 4, 8, 9, 10);
        Set<Integer> s3 = Set.of(5, 6, 7, 8);

        Set<Integer> collect = Stream.of(s1, s2, s3)
                                     .flatMap(Collection::stream)
                                     .collect(Collectors.toSet());
        System.out.println(collect);
    }

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

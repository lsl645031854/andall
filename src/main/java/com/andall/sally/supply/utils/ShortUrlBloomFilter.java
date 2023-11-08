package com.andall.sally.supply.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortUrlBloomFilter {

    private static BloomFilter<String> bloomFilter = BloomFilter.create(
            Funnels.stringFunnel(Charset.defaultCharset()), 10000000);



    public static boolean mightContain(String shorts) {
        return bloomFilter.mightContain(shorts);
    }

    public static void put(String shorts) {
        bloomFilter.put(shorts);
    }


}
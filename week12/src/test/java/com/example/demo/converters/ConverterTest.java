package com.example.demo.converters;

import com.example.demo.converters.bases.Converter;
import com.example.demo.exceptions.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Component
class SampleConverter extends Converter<String,Integer>{

    @Override
    public Integer convert(String source) throws BadRequestException{
        return Integer.parseInt(source);
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class ConverterTest {

    @Autowired
    private SampleConverter sampleConverter;

    @Test
    public void test_convert_list(){
        String[] numbers = new String[]{"4","5","6"};

        List<Integer> list = sampleConverter.convert(Arrays.asList(numbers));

        assertEquals(list,Arrays.asList(4,5,6));

    }
}


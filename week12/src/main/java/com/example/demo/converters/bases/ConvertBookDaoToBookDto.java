package com.example.demo.converters.bases;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.dao.Book;
import com.example.demo.models.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class ConvertBookDaoToBookDto extends Converter<Book, BookDTO>{

    @Override
    public BookDTO convert(Book source) throws BadRequestException{

        BookDTO bookDTO=new BookDTO();

        bookDTO.setId(source.getId());
        bookDTO.setName(source.getName());
        bookDTO.setYear(source.getYear());

        if(source.getCategory()!=null) {
            bookDTO.setIdCate(source.getCategory().getIdCate());
        }

        return bookDTO;
    }


}

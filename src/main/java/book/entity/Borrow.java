package book.entity;

import lombok.Data;

@Data
public class Borrow {
    Student student;
    Book book;
    int id;
}

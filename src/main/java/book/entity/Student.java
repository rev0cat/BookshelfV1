package book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Student {
    int sid;
    final String name;
    final String sex;
    final int grade;
}

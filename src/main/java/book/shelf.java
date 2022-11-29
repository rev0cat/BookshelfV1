package book;

import book.entity.Book;
import book.entity.Student;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

@Log
public class shelf {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            LogManager logManager = LogManager.getLogManager();
            logManager.readConfiguration(Resources.getResourceAsStream("log.properties"));
            while (true) {
                System.out.println("==============");
                System.out.println("1.录入学生信息");
                System.out.println("2.录入书籍信息");
                System.out.println("3.添加借阅信息");
                System.out.println("4.查询借阅信息");
                System.out.println("5.查询书籍信息");
                System.out.println("6.查询学生信息");
                System.out.print("输入您想要执行的操作,输入其他任意数字退出:");
                int input;
                try {
                    input = scanner.nextInt();
                } catch (Exception e) {
                    return;
                }
                scanner.nextLine();
                switch (input) {
                    case 1:
                        addStudent(scanner);
                        break;
                    case 2:
                        addBook(scanner);
                        break;
                    case 3:
                        addBorrow(scanner);
                        break;
                    case 4:
                        showBorrow();
                        break;
                    case 5:
                        showBook();
                        break;
                    case 6:
                        showStudent();
                        break;
                    default:
                        return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("请输入学生名字：");
        String name = scanner.nextLine();
        System.out.print("请输入学生性别：");
        String sex = scanner.nextLine();
        System.out.print("请输入学生年级：");
        String grade = scanner.nextLine();
        int g = Integer.parseInt(grade);

        Student student = new Student(name, sex, g);
        SQLUtils.doSqlWork(bookMapper -> {
            int i = bookMapper.addStudent(student);
            if (i > 0) {
                System.out.println("学生信息录入成功！");
                log.info("新添加一条学生信息" + student.toString());
            } else {
                System.out.println("学生信息录入失败，请重试！");
            }
        });
    }

    private static void addBook(Scanner scanner) {
        System.out.print("请输入书籍标题：");
        String title = scanner.nextLine();
        System.out.print("请输入书籍介绍：");
        String desc = scanner.nextLine();
        System.out.print("请输入书籍价格：");
        String price = scanner.nextLine();
        double p = Double.parseDouble(price);

        Book book = new Book(title, desc, p);
        SQLUtils.doSqlWork(bookMapper -> {
            int i = bookMapper.addBook(book);
            if (i > 0) {
                System.out.println("书籍信息录入成功！");
                log.info("新添加一条书籍信息" + book.toString());
            } else {
                System.out.println("书籍信息录入失败，请重试！");
            }
        });
    }

    private static void addBorrow(Scanner scanner) {
        System.out.print("请输入书籍号:");
        String a = scanner.nextLine();
        int bid = Integer.parseInt(a);
        System.out.print("请输入学号:");
        String b = scanner.nextLine();
        int sid = Integer.parseInt(b);
        SQLUtils.doSqlWork(bookMapper -> {
            bookMapper.addBorrow(sid, bid);
            System.out.println("借书录入成功！");
        });
    }

    private static void showBorrow(){
        SQLUtils.doSqlWork(bookMapper -> {
            bookMapper.getBorrowList().forEach(borrow -> {
                System.out.println(borrow.getStudent().getName()+" -> "+borrow.getBook().getTitle());
            });
        });
    }

    private static void showStudent(){
        SQLUtils.doSqlWork(bookMapper -> {
            bookMapper.getStudentList().forEach(student -> {
                System.out.println(student.getSid()+"."+student.getName()+" "+student.getSex()+" "+student.getGrade()+"级");
            });
        });
    }

    private static void showBook(){
        SQLUtils.doSqlWork(bookMapper -> {
            bookMapper.getBookList().forEach(book -> {
                System.out.println(book.getBid()+"."+book.getTitle()+"("+book.getPrice()+")");
            });
        });
    }
}

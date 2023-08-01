package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));


            student.addBook(
                    new Book("Think like a man", LocalDateTime.now().minusDays(4)));


            student.addBook(
                    new Book("Think and Grow Rich", LocalDateTime.now()));


            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard(
                    "123456789",
                    student);

            student.setStudentIdCard(studentIdCard);

            studentRepository.save(student);

            studentRepository.findById(1L)
                    .ifPresent(s -> {
                        System.out.println("fetch book lazy....");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(
                                    s.getFirstName() + " borrowed " + book.getBookName());
                        });
                    });

//            StudentIdCard studentIdCard = new StudentIdCard(
//                    "123456789",
//                    student);
//
//            studentIdCardRepository.save(studentIdCard);
//
//            studentRepository.findById(1L)
//                    .ifPresent(System.out::println);
//
//            studentIdCardRepository.findById(1L)
//                    .ifPresent(System.out::println);
//
//            studentRepository.deleteById(1L);


        };
    }
}

//    private void generateRandomStudents(StudentRepository studentRepository) {
//        Faker faker = new Faker();
//        for (int i = 0; i < 20; i++) {
//            String firstName = faker.name().firstName();
//            String lastName = faker.name().lastName();
//            String email = String.format("%s.%s@gmail.com", firstName, lastName);
//            Student student = new Student(
//                    firstName,
//                    lastName,
//                    email,
//                    faker.number().numberBetween(17, 55));
//            studentRepository.save(student);
//        }
//    }
//}



//package com.example.demo;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import java.util.List;
//
//@SpringBootApplication
//public class Application {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//
//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
//        return  args -> {
//            Student alex = new Student(
//                    "alex",
//                    "koech",
//                    "alexkoech@gmail.com",
//                    14
//            );
//            Student maria = new Student(
//                    "Joy",
//                    "Mwangi",
//                    "Joy.Mwangi@gmail.com",
//                    19
//            );
//
//            Student ahmed = new Student(
//                    "Julie",
//                    "Yego",
//                    "Julie.Yego@gmail.com",
//                    22
//            );
//            studentRepository.saveAll(List.of(alex, ahmed, maria));
//
//            studentRepository
//                    .findStudentByEmail("Joy.Mwangi@gmail.com")
//                    .ifPresentOrElse(
//                            System.out::println,
//                            () -> System.out.println("Student with email Joy.Mwangi@gmail.com not found"));
//
//            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqual(
//                    "Maria",
//                    21
//            ).forEach(System.out::println);
//
//            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
//                    "Julie",
//                    19
//            ).forEach(System.out::println);
//
//            System.out.println("Deleting Julie Yego");
//            System.out.println(studentRepository.deleteStudentById(3L));
//        };
//    }
//}
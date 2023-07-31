package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return  args -> {
            Student alex = new Student(
                    "alex",
                    "koech",
                    "alexkoech@gmail.com",
                    14
            );
            Student maria = new Student(
                    "Joy",
                    "Mwangi",
                    "Joy.Mwangi@gmail.com",
                    19
            );

            Student ahmed = new Student(
                    "Julie",
                    "Yego",
                    "Julie.Yego@gmail.com",
                    22
            );
            studentRepository.saveAll(List.of(alex, ahmed, maria));

            studentRepository
                    .findStudentByEmail("Joy.Mwangi@gmail.com")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Student with email Joy.Mwangi@gmail.com not found"));

            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqual(
                    "Maria",
                    21
            ).forEach(System.out::println);

            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
                    "Julie",
                    19
            ).forEach(System.out::println);

            System.out.println("Deleting Julie Yego");
            System.out.println(studentRepository.deleteStudentById(3L));
        };
    }
}
package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//This annotation marks the interface as a Spring Data repository.
//It tells Spring to create a bean for this repository during component scanning, making it available for dependency injection.
@Repository
//This interface provides basic CRUD (Create, Read, Update, Delete) operations and pagination support.
//It operates on the Student entity type and uses Long as the type of the primary key.
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(
            String firstName, Integer age);

    @Query(
            value = "SELECT * FROM student WHERE first_name = :firstName AND age >= :age",
            nativeQuery = true)
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int deleteStudentById(Long id);
}


//    public interface StudentRepository  extends JpaRepository<Student, Long> {
//
////    This method is annotated with @Query and uses a JPQL (Java Persistence Query Language) query to find a student by their email address.
////    It returns an Optional of Student, meaning it may return an actual student or an empty result if no student is found with the specified email.
//
//
////    Parameter Binding:
////    In the JPQL queries, method parameters are referenced using the ?1, ?2, etc., placeholders based on their order in the method's parameter list.
////    In the native SQL query, named parameters are used, which are bound to the method parameters using the @Param annotation.
//    @Query("SELECT s FROM Student s WHERE s.email = ?1")
//    Optional<Student> findStudentByEmail(String email);
//
//
////    This method is also annotated with @Query and retrieves a
////    list of students whose first name matches the provided firstName and age is greater than or equal to the specified age.
////    It uses JPQL syntax.
//    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
//    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(
//            String firstName, Integer age);
//
//
////    This method is similar to the previous one, but it uses a native SQL query to fetch the data instead of JPQL.
////    The @Param annotation is used to map the method parameters to named parameters in the SQL query.
//    @Query(
//            value = "SELECT * FROM student WHERE first_name = :firstName AND age >= :age",
//            nativeQuery = true)
//    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
//            @Param("firstName") String firstName,
//            @Param("age") Integer age);
//
////    @Query: This annotation is used to specify a custom JPQL or native SQL query for the method.
//
////    @Modifying: This annotation is used to indicate that the method performs write operations (e.g., insert, update, delete)
////    and should be used in conjunction with the @Query annotation for such methods.
//
////    @Transactional: This annotation is used to indicate that the method should be executed within a transaction.
////    It is needed for write operations, such as the delete method in this case.
//
//    @Transactional
//    @Modifying
////    This method is annotated with @Query and uses JPQL to delete a student from the database based on their id.
////    It is marked with @Modifying to indicate that it performs a write operation, and @Transactional to ensure that the operation is executed within a transaction.
//    @Query("DELETE FROM Student u WHERE u.id = ?1")
//    int deleteStudentById(Long id);
//}

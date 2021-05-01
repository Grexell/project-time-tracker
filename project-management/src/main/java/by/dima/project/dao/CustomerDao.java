package by.dima.project.dao;

import by.dima.project.model.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerDao extends ReactiveCrudRepository<Customer, Long> {
//    todo move select to view
    @Query("select c.id, c.name, c.contact\n" +
            "from customer c\n" +
            "inner join customer_project cp on c.id = cp.customer_id\n" +
            "inner join project p on cp.project_id = p.id\n" +
            "inner join user_project up on p.id = up.project_id\n" +
            "where up.user_id = :#{#managerId}\n" +
            "union\n" +
            "select c.id, c.name, c.contact\n" +
            "from customer c\n" +
            "where !exists(select *\n" +
            "              from customer_project cp\n" +
            "                       inner join project p on cp.project_id = p.id\n" +
            "                       inner join user_project up on p.id = up.project_id\n" +
            "                       inner join user u on up.user_id = u.id\n" +
            "              where cp.customer_id = c.id\n" +
            "                and u.role_id = 2)")
    Flux<Customer> findAllByManagerId(@Param("managerId") Long managerId);
}

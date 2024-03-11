package com.delsin.BankingService.model;

import com.delsin.BankingService.model.entity.Email;
import com.delsin.BankingService.model.entity.Phone;
import com.delsin.BankingService.model.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserSpecifications {
    public static Specification<User> birthDateAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("birthday"), date);
    }

    public static Specification<User> emailEquals(String email) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Email> emailsJoin = root.join("emails", JoinType.INNER);
            return criteriaBuilder.equal(emailsJoin.get("email"), email);
        };
    }

    public static Specification<User> phoneEquals(String phone) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Phone> phonesJoin = root.join("phones", JoinType.INNER);
            return criteriaBuilder.equal(phonesJoin.get("phone"), phone);
        };
    }

    public static Specification<User> fullNameLike(String fullName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("fullName"), fullName + "%");
    }


}

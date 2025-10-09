package com.safari.safari_2.repository;

import com.safari.safari_2.model.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    // Since we'll only have one contact details record, we can use findFirst()
    ContactDetails findFirstByOrderByIdAsc();
}

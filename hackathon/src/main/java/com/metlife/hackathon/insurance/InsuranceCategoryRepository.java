package com.metlife.hackathon.insurance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCategoryRepository extends JpaRepository<InsuranceCategory, Long> {
}

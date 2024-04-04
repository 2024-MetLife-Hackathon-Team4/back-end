package com.metlife.hackathon.insurance;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    default Insurance get(Long insuranceId) {
        return findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found: " + insuranceId));
    }

    List<Insurance> findAllByInsuranceCategory_Id(Long insuranceCategoryId);

    Optional<Insurance> findByName(String name);
}

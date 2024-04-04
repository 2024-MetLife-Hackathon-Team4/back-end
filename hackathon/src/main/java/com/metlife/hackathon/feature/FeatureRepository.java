package com.metlife.hackathon.feature;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByInsuranceId(Long id);
}

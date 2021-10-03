package com.github.iahrari.springrestmvc.repository;

import com.github.iahrari.springrestmvc.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {}

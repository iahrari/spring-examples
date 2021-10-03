package com.github.iahrari.springwebfluxrest.repository;

import com.github.iahrari.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {}

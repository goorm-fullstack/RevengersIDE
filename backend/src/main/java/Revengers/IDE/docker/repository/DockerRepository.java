package Revengers.IDE.docker.repository;

import Revengers.IDE.docker.model.Docker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockerRepository extends JpaRepository<Docker, Long> {
}

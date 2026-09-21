    package com.example.cricketmanagement.repository;

    import com.example.cricketmanagement.model.Users;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.Optional;

    public interface UserRepository extends JpaRepository<Users, Long> {
        Optional<Users> findByUserName(String userName);
    }

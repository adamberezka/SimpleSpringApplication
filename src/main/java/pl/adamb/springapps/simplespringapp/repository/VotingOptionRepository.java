package pl.adamb.springapps.simplespringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamb.springapps.simplespringapp.entity.VotingOption;

public interface VotingOptionRepository extends JpaRepository<VotingOption, Integer> {
}

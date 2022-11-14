package com.group10.softwareengineeringmetrics;

import static org.assertj.core.api.Assertions.assertThat;

import com.group10.softwareengineeringmetrics.models.Branch;
import com.group10.softwareengineeringmetrics.models.Repository;
import com.group10.softwareengineeringmetrics.models.User;
import com.group10.softwareengineeringmetrics.repository.BranchRepository;
import com.group10.softwareengineeringmetrics.repository.RepositoryRepository;
import com.group10.softwareengineeringmetrics.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    RepositoryRepository repositoryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void emptyBranchRepo() {
        List<Branch> branches = new ArrayList<>(branchRepository.findAll());
        assertThat(branches).isEmpty();
    }

    @Test
    public void emptyRepoRepo() {
        List<Repository> repositories = new ArrayList<>(repositoryRepository.findAll());
        assertThat(repositories).isEmpty();
    }
    @Test
    public void emptyUserRepo() {
        List<User> users = new ArrayList<>(userRepository.findAll());
        assertThat(users).isEmpty();
    }

    @Test
    public void shouldFindAllBranches() {
        Branch bra1 = new Branch("Branch#1", true);
        entityManager.persist(bra1);
        Branch bra2 = new Branch("Branch#2", false);
        entityManager.persist(bra2);
        Branch bra3 = new Branch("Branch#3", false);
        entityManager.persist(bra3);

        List<Branch> branches = branchRepository.findAll();
        assertThat(branches).hasSize(3).contains(bra1, bra2, bra3);
    }

    @Test
    public void shouldFindAllRepos() {
        Repository rep1 = new Repository("Repo#1", "ReadMe");
        entityManager.persist(rep1);
        Repository rep2 = new Repository("Repo#2", "ReadMe");
        entityManager.persist(rep2);
        Repository rep3 = new Repository("Repo#3", "ReadMe");
        entityManager.persist(rep3);

        List<Repository> repositories = repositoryRepository.findAll();
        assertThat(repositories).hasSize(3).contains(rep1, rep2, rep3);
    }

    @Test
    public void shouldFindAllUsers() {
        User user1 = new User("User#1");
        entityManager.persist(user1);
        User user2 = new User("User#2");
        entityManager.persist(user2);
        User user3 = new User("User#3");
        entityManager.persist(user3);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(3).contains(user1, user2, user3);
    }
    
}

package com.group10.softwareengineeringmetrics;

import static org.assertj.core.api.Assertions.assertThat;

import com.group10.softwareengineeringmetrics.models.*;
import com.group10.softwareengineeringmetrics.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    // This works to run, but throws an error in editing, so I have suppressed that error.
    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    RepositoryRepository repositoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommitRepository commitRepository;

    @Autowired
    PullRequestRepository pullRequestRepository;

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
    public void emptyCommitRepo() {
        List<Commit> commits = new ArrayList<>(commitRepository.findAll());
        assertThat(commits).isEmpty();
    }

    @Test
    public void emptyPullRequestRepo() {
        List<PullRequest> pullRequests = new ArrayList<>(pullRequestRepository.findAll());
        assertThat(pullRequests).isEmpty();
    }

    @Test
    public void shouldFindAllBranches() {
        Branch bra1 = new Branch("Branch#1", "Repo", 11111111);
        entityManager.persist(bra1);
        Branch bra2 = new Branch("Branch#2", "Repo", 11111111);
        entityManager.persist(bra2);
        Branch bra3 = new Branch("Branch#3", "Repo", 11111111);
        entityManager.persist(bra3);

        List<Branch> branches = branchRepository.findAll();
        assertThat(branches).hasSize(3).contains(bra1, bra2, bra3);
    }

    @Test
    public void shouldFindAllRepos() {
        Repository rep1 = new Repository(11111111, "owner/Repo1");
        entityManager.persist(rep1);
        Repository rep2 = new Repository(22222222, "owner/Repo2");
        entityManager.persist(rep2);
        Repository rep3 = new Repository(33333333, "owner/Repo3");
        entityManager.persist(rep3);

        List<Repository> repositories = repositoryRepository.findAll();
        assertThat(repositories).hasSize(3).contains(rep1, rep2, rep3);
    }

    @Test
    public void shouldFindAllUsers() {
        User user1 = new User(1111111, "User#1", "Repo", 101010);
        entityManager.persist(user1);
        User user2 = new User(2222222, "User#2", "Repo", 101010);
        entityManager.persist(user2);
        User user3 = new User(3333333, "User#3", "Repo", 101010);
        entityManager.persist(user3);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(3).contains(user1, user2, user3);
    }

    @Test
    public void shouldFindAllCommits() {
        Commit com1 = new Commit("abcdef123456789", "Branch", "2022-11-07T12:28:00Z", "author",
                12345, 2, 0, 0, "Repo", 1111111);
        entityManager.persist(com1);
        Commit com2 = new Commit("123456789abcdef", "Branch", "2022-11-09T11:28:00Z", "author",
                12345, 20, 5, 1, "Repo", 1111111);
        entityManager.persist(com2);
        Commit com3 = new Commit("1234abcdef56789", "Branch", "2022-10-09T11:30:00Z", "author",
                12345, 15, 20, 3, "Repo", 1111111);
        entityManager.persist(com3);

        List<Commit> commits = commitRepository.findAll();
        assertThat(commits).hasSize(3).contains(com1, com2, com3);
    }

    @Test
    public void shouldFindAllPullRequests() {
        PullRequest pul1 = new PullRequest(12345, "open", "Repo", 111111, "2022-11-07T12:28:00Z", "",
                "Branch1", "Branch2");
        entityManager.persist(pul1);
        PullRequest pul2 = new PullRequest(12346, "closed", "Repo", 111111, "2022-11-07T12:30:00Z", "2022-11-07T13:28:00Z",
                "Branch1", "Branch2");
        entityManager.persist(pul2);
        PullRequest pul3 = new PullRequest(12347, "closed", "Repo", 111111, "2022-10-07T09:28:00Z", "2022-10-09T13:28:00Z",
                "Branch1", "Branch2");
        entityManager.persist(pul3);

        List<PullRequest> pullRequests = pullRequestRepository.findAll();
        assertThat(pullRequests).hasSize(3).contains(pul1, pul2, pul3);
    }



}

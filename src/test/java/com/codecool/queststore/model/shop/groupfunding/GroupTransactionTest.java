package com.codecool.queststore.model.shop.groupfunding;

import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupTransactionTest {

    private GroupTransaction groupTransaction;
    private Artifact artifact;
    private User owner;
    private User user1;
    private User user2;

    @BeforeEach
    void init() {
        createArtifact();
        createUsers();
        groupTransaction = new GroupTransaction(owner, artifact);
        groupTransaction.addParticipant(user1);
    }

    @Test
    void testAdsNewParticipant() {
        groupTransaction.addParticipant(user2);
        List<User> users = groupTransaction.getUsers();

        assertTrue(users.contains(user2));
    }

    @Test //had to change addParticipant to pass test
    void testCantAddSameUserTwice() {
        groupTransaction.addParticipant(user1);
        List<User> users = groupTransaction.getUsers();
        int frequency = Collections.frequency(users, user1);

        assertEquals(1, frequency);
    }

    @Test //had to change addParticipant to pass test
    void testCantAddNullUser() {
        int expectedNrOfParticipants = groupTransaction.getUsers().size();
        groupTransaction.addParticipant(null);
        int actualNrOfParticipants = groupTransaction.getUsers().size();

        assertEquals(expectedNrOfParticipants, actualNrOfParticipants);
    }

    @Test
    void testDeleteParticipant() {
        groupTransaction.deleteParticipant(user1);
        List<User> users = groupTransaction.getUsers();

        assertFalse(users.contains(user1));
    }

    @Test
    void testDeleteHandlesUserNotInParticipants() {
        int expectedNrOfParticipants = groupTransaction.getUsers().size();
        groupTransaction.deleteParticipant(user2);
        int actualNrOfParticipants = groupTransaction.getUsers().size();

        assertEquals(expectedNrOfParticipants, actualNrOfParticipants);
    }

    @Test
    void testDeleteHandlesNullUser() {
        int expectedNrOfParticipants = groupTransaction.getUsers().size();
        groupTransaction.deleteParticipant(null);
        int actualNrOfParticipants = groupTransaction.getUsers().size();

        assertEquals(expectedNrOfParticipants, actualNrOfParticipants);
    }

    @Test //had to change deleteParticipant to pass test
    void testCantDeleteOwner() {
        int expectedNrOfParticipants = groupTransaction.getUsers().size();
        groupTransaction.deleteParticipant(owner);
        int actualNrOfParticipants = groupTransaction.getUsers().size();

        assertEquals(expectedNrOfParticipants, actualNrOfParticipants);
    }

    @Test //had to change constructor to pass test
    void testArtifactCantBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new GroupTransaction(owner, null));
    }

    @Test //had to change constructor to pass test
    void testOwnerCantBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new GroupTransaction(null, artifact));
    }

    @Test
    void testGetSharedCostForEvenNumberOfParticipants() {
        double sharedCost = groupTransaction.getSharedCost();

        assertEquals(10, sharedCost);
    }

    @Test
    void testGetSharedCostForUnevenNumberOfParticipants() {
        groupTransaction.addParticipant(user2);
        double sharedCost = groupTransaction.getSharedCost();

        assertEquals(6.666666666666667, sharedCost);
    }

    private void createArtifact() {
        artifact = new Artifact(1,
                1,
                "Artifact1",
                "Artifact1 desc",
                20,
                "artifact1.png",
                "artifact1_m.png",
                ArtifactCategory.GROUP,
                false);
    }

    private void createUsers() {
        owner = new User("Jan", "Kowalski", "kowalski@gmail.com", "Kraków", 1, Role.MENTOR);
        user1 = new User("Marcin", "Nowak", "nowak@gmail.com", "Kraków", 2, Role.MENTOR);
        user2 = new User("Robert", "Walczak", "walczak@gmail.com", "Kraków", 3, Role.MENTOR);
    }

}
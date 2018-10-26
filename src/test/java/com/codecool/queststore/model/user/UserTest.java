package com.codecool.queststore.model.user;

import com.codecool.queststore.model.user.title.TitleStrategy;
import com.codecool.queststore.model.user.wallet.WalletStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User student;
    private User mentor;
    private User admin;


    @BeforeEach
     void setUp() {
        student = new User("Mirek", "Jurek", "jurek@gmail.com", "Krk", 1, Role.STUDENT);
        mentor = new User("Marek", "Jarek", "jarek@gmail.com", "Krk", 2, Role.MENTOR);
        admin = new User("Jan", "Bartek", "bartek@gmail.com", "Krk", 3, Role.ADMIN);
    }


    @Test
    public void testIsDifferentWalletStrategy() {
        WalletStrategy mentorWallet = mentor.getWalletStrategy();
        WalletStrategy studentWallet = student.getWalletStrategy();
        WalletStrategy adminWallet = admin.getWalletStrategy();
        assertNotEquals(mentorWallet, studentWallet);
        assertNotEquals(adminWallet, studentWallet);

    }

    @Test
    public void testIsDiferentTitleStrategy() {
        TitleStrategy mentorTitle = mentor.getTitleStrategy();
        TitleStrategy studentTitle = student.getTitleStrategy();
        TitleStrategy adminTitle = admin.getTitleStrategy();
        assertNotEquals(mentorTitle, studentTitle);
        assertNotEquals(adminTitle, studentTitle);
    }


}
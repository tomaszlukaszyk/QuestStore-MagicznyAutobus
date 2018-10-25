package com.codecool.queststore.model.shop.groupfunding;

import com.codecool.queststore.DAO.ArtifactDAO;
import com.codecool.queststore.DAO.ConnectionPool;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupTransaction {
    private int idTransactionGroup =1;
    private final User owner;
    private final List<Participant> allParticipants = new ArrayList<>();
    private final Artifact target;
    private boolean isClosed = false;


    public GroupTransaction(User owner, Artifact target) {
        if (target == null) {
            throw new IllegalArgumentException("Artifact can't be null");
        }
        if (owner == null) {
            throw new IllegalArgumentException("Owner can't be null");
        }

        this.owner = owner;
        allParticipants.add(new Participant(true, owner));
        this.target = target;
    }

    public void register() {
        //todo: register in db
        this.idTransactionGroup = 1;
    }
    public int getIdTransactionGroup() {
        return this.idTransactionGroup;
    }

    public boolean getIsClosed() {
        return this.isClosed;
    }

    public Artifact getTarget() {
        return this.target;
    }

    public User getOwner() {
        return this.owner;
    }

    public List<User> getUsers() {

        List<User> users = new ArrayList<>();

        for(Participant participant: allParticipants){
            users.add(participant.getUser());
        }
        return users;
    }

    public void addParticipant(User user){
        if (user == null)
            return;

        Participant newParticipant = new Participant(user);
        if (!allParticipants.contains(newParticipant))
            allParticipants.add(newParticipant);
    }

    public void deleteParticipant(User user){
        if (user == null || user.equals(owner))
            return;

        allParticipants.remove(new Participant(user));
    }

    public void markParticipant(User user) {
       Participant wanted = new Participant(user);
       for(Participant participant : allParticipants) {
           if(wanted.equals(participant)){
               participant.setAccepted(true);
           }
       }
    }

    private int countParticipants() {
        return allParticipants.size();
    }

    public double getSharedCost() {
       int netCost = target.getCOST();

       return (double)netCost/countParticipants();

    }

    public void finalize() {
        ArtifactDAO dao =  new ArtifactDAO(ConnectionPool.getInstance());

        //if (areTheyAllAbleToPayYet()) {
            dao.finalizeGroupTransaction(this);
            isClosed = true;
        //}
    }


    public void cancel() {
            isClosed = true;
        }


    public boolean areTheyAllAbleToPayYet() {
        boolean flag = true;
        for (Participant e: allParticipants) {
            if (e.getUser().getWallet() < getSharedCost() /*|| !e.isAccepted()*/)
                flag = false;
        }
        return flag;
    }

}
class Participant {
    private boolean isAccepted;
    private User user;

    Participant(boolean isAccepted, User user) {
        this.isAccepted = isAccepted;
        this.user = user;
    }

    Participant(User user) {
        this.isAccepted = false;
        this.user = user;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUser());
    }
}

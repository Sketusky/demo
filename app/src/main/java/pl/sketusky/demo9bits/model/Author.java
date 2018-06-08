package pl.sketusky.demo9bits.model;

import io.realm.RealmObject;

public class Author extends RealmObject {

    protected String firstName;

    protected String lastName;

    protected String nick;

    public Author() {
    }

    public Author(String firstName, String lastName, String nick) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNick() {
        return nick;
    }

    public String getFullNameWithNickname() {
        return this.firstName + " '" + this.nick + "' " + this.lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}

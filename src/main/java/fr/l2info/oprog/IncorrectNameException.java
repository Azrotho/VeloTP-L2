package fr.l2info.oprog;

public class IncorrectNameException extends Exception {
    public IncorrectNameException() {
        super("Le nom fourni n'est pas correct.");
    }
}
package app.models.Group;

import java.util.Arrays;

public class EmailRecipents {

    private String[] recipents;

    public EmailRecipents(String[] recipents) {
        this.recipents = recipents;
    }

    public String[] getRecipents() {
        return recipents;
    }

    @Override
    public String toString() {
        return "EmailRecipents{" +
                "recipents=" + Arrays.toString(recipents) +
                '}';
    }
}

package todos;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Todo {
    public Todo(int id, String title, boolean completed) {
        this.id = id;
        this.title = new SimpleStringProperty(title);
        this.completed = new SimpleBooleanProperty(completed);
    }

    public final int id;
    public final StringProperty title;
    public final BooleanProperty completed;

    @Override
    public String toString() {
        return "#" + id + " [" + (completed.get() ? "x" : " ") + "] " + title.get();
    }
}

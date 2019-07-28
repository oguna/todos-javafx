package todos;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

public class TodoListCell extends ListCell<Todo> {
    private final CheckBox checkBox = new CheckBox("");
    private final Button removeButton = new Button("Delete");
    private final Label label = new Label();
    private final HBox hbox = new HBox();

    public TodoListCell(Consumer<Todo> removeAction) {
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        hbox.getChildren().addAll(checkBox, label, removeButton);
        this.checkBox.setSelected(false);
        this.removeButton.setOnAction(event -> {
            removeAction.accept(this.getItem());
        });
    }

    @Override
    protected void updateItem(Todo todo, boolean empty) {
        if (getItem() != null) {
            this.checkBox.selectedProperty().unbindBidirectional(getItem().completed);
            this.label.textProperty().unbind();
        }
        super.updateItem(todo, empty);
        if (empty || todo == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.checkBox.selectedProperty().bindBidirectional(todo.completed);
            this.label.textProperty().bind(todo.title);
            setGraphic(hbox);
        }
    }
}

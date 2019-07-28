package todos;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public ToggleButton allToggleButton;
    public ToggleButton activeToggleButton;
    public ToggleButton completedToggleButton;
    public Button clearCompletedButton;
    private ToggleGroup visibilityToggleGroup = new ToggleGroup();
    @FXML
    private Label label;

    @FXML
    private TextField textField;

    @FXML
    private ListView<Todo> listView;

    private IntegerProperty count = new SimpleIntegerProperty(0);
    private ObservableList<Todo> todos = FXCollections.observableArrayList();
    private FilteredList<Todo> filteredTodos = new FilteredList<>(this.todos, e->true);
    private ObjectProperty<Visibility> visibility = new SimpleObjectProperty<>(Visibility.ALL);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.count.bind(Bindings.size(this.todos));
        this.label.textProperty().bind(Bindings.concat(Bindings.convert(count), " items left"));
        this.listView.setItems(this.filteredTodos);
        this.listView.setCellFactory(param -> new TodoListCell(
                e->{this.todos.removeIf(v->v.equals(e));}));
        this.visibilityToggleGroup.getToggles().addAll(allToggleButton, activeToggleButton, completedToggleButton);
        this.visibilityToggleGroup.selectToggle(allToggleButton);
        this.visibilityToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(allToggleButton)) {
                    this.visibility.set(Visibility.ALL);
                } else if (newValue.equals(activeToggleButton)) {
                    this.visibility.set(Visibility.ACTIVE);
                } else if (newValue.equals(completedToggleButton)) {
                    this.visibility.set(Visibility.COMPLETED);
                }
            }
        });
        this.visibility.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case ALL:
                        this.filteredTodos.setPredicate(e -> true);
                        break;
                    case ACTIVE:
                        this.filteredTodos.setPredicate(e -> !e.completed.get());
                        break;
                    case COMPLETED:
                        this.filteredTodos.setPredicate(e -> e.completed.get());
                        break;
                }
            }
        });
    }

    public void toggleAll(ActionEvent actionEvent) {
        boolean allCompleted = todos.stream().allMatch(todo -> todo.completed.get());
        for (Todo todo : todos) {
            todo.completed.set(!allCompleted);
        }
    }

    public void add(ActionEvent actionEvent) {
        if (!textField.getText().isEmpty()) {
            Todo todo = new Todo(0, textField.getText(), false);
            todos.add(todo);
            textField.clear();
        }
    }

    public void clearCompleted(ActionEvent actionEvent) {
        this.todos.removeIf(todo -> todo.completed.get());
    }
}

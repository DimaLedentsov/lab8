package commands;

import common.collection.WorkerManager;
import common.commands.CommandImpl;
import common.commands.CommandType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import static common.utils.DateConverter.dateToString;

public class GroupCountingByEndDateCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public GroupCountingByEndDateCommand(WorkerManager cm) {
        super("group_counting_by_end_date", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        Map<LocalDate, Integer> map = collectionManager.groupByEndDate();
        if (map.isEmpty()) return "none of the elements have endDate field";


        String res = "";
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Map.Entry<LocalDate, Integer> pair : map.entrySet()) {
            LocalDate endDate = pair.getKey();
            int quantity = map.get(endDate);
            String cur = dateToString(endDate) + " : " + quantity;
            res += cur + "\n";
            list.add(cur);
        }
        Platform.runLater(()->{
            ListView<String> listView = new ListView<>();
            listView.setItems(list);
            Stage stage = new Stage();
            Scene scene = new Scene(listView);
            stage.setScene(scene);
            stage.setTitle("s");
            stage.showAndWait();
        });

        return res;
    }
}

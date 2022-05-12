package controllers.tools;

import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.scene.control.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableFilter <T>{
    private ObservableResourceFactory resourceFactory;
    private TableView<T> table;
    private ObservableList<T> list;
    //private Converter<T> converter;
    public TableFilter(TableView<T> tab, ObservableList<T> l, ObservableResourceFactory res){
        table = tab;
        list = l;
        resourceFactory = res;
    /*public TableFilter(TableView<T> tab, ObservableList<T> list, ConverterToRaw<T> converter,ObservableResourceFactory res){
        for(int i = 0; i<tab.getColumns().size();i++){
            final Integer index = i;
            TableColumn<T,?> col = tab.getColumns().get(index);
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setAutoHide(false);
            contextMenu.setHideOnEscape(true);
            TextField filter = new TextField();
            CustomMenuItem filterItem = new CustomMenuItem(filter);
            filterItem.setHideOnClick(false);

            MenuItem ok = new MenuItem();
            ok.textProperty().bind(res.getStringBinding("OK"));

            ok.setOnAction((event)->{
                //FilteredList<T> filteredList = new FilteredList<>(list,p->true);
                String condition = filter.getText();
                System.out.println(condition);

                ;
                if(condition!=null&&!condition.equals("")) {
                    System.out.println("b");
                    ObservableList<T> filteredList = list.filtered((p) -> converter.convert(p).get(index).contains(condition));
                    tab.setItems(filteredList);
                    filteredList.forEach(System.out::println);
                    //filteredList.setPredicate((p) -> String.valueOf(p).contains(condition));
                }
            });


            contextMenu.getItems().addAll(filterItem,ok);
            col.setContextMenu(contextMenu);
        }*/


    }
    public TableFilter<T> addFilter(TableColumn<T,?> col,Converter<T> converter){

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAutoHide(false);
        contextMenu.setHideOnEscape(true);
        TextField filter = new TextField();
        CustomMenuItem filterItem = new CustomMenuItem(filter);
        filterItem.setHideOnClick(false);

        MenuItem ok = new MenuItem();
        ok.textProperty().bind(resourceFactory.getStringBinding("OK"));

        String defaultHeaderStyle = col.getStyle();
        ObservableList<T> currentList = table.getItems();
        ok.setOnAction((event)->{
            //FilteredList<T> filteredList = new FilteredList<>(list,p->true);
            String condition = filter.getText();
            System.out.println(condition);


            if(condition!=null&&!condition.equals("")) {
                Pattern pattern = Pattern.compile(condition, Pattern.CASE_INSENSITIVE);

                System.out.println("b");
                ObservableList<T> filteredList = currentList.filtered((p) -> pattern.matcher(converter.convert(p)).find());
                table.setItems(filteredList);
                //filteredList.forEach(System.out::println);
                //col.getSt
                col.setStyle(".column-header {\n" +
                        "    -fx-background-color: green;\n" +
                        "    -fx-border-width: .1\n" +
                        "}");
                //filteredList.setPredicate((p) -> String.valueOf(p).contains(condition));
            }
        });

        MenuItem reset = new MenuItem();
        reset.textProperty().bind(resourceFactory.getStringBinding("ResetFilter"));

        reset.setOnAction((event)->{
            //FilteredList<T> filteredList = new FilteredList<>(list,p->true);
            table.setItems(currentList);
            col.setStyle(defaultHeaderStyle);
        });


        contextMenu.getItems().addAll(filterItem,ok,reset);
        col.setContextMenu(contextMenu);
        return this;
    }
    public void resetFilters(){
        table.setItems(list);
    }
}

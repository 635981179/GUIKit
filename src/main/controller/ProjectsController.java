package controller;

import dao.Project4TableDao;
import dao.ProjectDao;
import domain.Project4Table;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import utils.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Observable;


public class ProjectsController implements BaseController {
    @FXML private TableView tview;
    @FXML private TableColumn<Project4Table, Long> colId;
    @FXML private TableColumn<Project4Table, String> colXmmc;
    @FXML private TableColumn<Project4Table, Date> colStartTime;
    @FXML private TableColumn<Project4Table, String> colProvider;
    @FXML private Pagination pagi;
    @FXML private TextField name;
    @FXML private DatePicker startTime;
    @FXML private DatePicker endTime;
    ObservableList<Project4Table> list;

    private Project4TableDao project4TableDao = new Project4TableDao();
    private ProjectDao projectDao = new ProjectDao();

    private TableView v = new TableView();
    public void init(){
        startTime.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                int count = project4TableDao.getProjectsCountOnCondition(DateUtils.DateFromLocalDate(newValue),
                        DateUtils.DateFromLocalDate(endTime.getValue()), name.getText());
                System.out.println(count/10);
                pagi.setPageCount(count/10 + 1);
                pagi.setCurrentPageIndex(0);
                showList(0);
            }
        });
        endTime.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                int count = project4TableDao.getProjectsCountOnCondition(DateUtils.DateFromLocalDate(startTime.getValue()),
                        DateUtils.DateFromLocalDate(newValue), name.getText());
                System.out.println(count/10);
                pagi.setPageCount(count/10 + 1);
                pagi.setCurrentPageIndex(0);
                showList(0);
            }
        });
        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int count = project4TableDao.getProjectsCountOnCondition(DateUtils.DateFromLocalDate(startTime.getValue()),
                        DateUtils.DateFromLocalDate(endTime.getValue()), newValue);
                System.out.println(count/10);
                pagi.setPageCount(count/10 + 1);
                pagi.setCurrentPageIndex(0);
                showList(0);
            }
        });
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colXmmc.setCellValueFactory(new PropertyValueFactory("xmmc"));
        colStartTime.setCellValueFactory(new PropertyValueFactory("startTime"));
        colStartTime.setCellFactory(column -> {
            return new TableCell<Project4Table, Date>() {
                private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                @Override
                protected void updateItem(Date item, boolean empty) {
                    System.out.println(item);
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(df.format(item));
                    }
                }
            };
        });
        colProvider.setCellValueFactory(new PropertyValueFactory("provider"));
        v.setVisible(false);
        Task<ObservableList<Project4Table>> initDataTask = new Task<ObservableList<Project4Table>>() {
            @Override
            protected ObservableList<Project4Table> call() throws Exception {
                ObservableList<Project4Table> list =  FXCollections.observableArrayList();
                list.addAll(project4TableDao.getProjects(null, null, null, 10, 1));
                return list;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Succeeded");
                tview.setItems(getValue());
            }
        };
        pagi.setPageCount(projectDao.getProjectsCount()/10);
        pagi.setCurrentPageIndex(0);
        //bug，只利用回调，返回一个看不见的空容器。如果返回使用中的列表会造成布局错乱
        pagi.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                showList(param);
                return v;        //←
            }
        });
        new Thread(initDataTask).start();
    }

    public void showList(Integer param){
        Task<ObservableList<Project4Table>> onShowTask = new Task<ObservableList<Project4Table>>() {
            @Override
            protected ObservableList<Project4Table> call() throws Exception {
                ObservableList<Project4Table> list =  FXCollections.observableArrayList();
                ZoneId zone = ZoneId.systemDefault();
                String nm = name.getText() == null || name.equals("")?null:name.getText();
                Date start = startTime.getValue() == null?null:Date.from(startTime.getValue().atStartOfDay().atZone(zone).toInstant());
                Date end = endTime.getValue() == null?null:Date.from(endTime.getValue().atStartOfDay().atZone(zone).toInstant());
                list.addAll(project4TableDao.getProjects(start, end, nm, 10, param));
                return list;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Succeeded");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                tview.setItems(getValue());
            }
        };
        new Thread(onShowTask).start();
    }

    @FXML public void reset(){
        startTime.setValue(null);
        endTime.setValue(null);
        name.setText(null);
    }

    @Override
    public void destroy() {

    }
}
